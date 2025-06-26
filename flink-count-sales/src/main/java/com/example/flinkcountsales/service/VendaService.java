package com.example.flinkcountsales.service;

import com.example.flinkcountsales.model.ItemVenda;
import com.example.flinkcountsales.model.Venda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class VendaService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    private final Random random = new Random();
    private static final String TOPIC_NAME = "vendas";

    public void gerarEEnviarVenda() {
        Venda venda = gerarVendaAleatoria();
        kafkaTemplate.send(TOPIC_NAME, venda.getIdVenda(), venda);
        System.out.println("Venda enviada: " + venda.getIdVenda() + " - Vendedor: " + venda.getIdVendedor());
    }

    private Venda gerarVendaAleatoria() {
        String idVenda = UUID.randomUUID().toString();
        Integer idVendedor = random.nextInt(5) + 1; // Range 1-5
        
        List<ItemVenda> itens = gerarItensVenda();
        
        return new Venda(idVenda, idVendedor, itens);
    }

    private List<ItemVenda> gerarItensVenda() {
        List<ItemVenda> itens = new ArrayList<>();
        int numeroItens = random.nextInt(3) + 1; // 1 a 3 itens por venda
        
        for (int i = 0; i < numeroItens; i++) {
            Integer idProduto = random.nextInt(15) + 1; // Range 1-15
            Integer quantidade = random.nextInt(5) + 1; // 1 a 5 unidades
            Double valorUnitario = idProduto.doubleValue(); // Produto ID = preço unitário
            Double valorVenda = valorUnitario * quantidade;
            
            itens.add(new ItemVenda(idProduto, quantidade, valorVenda));
        }
        
        return itens;
    }
}