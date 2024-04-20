package com.example.springbootlab1.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CountryTest {

  @Test
  void testGetId() {
    Country entity = new Country();
    Long expectedId = 123L;
    entity.setId(expectedId);
    Long actualId = entity.getId();
    assertEquals(expectedId, actualId, "getId should return the correct id");
  }

  @Test
  void testSetId() {
    Country entity = new Country();
    Long expectedId = 456L;
    entity.setId(expectedId);
    Long actualId = entity.getId();
    assertEquals(expectedId, actualId, "setId should set the correct id");
  }
}
