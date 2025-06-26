package com.crisaltmann.flinkcountsales.appstarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.crisaltmann.flinkcountsales")
@EnableScheduling
public class SalesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SalesApplication.class, args);
    }

}