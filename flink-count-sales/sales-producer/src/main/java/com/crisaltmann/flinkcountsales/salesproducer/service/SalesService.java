package com.crisaltmann.flinkcountsales.salesproducer.service;

import com.crisaltmann.flinkcountsales.salesproducer.model.Sale;
import com.crisaltmann.flinkcountsales.salesproducer.model.SaleItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class SalesService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    private final Random random = new Random();
    private static final String TOPIC_NAME = "sales";

    public void generateAndSendSale() {
        Sale sale = generateRandomSale();
        kafkaTemplate.send(TOPIC_NAME, sale.getSaleId(), sale);
        System.out.println("Sale sent: " + sale.getSaleId() + " - Salesperson: " + sale.getSalespersonId());
    }

    private Sale generateRandomSale() {
        String saleId = UUID.randomUUID().toString();
        Integer salespersonId = random.nextInt(5) + 1; // Range 1-5
        
        List<SaleItem> items = generateSaleItems();
        
        return new Sale(saleId, salespersonId, items);
    }

    private List<SaleItem> generateSaleItems() {
        List<SaleItem> items = new ArrayList<>();
        int itemCount = random.nextInt(3) + 1; // 1 to 3 items per sale
        
        for (int i = 0; i < itemCount; i++) {
            Integer productId = random.nextInt(15) + 1; // Range 1-15
            Integer quantity = random.nextInt(5) + 1; // 1 to 5 units
            Double unitPrice = productId.doubleValue(); // Product ID = unit price
            Double saleValue = unitPrice * quantity;
            
            items.add(new SaleItem(productId, quantity, saleValue));
        }
        
        return items;
    }
}