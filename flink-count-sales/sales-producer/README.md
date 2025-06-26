# 🛍️ Sales Producer - Produtor de Eventos de Vendas

Este é um produtor Spring Boot que gera eventos de vendas automaticamente a cada 15 segundos e os envia para um tópico do Apache Kafka.

## 📋 Descrição

A aplicação simula um sistema de vendas gerando eventos com as seguintes características:

- **ID da Venda**: UUID único para cada venda
- **ID do Vendedor**: Número aleatório entre 1 e 5
- **Itens da Venda**: Lista com 1 a 3 produtos, onde:
  - **ID do Produto**: Entre 1 e 15
  - **Quantidade**: Entre 1 e 5 unidades
  - **Valor**: Preço unitário = ID do produto (ex: produto 5 custa R$ 5,00)

## 🛠️ Tecnologias

- ☕ **Java 17**
- 🚀 **Spring Boot 3.2.0**
- 📡 **Apache Kafka**
- 🔧 **Lombok**
- 📦 **Maven**

## 🚀 Execução Rápida

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

2. **Executar a aplicação Spring Boot**:
   ```bash
   mvn spring-boot:run
   ```

#### Com Podman
1. **Subir o Kafka**:
   ```bash
   podman-compose up -d
   ```

2. **Executar a aplicação Spring Boot**:
   ```bash
   mvn spring-boot:run
   ```

## 📊 Monitoramento

A aplicação irá exibir logs no console a cada evento enviado:
```
Sale sent: 123e4567-e89b-12d3-a456-426614174000 - Salesperson: 3
```

## 🔧 Configuração

O tópico Kafka utilizado é `sales` e a configuração padrão aponta para `localhost:9092`.

Para alterar as configurações, edite o arquivo `src/main/resources/application.yml`.

## 📝 Exemplo de Evento JSON

```json
{
  "sale_id": "123e4567-e89b-12d3-a456-426614174000",
  "salesperson_id": 3,
  "items": [
    {
      "product_id": 5,
      "quantity": 2,
      "sale_value": 10.0
    },
    {
      "product_id": 12,
      "quantity": 1,
      "sale_value": 12.0
    }
  ]
}
```

## 🛑 Parar a Aplicação

**Com Docker:**
```bash
docker-compose down
```

**Com Podman:**
```bash
podman-compose down
```