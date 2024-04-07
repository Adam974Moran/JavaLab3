package com.example.springbootlab1.data;

import com.example.springbootlab1.cache.ApiResponseCache;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ApiResponse {
  private static final ApiResponseCache apiResponseCache = new ApiResponseCache();

  private ApiResponse() {
  }

  public static String getJsonInString(String url) {
    if (apiResponseCache.getFromCache(url) == null) {
      RestTemplate restTemplate = new RestTemplate();
      ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
      apiResponseCache.addToCache(url, response.getBody());
      return response.getBody();
    } else {
      return apiResponseCache.getFromCache(url);
    }
  }
}
