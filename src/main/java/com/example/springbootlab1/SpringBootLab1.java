package com.example.springbootlab1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

/**
 * The type Spring boot lab 1.
 */
@EnableCaching
@SpringBootApplication()
public class SpringBootLab1 {

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(SpringBootLab1.class, args);
  }

}
