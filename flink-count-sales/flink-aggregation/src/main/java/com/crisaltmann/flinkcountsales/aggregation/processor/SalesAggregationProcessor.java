package com.crisaltmann.flinkcountsales.aggregation.processor;

import com.crisaltmann.flinkcountsales.domain.model.Sale;
import com.crisaltmann.flinkcountsales.aggregation.model.SalesAggregation;
import org.apache.flink.api.common.functions.AggregateFunction;

/**
 * Processador de agregação que calcula o valor total de vendas.
 * Implementa a função de agregação do Flink para acumular valores de vendas em tempo real.
 */
public class SalesAggregationProcessor implements AggregateFunction<Sale, SalesAggregation, SalesAggregation> {

    @Override
    public SalesAggregation createAccumulator() {
        System.out.println("Criando novo acumulador");
        return new SalesAggregation(0L, 0.0, System.currentTimeMillis());
    }

    @Override
    public SalesAggregation add(Sale sale, SalesAggregation accumulator) {
        System.out.println("Adicionando venda ao acumulador. Count atual: " + accumulator.getTotalSalesCount() + ", Valor: " + sale.getTotalValue());
        accumulator.addSale(sale);
        System.out.println("Após adicionar. Count: " + accumulator.getTotalSalesCount() + ", Total: " + accumulator.getTotalSalesValue());
        return accumulator;
    }

    @Override
    public SalesAggregation getResult(SalesAggregation accumulator) {
        return accumulator;
    }

    @Override
    public SalesAggregation merge(SalesAggregation agg1, SalesAggregation agg2) {
        return SalesAggregation.combine(agg1, agg2);
    }
}