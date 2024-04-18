package com.example.springbootlab1.cache;

import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * The type Api response cache.
 */
@Component
@Data
@EnableScheduling
public class ApiResponseCache {

  private static final int MAX_AMOUNT_OF_ELEMENTS = 3;
  private static final Logger logger = LoggerFactory.getLogger(ApiResponseCache.class);

  private final Map<String, String> cache = new LinkedHashMap<>() {
    @Override
    protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
      logger.info("Eldest Entry wad deleted");
      return size() > MAX_AMOUNT_OF_ELEMENTS;
    }
  };

  /**
   * Update cache.
   */
  @Scheduled(fixedRate = 300000) //5 минут
  public void updateCache() {
    RestTemplate restTemplate = new RestTemplate();
    for (String key : cache.keySet()) {
      ResponseEntity<String> response = restTemplate.getForEntity(key, String.class);
      addToCache(key, response.getBody());
    }
    logger.info("Cache has been updated successfully!");
  }

  /**
   * Add to cache.
   *
   * @param key   the key
   * @param value the value
   */
  public void addToCache(String key, String value) {
    cache.put(key, value);
  }

  /**
   * Gets from cache.
   *
   * @param key the key
   * @return data from cache
   */
  public String getFromCache(String key) {
    return cache.get(key);
  }
}
