package com.example.springbootlab1.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UrlGeneratorTest {

  @SneakyThrows
  @Test
  void testGenerateNewUrl_AllParameters() {
    String lat = "51.5085";
    String lng = "-0.1257";
    String date = "2023-04-01";
    String formatted = "2";
    String expectedUrl = "https://api.sunrise-sunset.org/json?lat=51.5085&lng=-0.1257&date=2023-04-01&formatted=2";

    String actualUrl = UrlGenerator.generateNewUrl(lat, lng, date, formatted);

    assertEquals(expectedUrl, actualUrl);
  }

  @SneakyThrows
  @Test
  void testGenerateNewUrl_DateOnly() {
    String lat = "51.5085";
    String lng = "-0.1257";
    String date = "2023-04-01";
    String formatted = "null";
    String expectedUrl = "https://api.sunrise-sunset.org/json?lat=51.5085&lng=-0.1257&date=2023-04-01";

    String actualUrl = UrlGenerator.generateNewUrl(lat, lng, date, formatted);

    assertEquals(expectedUrl, actualUrl);
  }

  @SneakyThrows
  @Test
  void testGenerateNewUrl_FormattedOnly() {
    String lat = "51.5085";
    String lng = "-0.1257";
    String date = "null";
    String formatted = "2";
    String expectedUrl = "https://api.sunrise-sunset.org/json?lat=51.5085&lng=-0.1257&formatted=2";

    String actualUrl = UrlGenerator.generateNewUrl(lat, lng, date, formatted);

    assertEquals(expectedUrl, actualUrl);
  }

  @SneakyThrows
  @Test
  void testGenerateNewUrl_NoOptionalParameters() {
    String lat = "51.5085";
    String lng = "-0.1257";
    String date = "null";
    String formatted = "null";
    String expectedUrl = "https://api.sunrise-sunset.org/json?lat=51.5085&lng=-0.1257";

    String actualUrl = UrlGenerator.generateNewUrl(lat, lng, date, formatted);

    assertEquals(expectedUrl, actualUrl);
  }

  @Test
  void testGenerateNewUrl_WrongFormatException() {
    String lat = "null";
    String lng = "-0.1257";
    String date = "2023-04-01";
    String formatted = "2";

    assertThrows(WrongFormatException.class, () -> UrlGenerator.generateNewUrl(lat, lng, date, formatted));
  }
}
