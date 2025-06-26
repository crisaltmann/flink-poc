package com.crisaltmann.flinkcountsales.aggregation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesAggregation {
    private long totalSalesCount;
    private double totalSalesValue;
    private long lastUpdated;
    
    public void addSale(Sale sale) {
        this.totalSalesCount++;
        this.totalSalesValue += sale.getTotalValue();
        this.lastUpdated = System.currentTimeMillis();
    }
    
    public static SalesAggregation combine(SalesAggregation agg1, SalesAggregation agg2) {
        return new SalesAggregation(
            agg1.totalSalesCount + agg2.totalSalesCount,
            agg1.totalSalesValue + agg2.totalSalesValue,
            Math.max(agg1.lastUpdated, agg2.lastUpdated)
        );
    }
}