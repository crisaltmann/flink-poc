package com.crisaltmann.flinkcountsales.salesproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SalesProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SalesProducerApplication.class, args);
    }

}