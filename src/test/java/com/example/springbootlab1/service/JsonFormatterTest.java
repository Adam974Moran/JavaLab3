//package com.example.springbootlab1.service;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//class JsonFormatterTest {
//
//  @Test
//  void testGetFormattedJsonKeys() {
//    // Пример JSON-ответа
//    String jsonResponse = "{\"results\":{\"sunrise\":\"06:00\",\"sunset\":\"18:00\"}}";
//
//    // Ожидаемый результат
//    String expectedJson = "{\"sunrise\": \"06:00\", \"sunset\": \"18:00\"}";
//
//    // Вызов тестируемой функции
//    String actualJson = JsonFormatter.getFormattedJsonKeys(jsonResponse);
//
//    // Проверка результата
//    assertEquals(expectedJson, actualJson);
//  }
//}
