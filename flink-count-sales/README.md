# 🛍️ Flink Count Sales - Projeto Multi-módulo

Este é um projeto Maven multi-módulo para explorar casos de uso do Apache Flink com geração de eventos de vendas.

## 📁 Estrutura do Projeto

```
flink-count-sales/
├── pom.xml                 # POM pai do projeto
├── README.md              # Este arquivo
├── domain/                # Módulo de modelos de domínio
│   ├── src/
│   └── README.md
├── sales-app-starter/     # Módulo inicializador da aplicação
│   ├── src/
│   ├── docker-compose.yml
│   ├── run.sh
│   └── application.yml
├── sales-producer/        # Módulo produtor de eventos de vendas
│   ├── src/
│   ├── run.sh
│   └── README.md
└── flink-aggregation/     # Módulo de agregação Flink
    ├── src/
    └── README.md
```

## 🧩 Módulos

### 📦 domain
Módulo de modelos de domínio compartilhados. Contém as classes `Sale` e `SaleItem` utilizadas por todos os outros módulos, garantindo consistência dos dados.

### 🚀 sales-app-starter
Módulo responsável por inicializar a aplicação Spring Boot. Contém a classe principal e as configurações centrais do projeto. Este módulo orquestra todos os demais módulos.

### 📡 sales-producer
Módulo de produção de eventos de vendas. Contém a lógica de negócio para gerar e enviar eventos automaticamente a cada 15 segundos para o Apache Kafka.

### 📊 flink-aggregation
Módulo de processamento em tempo real usando Apache Flink. Consome eventos de vendas do Kafka e calcula agregações como valor total de vendas em janelas de tempo.

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

### Executar Aplicação
```bash
# Na raiz do projeto
./run.sh

# Ou diretamente no módulo app-starter
cd sales-app-starter && ./run.sh
```

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

- **sales-analytics**: Análises avançadas e métricas em tempo real
- **sales-dashboard**: Dashboard para visualização dos dados
- **sales-alerts**: Sistema de alertas baseado em thresholds