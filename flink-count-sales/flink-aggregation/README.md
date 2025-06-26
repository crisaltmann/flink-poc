# 📊 Flink Aggregation - Módulo de Agregação de Vendas

Este módulo implementa processamento em tempo real de eventos de vendas usando Apache Flink, calculando agregações como valor total de vendas.

## 📋 Descrição

O módulo `flink-aggregation` é responsável por:

- **Consumir eventos de vendas** do tópico Kafka "sales"
- **Processar em tempo real** usando Apache Flink
- **Agregar valores totais** de vendas em janelas de tempo
- **Exibir resultados** de agregação no console

## 🏗️ Componentes

### 📦 Model (`model/`)

#### `Sale.java`
- Representa um evento de venda recebido do Kafka
- Contém ID da venda, vendedor e lista de itens
- Método `getTotalValue()` calcula valor total da venda

#### `SaleItem.java`  
- Representa um item individual dentro de uma venda
- Contém ID do produto, quantidade e valor

#### `SalesAggregation.java`
- Modelo de agregação que acumula estatísticas
- Campos: total de vendas, valor total, timestamp da última atualização
- Métodos para combinar agregações

### ⚙️ Processor (`processor/`)

#### `SalesAggregationProcessor.java`
- Implementa `AggregateFunction` do Flink
- Define como agregar eventos de vendas
- Operações: criar acumulador, adicionar venda, mesclar agregações

### 🚀 Job (`job/`)

#### `SalesAggregationJob.java`
- **Classe principal** do job Flink
- Configura fonte Kafka e pipeline de processamento
- Define janelas de agregação (30 segundos)
- Converte JSON para objetos Sale

## 🔧 Configuração

### Parâmetros do Kafka
- **Servidores**: `localhost:9092`
- **Tópico**: `sales`
- **Grupo de consumidor**: `flink-sales-aggregation`

### Janelas de Agregação
- **Tipo**: Tumbling Windows (janelas fixas)
- **Duração**: 30 segundos
- **Processamento**: Processing Time

## 🚀 Execução

### Pré-requisitos
1. **Flink instalado** e cluster rodando em `localhost:8081`
2. **Kafka rodando** com tópico `sales`
3. **Producer de vendas** executando

### Executar Job
```bash
# Script automatizado
./run-flink-job.sh
```

### Manual
```bash
# Compilar o projeto
mvn clean package

# Submeter para cluster Flink
flink run -c com.crisaltmann.flinkcountsales.aggregation.job.SalesAggregationJob \
  target/flink-aggregation-0.0.1-SNAPSHOT.jar
```

## 📊 Output

O job exibe resultados no console a cada janela de 30 segundos:

```
SalesAggregation(totalSalesCount=5, totalSalesValue=150.50, lastUpdated=1703123456789)
```

Onde:
- `totalSalesCount`: Número total de vendas na janela
- `totalSalesValue`: Valor total em reais das vendas
- `lastUpdated`: Timestamp da última atualização

## 🛠️ Tecnologias

- ☕ **Java 17**
- 🌊 **Apache Flink 1.18.0** - Stream processing
- 📡 **Flink Kafka Connector** - Integração com Kafka
- 🔧 **Lombok** - Redução de boilerplate
- 📝 **Jackson** - Processamento JSON
- 📋 **Log4j2** - Sistema de logs

## 🔗 Dependências

Este módulo funciona em conjunto com:
- `sales-producer`: Gera eventos de vendas para o Kafka
- Kafka: Broker de mensagens para streaming de eventos

## ⚡ Performance

- **Latência**: Baixa latência com processamento em streaming
- **Throughput**: Suporta alto volume de eventos
- **Paralelismo**: Configurável via Flink
- **Tolerância a falhas**: Checkpointing automático do Flink