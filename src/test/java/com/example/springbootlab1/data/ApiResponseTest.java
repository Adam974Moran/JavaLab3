package com.example.springbootlab1.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.example.springbootlab1.cache.ApiResponseCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class ApiResponseTest {

  private RestTemplate restTemplate;
  private ApiResponseCache apiResponseCache;

  @BeforeEach
  public void setUp() {
    restTemplate = Mockito.mock(RestTemplate.class);
    apiResponseCache = Mockito.mock(ApiResponseCache.class);
    ApiResponse.apiResponseCache = apiResponseCache; // Заменяем статическую переменную на мок
  }


  @Test
  void testGetJsonInString_CacheHit() {
    String url = "https://api.sunrise-sunset.org/json";
    String expectedJson = "{\"results\":\"\",\"status\":\"INVALID_REQUEST\"}";

    when(apiResponseCache.getFromCache(url)).thenReturn(expectedJson);

    String actualJson = ApiResponse.getJsonInString(url);

    assertEquals(expectedJson, actualJson);
    Mockito.verify(restTemplate, Mockito.never()).getForEntity(url, String.class);
  }
}