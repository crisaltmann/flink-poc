# 🛍️ Sales Producer - Módulo Produtor de Eventos de Vendas

Este módulo contém a lógica de geração de eventos de vendas que são enviados para o Apache Kafka a cada 15 segundos.

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

## 🏗️ Estrutura

- **Models**: `Sale` e `SaleItem` - representam os eventos de venda
- **Service**: `SalesService` - lógica de geração e envio dos eventos
- **Scheduler**: `SalesScheduler` - execução periódica a cada 15 segundos
- **Config**: `KafkaProducerConfig` - configuração do produtor Kafka

## 🚀 Execução

**Nota**: Este módulo não pode ser executado independentemente. Deve ser iniciado através do módulo `sales-app-starter`.

### Executar via App Starter
```bash
# Na raiz do projeto
./run.sh

# Ou diretamente
cd ../sales-app-starter && ./run.sh
```

## 📊 Monitoramento

A aplicação irá exibir logs no console a cada evento enviado:
```
Sale sent: 123e4567-e89b-12d3-a456-426614174000 - Salesperson: 3
```

## 🔧 Configuração

O tópico Kafka utilizado é `sales`. As configurações ficam no módulo `sales-app-starter` no arquivo `application.yml`.

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

## 🔗 Dependências

Este módulo é uma dependência do `sales-app-starter` e não possui configurações próprias de execução.