package com.example.springbootlab1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * The type Sunrise and sunset exception handler.
 */
@RestControllerAdvice
public class SunriseAndSunsetExceptionHandler {

  /**
   * Handle illegal argument exception response entity.
   *
   * @param e the e
   * @return the response entity
   */
  @ExceptionHandler({HttpClientErrorException.class})
  public ResponseEntity<Message> handleIllegalArgumentException(HttpClientErrorException e) {
    String errorMessage = "ERROR 400: Http Client Error";
    return ResponseEntity.status(e.getStatusCode()).body(new Message(errorMessage, e.getMessage()));
  }

  /**
   * Handle illegal argument exception response entity.
   *
   * @param e the e
   * @return the response entity
   */
  @ExceptionHandler({IllegalArgumentException.class})
  public ResponseEntity<Message> handleIllegalArgumentException(IllegalArgumentException e) {
    String errorMessage = "Error 404: Illegal Argument";
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new Message(errorMessage, e.getMessage()));
  }

  /**
   * Handle no resource found exception response entity.
   *
   * @param e the e
   * @return the response entity
   */
  @ExceptionHandler({NoHandlerFoundException.class})
  public ResponseEntity<Message> handleNoResourceFoundException(NoHandlerFoundException e) {
    String errorMessage = "ERROR 400: No Handler Found";
    return ResponseEntity.status(e.getStatusCode()).body(new Message(errorMessage, e.getMessage()));
  }

  /**
   * Handle method not supported exception response entity.
   *
   * @param e the e
   * @return the response entity
   */
  @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
  public ResponseEntity<Message> handleMethodNotSupportedException(
      HttpRequestMethodNotSupportedException e) {
    String errorMessage = "Error 405: Method Not Supported";
    return ResponseEntity.status(e.getStatusCode()).body(new Message(errorMessage, e.getMessage()));
  }

  /**
   * Handler runtime exception response entity.
   *
   * @param e the e
   * @return the response entity
   */
  @ExceptionHandler({RuntimeException.class})
  public ResponseEntity<Message> handlerRuntimeException(RuntimeException e) {
    String errorMessage = "Error 500: Runtime Exception";
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new Message(errorMessage, e.getMessage()));
  }

  /**
   * Handler runtime exception response entity.
   *
   * @param e the e
   * @return the response entity
   */
  @ExceptionHandler({MissingServletRequestParameterException.class})
  public ResponseEntity<Message> handlerRuntimeException(
      MissingServletRequestParameterException e) {
    String errorMessage = "Error 400: Bad Request";
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new Message(errorMessage, e.getMessage()));
  }

  /**
   * No resource found exception response entity.
   *
   * @param e the e
   * @return the response entity
   */
  @ExceptionHandler({NoResourceFoundException.class})
  public ResponseEntity<Message> noResourceFoundException(NoResourceFoundException e) {
    String errorMessage = "ERROR 404: No Resource Found";
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new Message(errorMessage, e.getMessage()));
  }

  /**
   * Exception response entity.
   *
   * @param e the e
   * @return the response entity
   */
  @ExceptionHandler({Exception.class})
  public ResponseEntity<Message> exception(Exception e) {
    String errorMessage = "Error 500: Unknown Exception";
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new Message(errorMessage, e.getMessage()));
  }

  private record Message(String message, String description) {
  }
}