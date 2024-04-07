package com.example.springbootlab1.controller;

import com.example.springbootlab1.data.ApiResponse;
import com.example.springbootlab1.model.Coordinates;
import com.example.springbootlab1.model.Country;
import com.example.springbootlab1.model.Date;
import com.example.springbootlab1.service.CoordinatesRepositoryService;
import com.example.springbootlab1.service.CountryRepositoryService;
import com.example.springbootlab1.service.DateRepositoryService;
import com.example.springbootlab1.service.JsonFormatter;
import com.example.springbootlab1.service.UrlGenerator;
import com.example.springbootlab1.service.WrongFormatException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Этот класс представляет собой главный контроллер сервиса.
 */
@RestController
public class SunriseAndSunsetController {
  private static final Logger logger = LoggerFactory.getLogger(SunriseAndSunsetController.class);
  private static final String DELETE_METHOD = "DELETE";

  private final CountryRepositoryService countryRepositoryService;
  private final CoordinatesRepositoryService coordinatesRepositoryService;
  private final DateRepositoryService dateRepositoryService;

  /**
   * Конструктор контроллера.
   *
   * @param countryRepositoryService     - сервис для стран
   * @param coordinatesRepositoryService - сервис для координат
   * @param dateRepositoryService        - сервис для дат
   */
  public SunriseAndSunsetController(CountryRepositoryService countryRepositoryService,
                                    CoordinatesRepositoryService coordinatesRepositoryService,
                                    DateRepositoryService dateRepositoryService) {
    this.countryRepositoryService = countryRepositoryService;
    this.coordinatesRepositoryService = coordinatesRepositoryService;
    this.dateRepositoryService = dateRepositoryService;
  }

  /**
   * Этот метод сохраняет координаты по стране в БД.
   *
   * @param countryName - название страны
   * @param lat         - широта
   * @param lng         - долгота
   * @return - возвращает строку оповещающую об успешном завершении запроса
   * @throws NoHandlerFoundException - выбрасывает исключение, если произошла
   *                                 ошибка при вводе данных пользователем
   */
  @PostMapping("/sunInfo/country/{countryName}/coordinates")
  public String addCoordinates(@PathVariable String countryName,
                               @RequestParam(value = "lat", defaultValue = "null") String lat,
                               @RequestParam(value = "lng", defaultValue = "null") String lng)
      throws NoHandlerFoundException {
    logger.info("Processing post request \"/sunInfo/country/countryName/coordinates\"");
    Country country = countryRepositoryService.findByCountryName(countryName);
    if (country == null) {
      country = new Country();
      country.setCountryName(countryName);
      countryRepositoryService.save(country);
    }
    if (Objects.equals(lng, "null") || Objects.equals(lat, "null")) {
      throw new NoHandlerFoundException("POST", "/sunInfo/country/{countryName}/coordinates",
          new HttpHeaders());
    }
    Coordinates coordinates = new Coordinates();
    coordinates.setLat(lat);
    coordinates.setLng(lng);
    coordinates.setCountry(country);
    coordinatesRepositoryService.save(coordinates);
    return "Created successfully!";
  }

  /**
   * Метод для исключения.
   */
  @PostMapping(value = "/**")
  public void defaultPostMethod() {
    throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
  }


