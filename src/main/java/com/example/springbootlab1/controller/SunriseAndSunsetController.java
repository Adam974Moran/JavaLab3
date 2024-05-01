package com.example.springbootlab1.controller;

import com.example.springbootlab1.data.ApiResponse;
import com.example.springbootlab1.model.Coordinates;
import com.example.springbootlab1.model.Country;
import com.example.springbootlab1.model.Date;
import com.example.springbootlab1.service.CoordinatesRepositoryService;
import com.example.springbootlab1.service.CountryRepositoryService;
import com.example.springbootlab1.service.DateRepositoryService;
import com.example.springbootlab1.service.JsonFormatter;
import com.example.springbootlab1.service.RequestsCounterService;
import com.example.springbootlab1.service.UrlGenerator;
import com.example.springbootlab1.service.WrongFormatException;
import com.google.gson.JsonObject;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Sunrise and sunset controller.
 */
@RestController
public class SunriseAndSunsetController {
  private static final Logger logger = LoggerFactory.getLogger(SunriseAndSunsetController.class);
  private static final String COUNTRY_NOT_FOUND = "Country not found";
  private static final String COORDINATES_NOT_FOUND = "Coordinates not found";

  private final CountryRepositoryService countryRepositoryService;
  private final CoordinatesRepositoryService coordinatesRepositoryService;
  private final DateRepositoryService dateRepositoryService;
  private final RequestsCounterService requestsCounterService;

  /**
   * Instantiates a new Sunrise and sunset controller.
   *
   * @param countryRepositoryService     the country repository service
   * @param coordinatesRepositoryService the coordinates' repository service
   * @param dateRepositoryService        the date repository service
   */
  public SunriseAndSunsetController(CountryRepositoryService countryRepositoryService,
                                    CoordinatesRepositoryService coordinatesRepositoryService,
                                    DateRepositoryService dateRepositoryService,
                                    RequestsCounterService requestsCounterService) {
    this.countryRepositoryService = countryRepositoryService;
    this.coordinatesRepositoryService = coordinatesRepositoryService;
    this.dateRepositoryService = dateRepositoryService;
    this.requestsCounterService = requestsCounterService;
  }

  /**
   * Add coordinates string.
   *
   * @param countryName the country name
   * @param lat         the lat
   * @param lng         the lng
   * @return the string
   * @throws IllegalAccessException the illegal access exception
   */
  @PostMapping("/sunInfo/country/{countryName}/coordinates")
  public String addCoordinates(@PathVariable String countryName,
                               @RequestParam(value = "lat", defaultValue = "null") String lat,
                               @RequestParam(value = "lng", defaultValue = "null") String lng)
      throws IllegalAccessException {
    requestsCounterService.increment();
    logger.info("Processing post request \"/sunInfo/country/countryName/coordinates\"");
    Country country = countryRepositoryService.findByCountryName(countryName);
    if (country == null) {
      country = new Country();
      country.setCountryName(countryName);
      countryRepositoryService.save(country);
    }
    if (Objects.equals(lng, "null") || Objects.equals(lat, "null")) {
      throw new IllegalAccessException(COORDINATES_NOT_FOUND);
    }
    Coordinates coordinates = new Coordinates();
    coordinates.setLat(lat);
    coordinates.setLng(lng);
    coordinates.setCountry(country);
    coordinatesRepositoryService.save(coordinates);
    return "Created successfully!";
  }


  /**
   * Bulk country insert response entity.
   *
   * @param countries the countries
   * @return the response entity
   */
  @PostMapping("/sunInfo/severalCountries")
  public ResponseEntity<String> bulkCountryInsert(@RequestBody List<Country> countries) {
    requestsCounterService.increment();
    for (Country country : countries) {
      countryRepositoryService.saveCountryWithCoordinates(country);
    }
    return ResponseEntity.ok("Country has been saved");
  }


