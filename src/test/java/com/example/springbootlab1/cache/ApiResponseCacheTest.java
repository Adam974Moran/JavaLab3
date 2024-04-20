package com.example.springbootlab1.cache;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ApiResponseCacheTest {

  @Autowired
  private ApiResponseCache apiResponseCache;

  @MockBean
  private RestTemplate restTemplate;

  @BeforeEach
  public void setUp() {
    restTemplate = Mockito.mock(RestTemplate.class);
    apiResponseCache = new ApiResponseCache();
  }

  @Test
  void testAddToCache() {
    String key = "testKey";
    String value = "testValue";

    apiResponseCache.addToCache(key, value);

    assertEquals(value, apiResponseCache.getFromCache(key));
  }

  @Test
  void testGetFromCache() {
    String key = "testKey";
    String value = "testValue";

    apiResponseCache.addToCache(key, value);

    assertEquals(value, apiResponseCache.getFromCache(key));
  }

  @Test
  void testGetFromCache_NotFound() {
    String key = "testKey";

    assertNull(apiResponseCache.getFromCache(key));
  }

//  @Test
//  void testUpdateCache() {
//    String url = "http://example.com";
//    String expectedJson = "{\"key\":\"value\"}";
//    ResponseEntity<String> responseEntity = ResponseEntity.ok(expectedJson);
//
//    when(restTemplate.getForEntity(url, String.class)).thenReturn(responseEntity);
//
//    apiResponseCache.addToCache(url, "oldValue");
//    apiResponseCache.updateCache();
//
//    assertEquals(expectedJson, apiResponseCache.getFromCache(url));
//  }
}
