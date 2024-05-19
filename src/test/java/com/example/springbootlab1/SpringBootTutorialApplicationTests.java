package com.example.springbootlab1;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {SpringBootLab1.class})
class SpringBootTutorialApplicationTests {

  @Test
  void contextLoads() {
    assertTrue(true, "This will always pass");
  }

}
