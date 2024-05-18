package com.example.springbootlab1.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

/**
 * The type Sunrise and sunset exception handler.
 */
@RestControllerAdvice
public class SunriseAndSunsetExceptionHandler {

  private static final Logger logger =
      LoggerFactory.getLogger(SunriseAndSunsetExceptionHandler.class);

  /**
   * Handle illegal argument exception response entity.
   *
   * @param e the e
   * @return the response entity
   */
  @ExceptionHandler({HttpClientErrorException.class})
  public ResponseEntity<Message> handleIllegalArgumentException(HttpClientErrorException e) {
    String errorMessage = "ERROR 400: Bad request";
    logger.error(errorMessage);
    return ResponseEntity.status(e.getStatusCode()).body(new Message(errorMessage, e.getMessage()));
  }

  /**
   * Exception response entity.
   *
   * @param e the e
   * @return the response entity
   */
  @ExceptionHandler({Exception.class})
  public ResponseEntity<Message> exception(Exception e) {
    String errorMessage = "Error 404: Not found";
    logger.error(errorMessage);
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new Message(errorMessage, e.getMessage()));
  }

  /**
   * No resource found exception response entity.
   *
   * @param e the e
   * @return the response entity
   */
//  @ExceptionHandler({NoResourceFoundException.class})
//  public ResponseEntity<Message> noResourceFoundException(NoResourceFoundException e) {
//    String errorMessage = "ERROR 404: No Resource Found";
//    logger.error(errorMessage);
//    return ResponseEntity.status(HttpStatus.NOT_FOUND)
//        .body(new Message(errorMessage, e.getMessage()));
//  }

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
    logger.error(errorMessage);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(errorMessage, e.getMessage()));
  }

  /**
   * Handler runtime exception response entity.
   *
   * @param e the e
   * @return the response entity
   */
  @ExceptionHandler({RuntimeException.class})
  public ResponseEntity<Message> handlerRuntimeException(RuntimeException e) {
    String errorMessage = "Error 500: Internal Server Error";
    logger.error(errorMessage);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new Message(errorMessage, e.getMessage()));
  }

  /**
   * The type Message.
   */
  public record Message(String message, String description) {

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
      return message;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
      return description;
    }
  }
}
