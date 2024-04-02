package com.example.springbootlab1.data;

import com.example.springbootlab1.cache.APIResponseCache;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class APIResponse {
    private APIResponse(){}
    private static final APIResponseCache apiResponseCache = new APIResponseCache();

    public static String getJsonInString(String url){
        if(apiResponseCache.getFromCache(url) == null) {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            apiResponseCache.addToCache(url, response.getBody());
            return response.getBody();
        }
        else {
            return apiResponseCache.getFromCache(url);
        }
    }
}