  /**
   * Gets sunrise and sunset info.
   *
   * @param lat       the lat
   * @param lng       the lng
   * @param date      the date
   * @param formatted the formatted
   * @return the sunrise and sunset info
   */
  @GetMapping(value = "/sunInfo", produces = "application/json")
  public String getSunriseAndSunsetInfo(
      @RequestParam(value = "lat", defaultValue = "null") String lat,
      @RequestParam(value = "lng", defaultValue = "null") String lng,
      @RequestParam(value = "date", defaultValue = "null") String date,
      @RequestParam(value = "formatted", defaultValue = "null") String formatted) {
    requestsCounterService.increment();
    logger.info("Processing get request \"/sunInfo\"");
    String url;
    try {
      url = UrlGenerator.generateNewUrl(lat, lng, date, formatted);
    } catch (WrongFormatException w) {
      return w.getExceptionMessage();
    }

    //"CREATING" DATE AND COORDINATES
    if (Objects.equals(date, "null") || Objects.equals(date, "today")) {
      date = LocalDate.now().toString();
    }
    Coordinates coordinates = null;
    List<Coordinates> coordinatesToCheck = coordinatesRepositoryService.findAll();
    for (Coordinates coordinate : coordinatesToCheck) {
      if (Objects.equals(coordinate.getLat(), lat) && Objects.equals(coordinate.getLng(), lng)) {
        coordinates = coordinate;
      }
    }
    Date coordinatesDate = dateRepositoryService.findByDate(date); //Ищем дату в репозитории
    if (Objects.equals(coordinates, null)) { //Если координаты не существует
      coordinates = new Coordinates();
      coordinates.setLat(lat);
      coordinates.setLng(lng);
      coordinatesRepositoryService.save(coordinates);

      //Проверяем нужно ли дополнительно сохранять и дату
      if (Objects.equals(coordinatesDate, null)) {
        coordinatesDate = new Date();
        coordinatesDate.setCoordinatesDate(date);
      }
      coordinatesDate.getCoordinates().add(coordinates);
      dateRepositoryService.save(coordinatesDate);
      coordinates.getDates().add(coordinatesDate);
    } else if (!Objects.equals(coordinatesDate, null)
        && !coordinates.getDates().contains(coordinatesDate)) {
      //Координата существует и существует дата, то мы проверяем привязвны
      // ли эти данные к друг гругу
      coordinatesDate.getCoordinates().add(coordinates);
      dateRepositoryService.save(coordinatesDate);
      coordinates.getDates().add(coordinatesDate);
      coordinatesRepositoryService.save(coordinates);
    } else if (Objects.equals(coordinatesDate, null)) {
      coordinatesDate = new Date();
      coordinatesDate.setCoordinatesDate(date);
      coordinatesDate.getCoordinates().add(coordinates);
      dateRepositoryService.save(coordinatesDate);
      coordinates.getDates().add(coordinatesDate);
      coordinatesRepositoryService.save(coordinates);
    }

    return JsonFormatter.getFormattedJsonKeys(ApiResponse.getJsonInString(url));
  }

  /**
   * Gets coordinates by country.
   *
   * @param countryName the country name
   * @param date        the date
   * @return the coordinates by country
   * @throws IllegalAccessException the illegal access exception
   */
  @GetMapping("/sunInfo/country/{countryName}")
  public String getCoordinatesByCountry(@PathVariable String countryName,
                                        @RequestParam(value = "date", defaultValue = "null")
                                        String date)
      throws IllegalAccessException {
    requestsCounterService.increment();
    logger.info("Processing get request \"/sunInfo/country/{countryName}\"");
    StringBuilder results = new StringBuilder("Results(");
    Country country = countryRepositoryService.findByCountryName(countryName);
    if (country == null) {

      throw new IllegalAccessException(COUNTRY_NOT_FOUND);
    }
    results.append(country.getCountryName()).append("):\n");

    List<Coordinates> coordinatesList =
        countryRepositoryService.getCoordinatesByCountryName(countryName);
    for (Coordinates coordinate : coordinatesList) {
      String url;
      try {
        url = UrlGenerator.generateNewUrl(coordinate.getLat(), coordinate.getLng(), date, null);
      } catch (WrongFormatException w) {
        return w.getExceptionMessage();
      }
      results.append(JsonFormatter.getFormattedJsonKeys(ApiResponse.getJsonInString(url)))
          .append("\n");
    }
    return results.toString();
  }

  /**
   * Gets all countries' info.
   *
   * @return the all countries info
   */
  @GetMapping(value = "/allCountriesInfo", produces = "application/json")
  public ResponseEntity<List<Country>> getAllCountriesInfo() {
    requestsCounterService.increment();
    logger.info("Processing get request \"/allCountriesInfo\"");
    return new ResponseEntity<>(countryRepositoryService.findAll(), HttpStatus.OK);
  }

  /**
   * Gets history by date.
   *
   * @return the history by date
   */
  @GetMapping(value = "/historyByDate", produces = "application/json")
  public ResponseEntity<List<Date>> getHistoryByDate() {
    requestsCounterService.increment();
    logger.info("Processing get request \"/historyByDate\"");
    List<Date> datesList = dateRepositoryService.findAll();
    return new ResponseEntity<>(datesList, HttpStatus.OK);
  }

  /**
   * Gets history by coordinates.
   *
   * @return the history by coordinates
   */
  @GetMapping(value = "/historyByCoordinates", produces = "application/json")
  public ResponseEntity<List<Coordinates>> getHistoryByCoordinates() {
    requestsCounterService.increment();
    logger.info("Processing get request \"/historyByCoordinates\"");
    List<Coordinates> coordinatesList = coordinatesRepositoryService.findAll();
    return new ResponseEntity<>(coordinatesList, HttpStatus.OK);
  }

