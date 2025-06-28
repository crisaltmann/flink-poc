package com.crisaltmann.flinkcountsales.aggregation.job;

import com.crisaltmann.flinkcountsales.domain.model.Sale;
import com.crisaltmann.flinkcountsales.aggregation.model.SalesAggregation;
import com.crisaltmann.flinkcountsales.aggregation.processor.SalesAggregationProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
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
    
    // Configurações padrão
    private static String KAFKA_SERVERS = "localhost:9092";
    private static String KAFKA_TOPIC = "sales";
    private static String KAFKA_GROUP_ID = "flink-sales-aggregation";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    public static void main(String[] args) throws Exception {
        // Processar argumentos da linha de comando
        for (int i = 0; i < args.length; i++) {
            if ("--kafka.bootstrap-servers".equals(args[i]) && i + 1 < args.length) {
                KAFKA_SERVERS = args[i + 1];
                i++;
            } else if ("--kafka.topic.sales".equals(args[i]) && i + 1 < args.length) {
                KAFKA_TOPIC = args[i + 1];
                i++;
            } else if ("--kafka.group-id".equals(args[i]) && i + 1 < args.length) {
                KAFKA_GROUP_ID = args[i + 1];
                i++;
            }
        }
        
        System.out.println("Configurações Kafka:");
        System.out.println("Bootstrap Servers: " + KAFKA_SERVERS);
        System.out.println("Topic: " + KAFKA_TOPIC);
        System.out.println("Group ID: " + KAFKA_GROUP_ID);
        
        // Configurar ambiente de execução do Flink
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        
        // Configurar fonte Kafka
        KafkaSource<String> source = KafkaSource.<String>builder()
                .setBootstrapServers(KAFKA_SERVERS)
                .setTopics(KAFKA_TOPIC)
                .setGroupId(KAFKA_GROUP_ID)
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .build();
        
        // Criar stream de dados
        DataStream<String> kafkaStream = env.fromSource(source, WatermarkStrategy.noWatermarks(), "Kafka Sales Source");
        
        // Processar eventos de vendas
        DataStream<SalesAggregation> aggregatedSales = kafkaStream
                .map(SalesAggregationJob::parseSale)
                .filter(sale -> sale != null)
                .keyBy(sale -> "global") // Chave única para agregação global
                .window(TumblingProcessingTimeWindows.of(Time.seconds(30))) // Janela de 60 segundos
                .aggregate(new SalesAggregationProcessor());
        
        // Criar sink para Kafka para salvar resultados
        KafkaSink<String> kafkaSink = KafkaSink.<String>builder()
                .setBootstrapServers(KAFKA_SERVERS)
                .setRecordSerializer(KafkaRecordSerializationSchema.builder()
                        .setTopic("sales-aggregated")
                        .setValueSerializationSchema(new SimpleStringSchema())
                        .build())
                .build();

        // Converter para JSON e enviar para Kafka
        DataStream<String> jsonResults = aggregatedSales
                .map(agg -> {
                    System.out.println("Agregacao calculada: " + agg);
                    return objectMapper.writeValueAsString(agg);
                });

        // Salvar no Kafka e também imprimir
        jsonResults.sinkTo(kafkaSink);
        jsonResults.print();
        
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