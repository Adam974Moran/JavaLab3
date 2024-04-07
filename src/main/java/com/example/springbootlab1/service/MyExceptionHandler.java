package com.example.springbootlab1.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;

/**
 * The type My exception handler.
 */
@ControllerAdvice
public class MyExceptionHandler extends ResponseStatusExceptionHandler {

  /**
   * Handle wrong endpoint exception response entity.
   *
   * @return the response entity
   */
  @ExceptionHandler(WrongEndpointException.class)
  protected ResponseEntity<ExceptionMessage> handleWrongEndpointException() {
    return new ResponseEntity<>(new ExceptionMessage("There is no such endpoint"),
        HttpStatus.NOT_FOUND);
  }

  @Data
  @AllArgsConstructor
  private static class ExceptionMessage {
    private String message;
  }
}
