package com.example.springbootlab1.service;

import java.util.Objects;

/**
 * The type Url generator.
 */
public class UrlGenerator {
  private UrlGenerator() {
  }

  /**
   * Generate new url string.
   *
   * @param lat       the lat
   * @param lng       the lng
   * @param date      the date
   * @param formatted the formatted
   * @return the string
   * @throws WrongFormatException the wrong format exception
   */
  public static String generateNewUrl(String lat, String lng, String date, String formatted)
      throws WrongFormatException {
    if (Objects.equals(lat, "null") || Objects.equals(lng, "null")) {
      throw new WrongFormatException("Wrong format! Variables \"lat\" and \"lng\" are obligatory!");
    }

    String url = "https://api.sunrise-sunset.org/json";
    String newUrl;
    final String latS = "?lat=";
    final String lngS = "&lng=";

    if (!Objects.equals(date, "null") && !Objects.equals(formatted, "null")) {
      newUrl = url + latS + lat + lngS + lng + "&date=" + date + "&formatted=" + formatted;
    } else if (!Objects.equals(date, "null") && Objects.equals(formatted, "null")) {
      newUrl = url + latS + lat + lngS + lng + "&date=" + date;
    } else if (Objects.equals(date, "null") && !Objects.equals(formatted, "null")) {
      newUrl = url + latS + lat + lngS + lng + "&formatted=" + formatted;
    } else {
      newUrl = url + latS + lat + lngS + lng;
    }

    return newUrl;
  }
}