  /**
   * Метод, отправляющий запрос на удаленный API сервис, для получения
   * восхода и захода солнца.
   *
   * @param lat       - широта
   * @param lng       - долгота
   * @param date      - дата восхода и захода
   * @param formatted - формат вывода времени
   * @return - возвращает json
   */
  @GetMapping(value = "/sunInfo", produces = "application/json")
  public String getSunriseAndSunsetInfo(
      @RequestParam(value = "lat", defaultValue = "null") String lat,
      @RequestParam(value = "lng", defaultValue = "null") String lng,
      @RequestParam(value = "date", defaultValue = "null") String date,
      @RequestParam(value = "formatted", defaultValue = "null") String formatted) {
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
   * Метод, выдающий координаты по стране.
   *
   * @param countryName - имя страны
   * @param date        - дата
   * @return - возвращает json c координатами страны
   * @throws NoHandlerFoundException - выбрасывает исключение, если произошла
   *                                 ошибка при вводе данных пользователем
   */
  @GetMapping("/sunInfo/country/{countryName}")
  public String getCoordinatesByCountry(@PathVariable String countryName,
                                        @RequestParam(value = "date", defaultValue = "null")
                                        String date) throws NoHandlerFoundException {
    logger.info("Processing get request \"/sunInfo/country/{countryName}\"");
    StringBuilder results = new StringBuilder("Results(");
    Country country = countryRepositoryService.findByCountryName(countryName);
    if (country == null) {
      throw new NoHandlerFoundException("GET", "/sunInfo/country/{countryName}", new HttpHeaders());
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

  @GetMapping(value = "/allCountriesInfo", produces = "application/json")
  public ResponseEntity<List<Country>> getAllCountriesInfo() {
    logger.info("Processing get request \"/allCountriesInfo\"");
    return new ResponseEntity<>(countryRepositoryService.findAll(), HttpStatus.OK);
  }

  /**
   * Метод, возвразщающий историю запросов по дате.
   *
   * @return - возвращает список дат и их координаты
   */
  @GetMapping(value = "/historyByDate", produces = "application/json")
  public ResponseEntity<List<Date>> getHistoryByDate() {
    logger.info("Processing get request \"/historyByDate\"");
    List<Date> datesList = dateRepositoryService.findAll();
    return new ResponseEntity<>(datesList, HttpStatus.OK);
  }

  /**
   * Метод, возвращающий историю запросов по координатам.
   *
   * @return - возвращает список координат и их даты
   */
  @GetMapping(value = "/historyByCoordinates", produces = "application/json")
  public ResponseEntity<List<Coordinates>> getHistoryByCoordinates() {
    logger.info("Processing get request \"/historyByCoordinates\"");
    List<Coordinates> coordinatesList = coordinatesRepositoryService.findAll();
    return new ResponseEntity<>(coordinatesList, HttpStatus.OK);
  }

  //END POINT FOR LAB 3

  @GetMapping(value = "/sunInfo/date", produces = "application/json")
  public Object getSunsetAndSunriseInformationByDate(
      @RequestParam(value = "dateId", defaultValue = "null") Long dateId) {
    logger.info("Processing get request \"/sunInfo/date\"");
    StringBuilder result = new StringBuilder("{\"coordinatesDate\": \"").append(
        dateRepositoryService.findDateById(dateId).getCoordinatesDate()).append("\",\"result\":[");
    List<Coordinates> coordinatesSet = dateRepositoryService.getCoordinatesByDateId(dateId);
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

  @GetMapping(value = "/**")
  public void defaultGetMethod() {
    throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
  }

  //PUT
  @PutMapping("/country/{countryName}/{newCountryName}")
  public String updateCountryName(@PathVariable String countryName,
                                  @PathVariable String newCountryName)
      throws NoHandlerFoundException {
    logger.info("Processing put request \"/country/{countryName}/{newCountryName}\"");
    Country country = countryRepositoryService.findByCountryName(countryName);
    Country countryCheck = countryRepositoryService.findByCountryName(newCountryName);
    if (Objects.equals(country, null) || !Objects.equals(countryCheck, null)) {
      throw new NoHandlerFoundException("PUT", "/country/{countryName}/{newCountryName}",
          new HttpHeaders());
    } else {
      country.setCountryName(newCountryName);
      countryRepositoryService.save(country);
      return "Country name updated successfully!";
    }
  }

  @PutMapping("/coordinates/{coordinatesId}")
  public String updateCountryName(@PathVariable Long coordinatesId,
                                  @RequestParam(value = "lat", defaultValue = "null") String lat,
                                  @RequestParam(value = "lng", defaultValue = "null") String lng)
      throws NoHandlerFoundException {
    logger.info("Processing put request \"/coordinates/{coordinatesId}\"");
    Coordinates coordinates = coordinatesRepositoryService.findCoordinatesById(coordinatesId);
    if (Objects.equals(coordinates, null) || Objects.equals(lat, "null")
        || Objects.equals(lng, "null")) {
      throw new NoHandlerFoundException("PUT", "/coordinates/{coordinatesId}", new HttpHeaders());
    } else {
      coordinates.setLat(lat);
      coordinates.setLng(lng);
      coordinatesRepositoryService.save(coordinates);
      return "Coordinates updated successfully!";
    }
  }

  @PutMapping("/date/{dateId}/{newDateValue}")
  public String updateDateValue(@PathVariable Long dateId, @PathVariable String newDateValue)
      throws NoHandlerFoundException {
    logger.info("Processing put request \"/date/{dateId}/{newDateValue}\"");
    Date dateToUpdate = dateRepositoryService.findDateById(dateId);
    Date dateToCheck = dateRepositoryService.findByDate(newDateValue);
    if (Objects.equals(dateToUpdate, null) || !Objects.equals(dateToCheck, null)) {
      throw new NoHandlerFoundException("PUT", "/date/{dateId}/{newDateValue}", new HttpHeaders());
    } else {
      dateToUpdate.setCoordinatesDate(newDateValue);
      dateRepositoryService.save(dateToUpdate);
      return "Country name updated successfully!";
    }
  }

  @PutMapping(value = "/**")
  public void defaultPutMethod() {
    throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
  }

  //DELETE
  @DeleteMapping("/sunInfo/country/{countryName}")
  public String deleteCoordinates(@PathVariable String countryName) throws NoHandlerFoundException {
    logger.info("Processing delete request \"/sunInfo/country/{countryName}\"");
    Country country = countryRepositoryService.findByCountryName(countryName);
    if (country == null) {
      throw new NoHandlerFoundException(DELETE_METHOD, "/sunInfo/country/{countryName}",
          new HttpHeaders());
    }
    List<Coordinates> coordinates =
        countryRepositoryService.getCoordinatesByCountryName(countryName);
    coordinatesRepositoryService.deleteAll(coordinates);
    countryRepositoryService.delete(country);
    return "Deleted successfully!";
  }

  @DeleteMapping("/sunInfo/country/{countryName}/coordinates/{coordinatesId}")
  public String deleteCoordinates(@PathVariable String countryName,
                                  @PathVariable Long coordinatesId) throws NoHandlerFoundException {
    logger.info(
        "Processing delete request \"/sunInfo/country/{countryName}/coordinates/{coordinatesId}\"");
    Country country = countryRepositoryService.findByCountryName(countryName);
    if (country == null) {
      throw new NoHandlerFoundException(DELETE_METHOD,
          "/sunInfo/country/{countryName}/coordinates/{coordinatesId}", new HttpHeaders());
    }
    List<Coordinates> coordinates =
        countryRepositoryService.getCoordinatesByCountryName(countryName);
    for (Coordinates coordinate : coordinates) {
      if (coordinate.checkId(coordinatesId)) {
        coordinatesRepositoryService.deleteById(coordinatesId);
        return "Deleted successfully!";
      }
    }
    throw new NoHandlerFoundException(DELETE_METHOD,
        "/sunInfo/country/{countryName}/coordinates/{coordinatesId}", new HttpHeaders());
  }

  @DeleteMapping("/history/date/{dateId}")
  public String deleteByDate(@PathVariable Long dateId) throws NoHandlerFoundException {
    logger.info("Processing delete request \"/history/date/{dateId}\"");
    Date removableDate = dateRepositoryService.findDateById(dateId);
    if (Objects.equals(removableDate, null)) {
      throw new NoHandlerFoundException(DELETE_METHOD, "/history/date/{dateId}", new HttpHeaders());
    }
    List<Coordinates> coordinates = dateRepositoryService.getCoordinatesByDateId(dateId);
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

  @DeleteMapping("/history/coordinates/{coordinatesId}")
  public String deleteByCoordinates(@PathVariable Long coordinatesId)
      throws NoHandlerFoundException {
    logger.info("Processing delete request \"/history/coordinates/{coordinatesId}\"");
    Coordinates removableCoordinates =
        coordinatesRepositoryService.findCoordinatesById(coordinatesId);
    if (Objects.equals(removableCoordinates, null)) {
      throw new NoHandlerFoundException(DELETE_METHOD, "/history/coordinates/{coordinatesId}",
          new HttpHeaders());
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

  @DeleteMapping(value = "/**")
  public void defaultDeleteMethod() {
    throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
  }
}
