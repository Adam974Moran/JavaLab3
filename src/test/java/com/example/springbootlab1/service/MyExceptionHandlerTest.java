package com.example.springbootlab1.service;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class MyExceptionHandlerTest {

  @Test
  void testHandleWrongEndpointException() {
    MyExceptionHandler exceptionHandler = new MyExceptionHandler();
    ResponseEntity<MyExceptionHandler.ExceptionMessage> responseEntity = exceptionHandler.handleWrongEndpointException();

    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    assertEquals("There is no such endpoint", responseEntity.getBody().getMessage());
  }

  @Test
  void testEqualsAndHashCode() {
    MyExceptionHandler.ExceptionMessage message1 = new MyExceptionHandler.ExceptionMessage("Test message");
    MyExceptionHandler.ExceptionMessage message2 = new MyExceptionHandler.ExceptionMessage("Test message");
    MyExceptionHandler.ExceptionMessage message3 = new MyExceptionHandler.ExceptionMessage("Different message");

    // Проверяем, что два объекта с одинаковым сообщением равны
    assertEquals(message1, message2);
    assertEquals(message1.hashCode(), message2.hashCode());

    // Проверяем, что объекты с разными сообщениями не равны
    assertNotEquals(message1, message3);
    assertNotEquals(message1.hashCode(), message3.hashCode());
  }

  @Test
  void testToString() {
    MyExceptionHandler.ExceptionMessage message = new MyExceptionHandler.ExceptionMessage("Test message");
    String expectedToString = "MyExceptionHandler.ExceptionMessage(message=Test message)";

    assertEquals(expectedToString, message.toString());
  }
}
