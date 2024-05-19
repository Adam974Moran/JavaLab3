package com.example.springbootlab1.exception;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SunriseAndSunsetExceptionHandlerTest {



  @Test
  void testHandleIllegalArgumentException() {
    SunriseAndSunsetExceptionHandler exceptionHandler = new SunriseAndSunsetExceptionHandler();
    HttpClientErrorException e = new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Bad request");
    ResponseEntity<SunriseAndSunsetExceptionHandler.Message> response = exceptionHandler.handleIllegalArgumentException(e);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("ERROR 400: Bad request", response.getBody().getMessage());
    assertEquals("400 Bad request", response.getBody().getDescription());
  }

  @Test
  void testException() {
    SunriseAndSunsetExceptionHandler exceptionHandler = new SunriseAndSunsetExceptionHandler();
    Exception e = new Exception("Not found");
    ResponseEntity<SunriseAndSunsetExceptionHandler.Message> response = exceptionHandler.exception(e);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Error 404: Not found", response.getBody().getMessage());
    assertEquals("Not found", response.getBody().getDescription());
  }


  @Test
  void testHandleMethodNotSupportedException() {
    SunriseAndSunsetExceptionHandler exceptionHandler = new SunriseAndSunsetExceptionHandler();
    HttpRequestMethodNotSupportedException e = new HttpRequestMethodNotSupportedException("Method Not Supported");
    ResponseEntity<SunriseAndSunsetExceptionHandler.Message> response = exceptionHandler.handleMethodNotSupportedException(e);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Error 405: Method Not Supported", response.getBody().getMessage());
    assertEquals("Request method 'Method Not Supported' is not supported", response.getBody().getDescription());
  }

  @Test
  void testHandlerRuntimeException() {
    SunriseAndSunsetExceptionHandler exceptionHandler = new SunriseAndSunsetExceptionHandler();
    RuntimeException e = new RuntimeException("Internal Server Error");
    ResponseEntity<SunriseAndSunsetExceptionHandler.Message> response = exceptionHandler.handlerRuntimeException(e);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("Error 500: Internal Server Error", response.getBody().getMessage());
    assertEquals("Internal Server Error", response.getBody().getDescription());
  }
}
