# 📦 Domain - Módulo de Modelos de Domínio

Este módulo contém as classes de domínio compartilhadas entre todos os módulos do projeto.

## 📋 Descrição

O módulo `domain` centraliza os modelos de dados principais do projeto, garantindo consistência e reutilização entre os diferentes módulos.

## 🏗️ Componentes

### 📄 Model (`model/`)

#### `Sale.java`
- **Descrição**: Representa um evento de venda completo
- **Campos**:
  - `saleId`: Identificador único da venda (UUID)
  - `salespersonId`: ID do vendedor (1-5)
  - `items`: Lista de itens vendidos
- **Métodos**:
  - `getTotalValue()`: Calcula valor total da venda

#### `SaleItem.java`
- **Descrição**: Representa um item individual dentro de uma venda
- **Campos**:
  - `productId`: ID do produto (1-15)
  - `quantity`: Quantidade vendida
  - `saleValue`: Valor total do item (preço unitário × quantidade)

## 🔧 Tecnologias

- ☕ **Java 17**
- 🔧 **Lombok** - Redução de boilerplate
- 📝 **Jackson** - Anotações para serialização JSON

## 📝 Exemplo de JSON

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

Este módulo é utilizado por:
- `sales-producer`: Para criação e envio de eventos de vendas
- `flink-aggregation`: Para processamento dos eventos recebidos

## 📋 Regras de Negócio

- **Produtos**: IDs de 1 a 15, onde o preço unitário = ID do produto
- **Vendedores**: IDs de 1 a 5
- **Quantidade**: Entre 1 e 5 unidades por item
- **Valor**: Calculado automaticamente (quantidade × preço unitário)