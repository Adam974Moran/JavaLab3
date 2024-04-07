package com.example.springbootlab1.service;

import com.example.springbootlab1.model.Coordinates;
import com.example.springbootlab1.model.Date;
import com.google.gson.Gson;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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

  /**
   * Gets formatted json for dates.
   *
   * @param datesList the dates list
   * @return the formatted json for dates
   */
  public static StringBuilder getFormattedJsonForDates(List<Date> datesList) {
    StringBuilder result = new StringBuilder("[ ");
    for (Date date : datesList) {
      result.append("{\n  \"id\": ").append(date.getId()).append(", \n  \"date\": \"")
          .append(date.getCoordinatesDate()).append("\",\n  \"coordinates\":[");
      Set<Coordinates> coordinatesSet = date.getCoordinates();
      for (Coordinates coordinates : coordinatesSet) {
        result.append(" {\n    \"id\": ").append(coordinates.getId()).append(", \n    \"lat\": \"")
            .append(coordinates.getLat()).append("\", \n    \"lng\": \"")
            .append(coordinates.getLng()).append("\"\n   },");
      }
      result.deleteCharAt(result.length() - 1).append(" ]\n}, ");
    }
    if (datesList.isEmpty()) {
      result.deleteCharAt(result.length() - 1);
    }
    result.append("]");
    return result;
  }

  /**
   * Gets formatted json for coordinates.
   *
   * @param coordinatesList the coordinates list
   * @return the formatted json for coordinates
   */
  public static StringBuilder getFormattedJsonForCoordinates(List<Coordinates> coordinatesList) {
    StringBuilder result = new StringBuilder("[ ");
    for (Coordinates coordinates : coordinatesList) {
      Set<Date> dateSet = coordinates.getDates();
      if (Objects.equals(dateSet.size(), 0)) {
        continue;
      }
      result.append("{\n  \"id\": ").append(coordinates.getId()).append(", \n  \"lat\": \"")
          .append(coordinates.getLat()).append("\", \n  \"lng\": \"").append(coordinates.getLng())
          .append("\",\n  \"dates\":[");
      for (Date date : dateSet) {
        result.append(" {\n    \"id\": ").append(date.getId()).append(", \n    \"date\": \"")
            .append(date.getCoordinatesDate()).append("\"\n   },");
      }
      result.deleteCharAt(result.length() - 1).append(" ]\n}, ");
    }
    if (result.length() == 2) {
      result.deleteCharAt(result.length() - 1);
    }
    result.append("]");
    return result;
  }
}
