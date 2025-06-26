package com.crisaltmann.flinkcountsales.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemVenda {
    @JsonProperty("id_produto")
    private Integer idProduto;
    
    private Integer quantidade;
    
    @JsonProperty("valor_venda")
    private Double valorVenda;
}