package com.example.springbootlab1.service;

import com.google.gson.Gson;

/**
 * The type Json formatter.
 */
public class JsonFormatter {
  private JsonFormatter() {
  }

  /**
   * Gets formatted json keys.
   *
   * @param jsonResponse the json response
   * @return the formatted json keys
   */
  public static String getFormattedJsonKeys(String jsonResponse) {
    Gson json = new Gson();
    SunriseAndSunsetKeys sunrise = json.fromJson(jsonResponse, SunriseAndSunsetKeys.class);
    return "{\"sunrise\": \"" + sunrise.results.sunrise + "\", \"sunset\": \""
        + sunrise.results.sunset + "\"}";
  }
}
