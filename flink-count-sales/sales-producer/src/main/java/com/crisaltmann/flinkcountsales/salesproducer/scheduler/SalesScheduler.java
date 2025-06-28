package com.crisaltmann.flinkcountsales.salesproducer.scheduler;

import com.crisaltmann.flinkcountsales.salesproducer.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SalesScheduler {

    @Autowired
    private SalesService salesService;

    @Scheduled(fixedRate = 5000) // 15 seconds
    public void sendPeriodicSale() {
        salesService.generateAndSendSale();
    }
}