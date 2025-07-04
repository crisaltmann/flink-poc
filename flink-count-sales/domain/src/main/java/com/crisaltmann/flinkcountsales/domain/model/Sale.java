package com.crisaltmann.flinkcountsales.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sale {
    @JsonProperty("sale_id")
    private String saleId;
    
    @JsonProperty("salesperson_id")
    private Integer salespersonId;
    
    private List<SaleItem> items;
    
    public double getTotalValue() {
        return items.stream()
            .mapToDouble(SaleItem::getSaleValue)
            .sum();
    }
}