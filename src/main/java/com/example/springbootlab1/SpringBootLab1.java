package com.example.springbootlab1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication()
public class SpringBootLab1 {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootLab1.class, args);
    }

}
