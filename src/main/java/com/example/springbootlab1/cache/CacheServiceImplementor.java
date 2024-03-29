package com.example.springbootlab1.cache;


import com.example.springbootlab1.data.APIResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service
public class CacheServiceImplementor implements CacheService{

    @Override
    @Cacheable(cacheNames = "sunInfoCache", key = "#url")
    public String getOrRecordSunInfoInString(String url) {
        return APIResponse.getJsonInString(url);
    }
}
