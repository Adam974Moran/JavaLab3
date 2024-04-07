package com.example.springbootlab1.service;

/**
 * The type Wrong format exception.
 */
public class WrongFormatException extends Exception {
  private final String exceptionMessage;

  /**
   * Instantiates a new Wrong format exception.
   *
   * @param message the message
   */
  public WrongFormatException(String message) {
    this.exceptionMessage = message;
  }

  /**
   * Gets exception message.
   *
   * @return the exception message
   */
  public String getExceptionMessage() {
    return this.exceptionMessage;
  }
}
