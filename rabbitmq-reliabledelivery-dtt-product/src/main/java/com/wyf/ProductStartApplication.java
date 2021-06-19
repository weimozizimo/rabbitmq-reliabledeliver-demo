package com.wyf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;

@EnableTransactionManagement
@RestController
@SpringBootApplication
@EnableConfigurationProperties
public class ProductStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductStartApplication.class);
    }
}
