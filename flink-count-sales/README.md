# 🛍️ Flink Count Sales - Projeto Multi-módulo

Este é um projeto Maven multi-módulo para explorar casos de uso do Apache Flink com geração de eventos de vendas.

## 📁 Estrutura do Projeto

```
flink-count-sales/
├── pom.xml                 # POM pai do projeto
├── README.md              # Este arquivo
└── sales-producer/        # Módulo produtor de eventos de vendas
    ├── src/
    ├── docker-compose.yml
    ├── run.sh
    └── README.md
```

## 🧩 Módulos

### 📡 sales-producer
Produtor Spring Boot que gera eventos de vendas automaticamente a cada 15 segundos e os envia para um tópico do Apache Kafka.

**Características dos eventos:**
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
- 📦 **Maven (Multi-módulo)**

## 🚀 Execução

### Executar Projeto Completo
```bash
# Na raiz do projeto
mvn clean install
```

### Executar Módulo Específico
Consulte o README de cada módulo para instruções específicas de execução.

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

## 🔮 Próximos Módulos

- **sales-consumer**: Consumidor Flink para processamento de eventos
- **sales-analytics**: Análises e métricas em tempo real
- **sales-dashboard**: Dashboard para visualização dos dados