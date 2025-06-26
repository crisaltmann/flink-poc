#!/bin/bash

echo "🔥 Executando Flink Sales Aggregation Job"
echo "=========================================="

# Verificar se o Maven está instalado
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven não encontrado. Por favor, instale o Maven."
    exit 1
fi

# Verificar se o Flink está rodando
if ! command -v flink &> /dev/null; then
    echo "❌ Flink CLI não encontrado."
    echo "💡 Para instalar o Flink:"
    echo "   1. Baixe: https://flink.apache.org/downloads.html"
    echo "   2. Extraia e adicione bin/ ao PATH"
    echo "   3. Execute: ./bin/start-cluster.sh"
    exit 1
fi

# Compilar o projeto se necessário
echo "🔨 Compilando o projeto Flink..."
cd .. && mvn clean install -pl flink-aggregation -am -q && cd flink-aggregation

# Verificar se o cluster Flink está rodando
if ! curl -s http://localhost:8081 > /dev/null; then
    echo "❌ Cluster Flink não está rodando em localhost:8081"
    echo "💡 Para iniciar o Flink:"
    echo "   ./bin/start-cluster.sh"
    echo "   Acesse: http://localhost:8081"
    exit 1
fi

# Executar o job no cluster Flink
echo "🚀 Submetendo job para o cluster Flink..."
echo "🌐 Dashboard: http://localhost:8081"
echo ""

flink run \
    -c com.crisaltmann.flinkcountsales.aggregation.job.SalesAggregationJob \
    target/flink-aggregation-0.0.1-SNAPSHOT.jar \
    --kafka.bootstrap-servers localhost:9092 \
    --kafka.topic.sales sales \
    --kafka.group-id flink-sales-aggregation

echo "✅ Job submetido! Verifique o dashboard do Flink para acompanhar a execução."