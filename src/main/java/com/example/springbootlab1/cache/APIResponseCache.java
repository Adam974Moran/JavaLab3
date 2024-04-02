package com.example.springbootlab1.cache;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
@Data
public class APIResponseCache {

    private static final int MAX_AMOUNT_OF_ELEMENTS = 3;
    private static final Logger logger = LoggerFactory.getLogger(APIResponseCache.class);

    private final Map<String, String> cache = new LinkedHashMap<>() {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
            logger.info("Eldest Entry wad deleted");
            return size() > MAX_AMOUNT_OF_ELEMENTS;
        }
    };

    public void addToCache(String key, String value) {
        cache.put(key, value);
    }

    public String getFromCache(String key) {
        return cache.get(key);
    }
}
