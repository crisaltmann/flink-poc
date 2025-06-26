package com.example.flinkcountsales.scheduler;

import com.example.flinkcountsales.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class VendaScheduler {

    @Autowired
    private VendaService vendaService;

    @Scheduled(fixedRate = 15000) // 15 segundos
    public void enviarVendaPeriodica() {
        vendaService.gerarEEnviarVenda();
    }
}