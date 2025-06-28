#!/bin/bash

echo "🚀 Iniciando Flink Count Sales - Ambiente Completo"
echo "=================================================="

# Verificar se o Podman está disponível
if command -v podman &> /dev/null; then
    CONTAINER_CMD="podman"
    COMPOSE_CMD="podman-compose"
    echo "🐳 Usando Podman"
elif command -v docker &> /dev/null; then
    CONTAINER_CMD="docker"
    COMPOSE_CMD="docker-compose"
    echo "🐳 Usando Docker"
else
    echo "❌ Nem Docker nem Podman foram encontrados. Por favor, instale um deles."
    exit 1
fi

# Verificar se o container runtime está rodando
if ! $CONTAINER_CMD info > /dev/null 2>&1; then
    echo "❌ $CONTAINER_CMD não está rodando. Por favor, inicie o $CONTAINER_CMD e tente novamente."
    exit 1
fi

# Função para cleanup ao sair
cleanup() {
    echo ""
    echo "🛑 Parando todos os containers..."
    
    cd sales-app-starter
    if [ "$COMPOSE_CMD" = "docker-compose" ]; then
        echo "🐳 Parando containers Docker..."
        docker-compose down
    else
        echo "🐳 Parando containers Podman..."
        podman-compose down
    fi
    
    echo "✅ Cleanup concluído!"
    exit 0
}

# Capturar sinais de interrupção
trap cleanup SIGINT SIGTERM

# Subir todos os serviços
echo "📡 Iniciando todos os serviços..."
echo "   - Kafka + Zookeeper"
echo "   - Flink (JobManager + TaskManager)"
echo "   - Sales Producer (Spring Boot)"
echo "   - Flink Job Submitter"
echo "   - Kafka UI"
echo ""

cd sales-app-starter
$COMPOSE_CMD up --build

echo "🎯 Serviços disponíveis:"
echo "   📊 Flink Dashboard: http://localhost:8081"
echo "   📡 Kafka UI: http://localhost:8080"
echo "   🛍️ Producer: logs nos containers"
echo ""
echo "🔍 Pressione Ctrl+C para parar todos os containers"

# Aguardar até ser interrompido
wait