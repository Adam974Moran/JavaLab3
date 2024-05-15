//package com.example.springbootlab1.data;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//import com.example.springbootlab1.cache.ApiResponseCache;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//
//@SpringBootTest
//class ApiResponseTest {
//
//  private RestTemplate restTemplate;
//  private ApiResponseCache apiResponseCache;
//
//  @BeforeEach
//  public void setUp() {
//    restTemplate = Mockito.mock(RestTemplate.class);
//    apiResponseCache = Mockito.mock(ApiResponseCache.class);
//    ApiResponse.apiResponseCache = apiResponseCache; // Заменяем статическую переменную на мок
//  }
//
////  @Test
////  void testGetJsonInString_CacheMiss() {
////    String url = "https://api.sunrise-sunset.org/json?lat=25&lng=25";
////    String expectedJson =
////        "{\"results\":{\"sunrise\":\"3:36:13 AM\",\"sunset\":\"4:56:31 PM\",\"solar_noon\":\"10:16:22 AM\",\"day_length\":\"13:20:18\",\"civil_twilight_begin\":\"3:12:54 AM\",\"civil_twilight_end\":\"5:19:50 PM\",\"nautical_twilight_begin\":\"2:43:42 AM\",\"nautical_twilight_end\":\"5:49:02 PM\",\"astronomical_twilight_begin\":\"2:13:31 AM\",\"astronomical_twilight_end\":\"6:19:13 PM\"},\"status\":\"OK\",\"tzid\":\"UTC\"}";
////    ResponseEntity<String> responseEntity = ResponseEntity.ok(expectedJson);
////
////    when(restTemplate.getForEntity(url, String.class)).thenReturn(responseEntity);
////    when(apiResponseCache.getFromCache(url)).thenReturn(null);
////
////    String actualJson = ApiResponse.getJsonInString(url);
////
////    assertEquals(expectedJson, actualJson);
////    Mockito.verify(apiResponseCache).addToCache(url, expectedJson);
////  }
//
//  @Test
//  void testGetJsonInString_CacheHit() {
//    String url = "https://api.sunrise-sunset.org/json";
//    String expectedJson = "{\"results\":\"\",\"status\":\"INVALID_REQUEST\"}";
//
//    when(apiResponseCache.getFromCache(url)).thenReturn(expectedJson);
//
//    String actualJson = ApiResponse.getJsonInString(url);
//
//    assertEquals(expectedJson, actualJson);
//    Mockito.verify(restTemplate, Mockito.never()).getForEntity(url, String.class);
//  }
//}