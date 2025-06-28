FROM openjdk:17-jdk-slim

# Instalar Maven e curl
RUN apt-get update && apt-get install -y maven curl && rm -rf /var/lib/apt/lists/*

# Criar diretório de trabalho
WORKDIR /app

# Copiar settings.xml para usar Maven Central
COPY settings.xml /root/.m2/settings.xml

# Primeiro, instalar o POM pai
COPY pom.xml ./
RUN mvn install -N -q

# Segundo, compilar e instalar o módulo domain
WORKDIR /app/domain
COPY domain/pom.xml ./
COPY domain/src ./src
RUN mvn clean install -q

# Terceiro, compilar o módulo flink-aggregation
WORKDIR /app/flink-aggregation
COPY flink-aggregation/pom.xml ./
COPY flink-aggregation/src ./src
RUN mvn clean package -q

# Script para submeter job ao Flink
COPY flink-aggregation/submit-job.sh ./
RUN chmod +x submit-job.sh

# Aguardar Flink estar disponível e submeter job
CMD ["./submit-job.sh"]