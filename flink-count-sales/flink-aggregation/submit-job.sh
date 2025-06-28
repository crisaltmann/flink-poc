#!/bin/bash

echo "🔥 Aguardando Flink JobManager ficar disponível..."

# Aguardar Flink estar pronto
until curl -s http://flink-jobmanager:8081/v1/overview > /dev/null 2>&1; do
    echo "⏳ Aguardando Flink JobManager... (tentando http://flink-jobmanager:8081)"
    sleep 5
done

echo "✅ Flink JobManager disponível!"

# Aguardar TaskManager estar pronto
echo "⏳ Aguardando TaskManager..."
sleep 15

echo "📁 Fazendo upload do JAR..."

# Upload do JAR
UPLOAD_RESPONSE=$(curl -s -X POST \
    -F "jarfile=@target/flink-aggregation-0.0.1-SNAPSHOT.jar" \
    http://flink-jobmanager:8081/v1/jars/upload)

echo "Upload response: $UPLOAD_RESPONSE"

# Extrair JAR ID do response
JAR_ID=$(echo $UPLOAD_RESPONSE | grep -o '"filename":"[^"]*"' | cut -d'"' -f4 | sed 's|.*/||')

if [ -z "$JAR_ID" ]; then
    echo "❌ Erro no upload do JAR"
    echo "Response: $UPLOAD_RESPONSE"
    exit 1
fi

echo "✅ JAR uploaded: $JAR_ID"

echo "🚀 Submetendo job de agregação de vendas..."

# Submeter o job
RUN_RESPONSE=$(curl -s -X POST \
    -H "Content-Type: application/json" \
    -d '{
        "entryClass": "com.crisaltmann.flinkcountsales.aggregation.job.SalesAggregationJob",
        "programArgs": "--kafka.bootstrap-servers kafka:29092 --kafka.topic.sales sales --kafka.group-id flink-sales-aggregation"
    }' \
    "http://flink-jobmanager:8081/v1/jars/$JAR_ID/run")

echo "✅ Job submetido!"
echo "Response: $RUN_RESPONSE"

echo "🎯 Job rodando no Flink!"
echo "📊 Acesse http://localhost:8081 para visualizar"

# Manter container rodando para logs
tail -f /dev/null