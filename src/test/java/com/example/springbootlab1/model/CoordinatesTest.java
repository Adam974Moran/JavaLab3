package com.example.springbootlab1.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CoordinatesTest {

  @Test
  void testGetLat() {
    Coordinates coordinates = new Coordinates();
    coordinates.setLat("40.7128");
    assertEquals("40.7128", coordinates.getLat());
  }

  @Test
  void testGetLng() {
    Coordinates coordinates = new Coordinates();
    coordinates.setLng("-74.0060");
    assertEquals("-74.0060", coordinates.getLng());
  }
}
