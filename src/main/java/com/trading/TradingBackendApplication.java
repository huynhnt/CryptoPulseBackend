package com.trading;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TradingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(TradingBackendApplication.class, args);
    }

}
