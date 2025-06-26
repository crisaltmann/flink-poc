# 🚀 Sales App Starter - Inicializador da Aplicação

Este é o módulo responsável por inicializar toda a aplicação Spring Boot dos módulos de vendas.

## 📋 Descrição

Este módulo contém:

- **Classe Principal**: `SalesApplication` - ponto de entrada da aplicação
- **Configurações Centrais**: `application.yml` com configurações do Kafka
- **Orquestração**: Gerencia e inicializa todos os módulos do projeto
- **Infraestrutura**: Docker Compose para subir dependências

## 🔧 Funcionalidades

- Inicialização do Spring Boot com scan de todos os pacotes do projeto
- Habilitação do scheduling para execução de tarefas periódicas
- Configuração centralizada do Kafka
- Gerenciamento de dependências entre módulos

## 🚀 Execução

### Script Automatizado
```bash
./run.sh
```

### Execução Manual

#### Com Docker
1. **Subir o Kafka**:
   ```bash
   docker-compose up -d
   ```

2. **Executar a aplicação**:
   ```bash
   mvn spring-boot:run
   ```

#### Com Podman
1. **Subir o Kafka**:
   ```bash
   podman-compose up -d
   ```

2. **Executar a aplicação**:
   ```bash
   mvn spring-boot:run
   ```

## 📦 Dependências

Este módulo inclui:
- `sales-producer`: Para geração de eventos de vendas
- Spring Boot Starter
- Configurações de logging

## 🛑 Parar a Aplicação

**Com Docker:**
```bash
docker-compose down
```

**Com Podman:**
```bash
podman-compose down
```