package com.crisaltmann.flinkcountsales.aggregation.job;

import com.crisaltmann.flinkcountsales.domain.model.Sale;
import com.crisaltmann.flinkcountsales.aggregation.model.SalesAggregation;
import com.crisaltmann.flinkcountsales.aggregation.processor.SalesAggregationProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * Job principal do Flink para agregação de vendas em tempo real.
 * 
 * Este job:
 * 1. Consome eventos de vendas do tópico Kafka "sales"
 * 2. Converte JSON para objetos Sale
 * 3. Agrega valores totais em janelas de tempo
 * 4. Exibe resultados no console
 */
public class SalesAggregationJob {
    
    // Configurações que podem ser passadas como argumentos ou variáveis de ambiente
    private static final String KAFKA_SERVERS = System.getProperty("kafka.bootstrap-servers", "localhost:9092");
    private static final String KAFKA_TOPIC = System.getProperty("kafka.topic.sales", "sales");
    private static final String KAFKA_GROUP_ID = System.getProperty("kafka.group-id", "flink-sales-aggregation");
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    public static void main(String[] args) throws Exception {
        // Configurar ambiente de execução do Flink
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        
        // Configurar fonte Kafka
        KafkaSource<String> source = KafkaSource.<String>builder()
                .setBootstrapServers(KAFKA_SERVERS)
                .setTopics(KAFKA_TOPIC)
                .setGroupId(KAFKA_GROUP_ID)
                .setStartingOffsets(OffsetsInitializer.latest())
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .build();
        
        // Criar stream de dados
        DataStream<String> kafkaStream = env.fromSource(source, WatermarkStrategy.noWatermarks(), "Kafka Sales Source");
        
        // Processar eventos de vendas
        DataStream<SalesAggregation> aggregatedSales = kafkaStream
                .map(SalesAggregationJob::parseSale)
                .filter(sale -> sale != null)
                .keyBy(sale -> "global") // Chave única para agregação global
                .window(TumblingProcessingTimeWindows.of(Time.seconds(30))) // Janela de 30 segundos
                .aggregate(new SalesAggregationProcessor());
        
        // Exibir resultados
        aggregatedSales.print();
        
        // Executar job
        env.execute("Sales Aggregation Job");
    }
    
    /**
     * Converte string JSON em objeto Sale.
     */
    private static Sale parseSale(String jsonString) {
        try {
            return objectMapper.readValue(jsonString, Sale.class);
        } catch (Exception e) {
            System.err.println("Erro ao processar evento de venda: " + e.getMessage());
            return null;
        }
    }
}