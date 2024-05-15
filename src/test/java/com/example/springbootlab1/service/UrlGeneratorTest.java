//package com.example.springbootlab1.service;
//
//import lombok.SneakyThrows;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvSource;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class UrlGeneratorTest {
//
//  @SneakyThrows
//  @ParameterizedTest
//  @CsvSource({
//      "51.5085, -0.1257, 2023-04-01, 2, https://api.sunrise-sunset.org/json?lat=51.5085&lng=-0.1257&date=2023-04-01&formatted=2",
//      "51.5085, -0.1257, 2023-04-01, null, https://api.sunrise-sunset.org/json?lat=51.5085&lng=-0.1257&date=2023-04-01",
//      "51.5085, -0.1257, null, 2, https://api.sunrise-sunset.org/json?lat=51.5085&lng=-0.1257&formatted=2",
//      "51.5085, -0.1257, null, null, https://api.sunrise-sunset.org/json?lat=51.5085&lng=-0.1257"
//  })
//  void testGenerateNewUrl(String lat, String lng, String date, String formatted, String expectedUrl) {
//    String actualUrl = UrlGenerator.generateNewUrl(lat, lng, date, formatted);
//    assertEquals(expectedUrl, actualUrl);
//  }
//
//  @Test
//  void testGenerateNewUrl_WrongFormatException() {
//    String lat = "null";
//    String lng = "-0.1257";
//    String date = "2023-04-01";
//    String formatted = "2";
//
//    assertThrows(WrongFormatException.class, () -> UrlGenerator.generateNewUrl(lat, lng, date, formatted));
//  }
//}