  //END POINT FOR LAB 3

  /**
   * Gets sunset and sunrise information by date.
   *
   * @param dateId the date id
   * @return the sunset and sunrise information by date
   */
  @GetMapping(value = "/sunInfo/date", produces = "application/json")
  public Object getSunsetAndSunriseInformationByDate(
      @RequestParam(value = "dateId", defaultValue = "null") Long dateId) {
    requestsCounterService.increment();
    logger.info("Processing get request \"/sunInfo/date\"");
    StringBuilder result = new StringBuilder("{\"coordinatesDate\": \"").append(
        dateRepositoryService.findDateById(dateId).getCoordinatesDate()).append("\",\"result\":[");
    Set<Coordinates> coordinatesSet = dateRepositoryService.getCoordinatesByDateId(dateId);
    for (Coordinates coordinates : coordinatesSet) {
      String url;
      try {
        url = UrlGenerator.generateNewUrl(coordinates.getLat(), coordinates.getLng(),
            dateRepositoryService.findDateById(dateId).getCoordinatesDate(), null);
      } catch (WrongFormatException w) {
        return w.getExceptionMessage();
      }
      result.append("\"sunInfo\":")
          .append(JsonFormatter.getFormattedJsonKeys(ApiResponse.getJsonInString(url))).append(",");
    }
    result.deleteCharAt(result.length() - 1);
    result.append("]");
    return ResponseEntity.ok(result);
  }


  //ENDPOINT FOR REQUESTS COUNTER
  @GetMapping("/getAmountOfRequests")
  public ResponseEntity<String> getAmountOfRequests() {
    requestsCounterService.increment();
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("count", requestsCounterService.getCount());
    return ResponseEntity.ok(jsonObject.toString());
  }


  /**
   * Update country name string.
   *
   * @param countryName    the country name
   * @param newCountryName the new country name
   * @return the string
   * @throws IllegalAccessException the illegal access exception
   */
  //PUT
  @PutMapping("/country/{countryName}/{newCountryName}")
  public String updateCountryName(@PathVariable String countryName,
                                  @PathVariable String newCountryName)
      throws IllegalAccessException {
    requestsCounterService.increment();
    logger.info("Processing put request \"/country/{countryName}/{newCountryName}\"");
    Country country = countryRepositoryService.findByCountryName(countryName);
    Country countryCheck = countryRepositoryService.findByCountryName(newCountryName);
    if (Objects.equals(country, null) || !Objects.equals(countryCheck, null)) {
      throw new IllegalAccessException(COUNTRY_NOT_FOUND);
    } else {
      country.setCountryName(newCountryName);
      countryRepositoryService.save(country);
      return "Country name updated successfully!";
    }
  }

  /**
   * Update country name string.
   *
   * @param coordinatesId the coordinates id
   * @param lat           the lat
   * @param lng           the lng
   * @return the string
   * @throws IllegalAccessException the illegal access exception
   */
  @PutMapping("/coordinates/{coordinatesId}")
  public String updateCountryName(@PathVariable Long coordinatesId,
                                  @RequestParam(value = "lat", defaultValue = "null") String lat,
                                  @RequestParam(value = "lng", defaultValue = "null") String lng)
      throws IllegalAccessException {
    requestsCounterService.increment();
    logger.info("Processing put request \"/coordinates/{coordinatesId}\"");
    Coordinates coordinates = coordinatesRepositoryService.findCoordinatesById(coordinatesId);
    if (Objects.equals(coordinates, null) || Objects.equals(lat, "null")
        || Objects.equals(lng, "null")) {
      throw new IllegalAccessException(COORDINATES_NOT_FOUND);
    } else {
      coordinates.setLat(lat);
      coordinates.setLng(lng);
      coordinatesRepositoryService.save(coordinates);
      return "Coordinates updated successfully!";
    }
  }

  /**
   * Update date value string.
   *
   * @param dateId       the date id
   * @param newDateValue the new date value
   * @return the string
   * @throws IllegalAccessException the illegal access exception
   */
  @PutMapping("/date/{dateId}/{newDateValue}")
  public String updateDateValue(@PathVariable Long dateId, @PathVariable String newDateValue)
      throws IllegalAccessException {
    requestsCounterService.increment();
    logger.info("Processing put request \"/date/{dateId}/{newDateValue}\"");
    Date dateToUpdate = dateRepositoryService.findDateById(dateId);
    Date dateToCheck = dateRepositoryService.findByDate(newDateValue);
    if (Objects.equals(dateToUpdate, null) || !Objects.equals(dateToCheck, null)) {
      throw new IllegalAccessException("Date not found");
    } else {
      dateToUpdate.setCoordinatesDate(newDateValue);
      dateRepositoryService.save(dateToUpdate);
      return "Country name updated successfully!";
    }
  }

