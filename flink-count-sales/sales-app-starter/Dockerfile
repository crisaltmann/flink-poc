FROM openjdk:17-jdk-slim

# Instalar Maven
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

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

# Terceiro, compilar e instalar o módulo sales-producer
WORKDIR /app/sales-producer
COPY sales-producer/pom.xml ./
COPY sales-producer/src ./src
RUN mvn clean install -q

# Quarto, compilar o módulo sales-app-starter
WORKDIR /app/sales-app-starter
COPY sales-app-starter/pom.xml ./
COPY sales-app-starter/src ./src

# Expor porta da aplicação
EXPOSE 8080

# Comando para executar a aplicação
CMD ["mvn", "spring-boot:run"]