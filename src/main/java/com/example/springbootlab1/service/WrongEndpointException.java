package com.example.springbootlab1.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Wrong endpoint exception.
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "There is no such address")
public class WrongEndpointException extends RuntimeException {
}
