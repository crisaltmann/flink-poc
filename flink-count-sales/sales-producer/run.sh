#!/bin/bash

echo "🚀 Iniciando Flink Count Sales Producer"
echo "========================================"

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

# Subir o Kafka
echo "📡 Subindo o Kafka com $COMPOSE_CMD..."
$COMPOSE_CMD up -d

# Aguardar o Kafka estar pronto
echo "⏳ Aguardando o Kafka ficar disponível..."
sleep 15

# Verificar se o Maven está instalado
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven não encontrado. Por favor, instale o Maven."
    exit 1
fi

# Executar a aplicação Spring Boot
echo "🛍️ Iniciando o producer de vendas..."
echo "💡 A aplicação irá gerar eventos de venda a cada 15 segundos"
echo "🔍 Pressione Ctrl+C para parar a aplicação"
echo ""

cd ../sales-app-starter && mvn spring-boot:run