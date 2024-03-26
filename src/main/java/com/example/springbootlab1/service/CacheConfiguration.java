package com.example.springbootlab1.service;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.TimeUnit;


@Configuration
@EnableCaching
@EnableScheduling
public class CacheConfiguration {
    @Bean
    public CaffeineCacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("sunInfoCache");
        cacheManager.setCaffeine(Caffeine.newBuilder().maximumSize(1).expireAfterWrite(10, TimeUnit.SECONDS).
                evictionListener((key, value, cause) -> System.out.println("Evicted: " + key + ", Cause: " + cause.toString())));
        return cacheManager;
    }
}
