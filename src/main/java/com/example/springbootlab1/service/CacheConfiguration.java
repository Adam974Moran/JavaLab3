package com.example.springbootlab1.service;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@Configuration
@EnableCaching
@EnableScheduling
public class CacheConfiguration {
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("sunInfoCache");
    }

    @CacheEvict(cacheNames = "sunInfoCache", allEntries = true)
    @Scheduled(fixedRate = 6000) //10 минут ожидания
    public void clearCache(){
        //Метод пустой так как для аннотации @CacheEvict,
        //которая просто очищает кеш, аннотация не нужна
    }
}
