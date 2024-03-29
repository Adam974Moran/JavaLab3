package com.example.springbootlab1.cache;

import org.springframework.stereotype.Service;

@Service
public interface CacheService{
    String getOrRecordSunInfoInString(String url);
}

