package com.crisaltmann.flinkcountsales.salesproducer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleItem {
    @JsonProperty("product_id")
    private Integer productId;
    
    private Integer quantity;
    
    @JsonProperty("sale_value")
    private Double saleValue;
}