  /**
   * Delete coordinates string.
   *
   * @param countryName the country name
   * @return the string
   * @throws IllegalAccessException the illegal access exception
   */
  //DELETE
  @DeleteMapping("/sunInfo/country/{countryName}")
  public String deleteCoordinates(@PathVariable String countryName)
      throws IllegalAccessException {
    requestsCounterService.increment();
    logger.info("Processing delete request \"/sunInfo/country/{countryName}\"");
    Country country = countryRepositoryService.findByCountryName(countryName);
    if (country == null) {
      throw new IllegalAccessException(COUNTRY_NOT_FOUND);
    }
    List<Coordinates> coordinates =
        countryRepositoryService.getCoordinatesByCountryName(countryName);
    coordinatesRepositoryService.deleteAll(coordinates);
    countryRepositoryService.delete(country);
    return "Deleted successfully!";
  }

  /**
   * Delete coordinates string.
   *
   * @param countryName   the country name
   * @param coordinatesId the coordinates id
   * @return the string
   * @throws IllegalAccessException the illegal access exception
   */
  @DeleteMapping("/sunInfo/country/{countryName}/coordinates/{coordinatesId}")
  public String deleteCoordinates(@PathVariable String countryName,
                                  @PathVariable Long coordinatesId) throws IllegalAccessException {
    requestsCounterService.increment();
    logger.info(
        "Processing delete request \"/sunInfo/country/{countryName}/coordinates/{coordinatesId}\"");
    Country country = countryRepositoryService.findByCountryName(countryName);
    if (country == null) {
      throw new IllegalAccessException(COUNTRY_NOT_FOUND);
    }
    List<Coordinates> coordinates =
        countryRepositoryService.getCoordinatesByCountryName(countryName);
    for (Coordinates coordinate : coordinates) {
      if (coordinate.checkId(coordinatesId)) {
        coordinatesRepositoryService.deleteById(coordinatesId);
        return "Deleted successfully!";
      }
    }
    throw new IllegalAccessException(COORDINATES_NOT_FOUND);
  }

  /**
   * Delete by date string.
   *
   * @param dateId the date id
   * @return the string
   * @throws IllegalAccessException the illegal access exception
   */
  @DeleteMapping("/history/date/{dateId}")
  public String deleteByDate(@PathVariable Long dateId)
      throws IllegalAccessException {
    requestsCounterService.increment();
    logger.info("Processing delete request \"/history/date/{dateId}\"");
    Date removableDate = dateRepositoryService.findDateById(dateId);
    if (Objects.equals(removableDate, null)) {
      throw new IllegalAccessException("Date not found");
    }
    Set<Coordinates> coordinates = dateRepositoryService.getCoordinatesByDateId(dateId);
    for (Coordinates coordinate : coordinates) {
      if (coordinate.getDates().size() == 1 && Objects.equals(coordinate.getCountry(), null)) {
        removableDate.getCoordinates().remove(coordinate);
        coordinate.getDates().remove(removableDate);
        dateRepositoryService.save(removableDate);
        coordinatesRepositoryService.deleteById(coordinate.getId());
      } else {
        removableDate.getCoordinates().remove(coordinate);
        coordinate.getDates().remove(removableDate);
        dateRepositoryService.save(removableDate);
      }
    }
    dateRepositoryService.deleteById(removableDate.getId());
    return "Deleted successfully";
  }

  /**
   * Delete by coordinates string.
   *
   * @param coordinatesId the coordinates id
   * @return the string
   * @throws IllegalAccessException the illegal access exception
   */
  @DeleteMapping("/history/coordinates/{coordinatesId}")
  public String deleteByCoordinates(@PathVariable Long coordinatesId)
      throws IllegalAccessException {
    requestsCounterService.increment();
    logger.info("Processing delete request \"/history/coordinates/{coordinatesId}\"");
    Coordinates removableCoordinates =
        coordinatesRepositoryService.findCoordinatesById(coordinatesId);
    if (Objects.equals(removableCoordinates, null)) {
      throw new IllegalAccessException(COORDINATES_NOT_FOUND);
    }
    List<Date> dateList = coordinatesRepositoryService.getDateByCoordinatesId(coordinatesId);
    for (Date date : dateList) {
      if (date.getCoordinates().size() == 1) {
        removableCoordinates.getDates().remove(date);
        date.getCoordinates().remove(removableCoordinates);
        coordinatesRepositoryService.save(removableCoordinates);
        dateRepositoryService.deleteById(date.getId());
      } else {
        removableCoordinates.getDates().remove(date);
        date.getCoordinates().remove(removableCoordinates);
        coordinatesRepositoryService.save(removableCoordinates);
      }
    }
    coordinatesRepositoryService.deleteById(removableCoordinates.getId());
    return "Deleted successfully";
  }
}
