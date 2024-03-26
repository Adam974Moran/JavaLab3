package com.example.springbootlab1.service;

import org.springframework.stereotype.Service;

@Service
public interface CacheService{
    String getOrRecordSunInfoInString(String url);

}

