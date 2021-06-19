package com.wyf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class ProductStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductStartApplication.class);
    }
}
