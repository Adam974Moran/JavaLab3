//package com.example.springbootlab1.controller;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import com.example.springbootlab1.data.ApiResponse;
//import com.example.springbootlab1.model.Coordinates;
//import com.example.springbootlab1.model.Country;
//import com.example.springbootlab1.model.Date;
//import com.example.springbootlab1.service.CoordinatesRepositoryService;
//import com.example.springbootlab1.service.CountryRepositoryService;
//import com.example.springbootlab1.service.DateRepositoryService;
//import com.example.springbootlab1.service.UrlGenerator;
//import com.example.springbootlab1.service.WrongFormatException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockedStatic;
//import org.mockito.Mockito;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//@SpringBootTest
//class SunriseAndSunsetControllerTest {
//
//  @Mock
//  private CountryRepositoryService countryRepositoryService;
//
//  @Mock
//  private CoordinatesRepositoryService coordinatesRepositoryService;
//
//  @Mock
//  private DateRepositoryService dateRepositoryService;
//
//  @InjectMocks
//  private SunriseAndSunsetController controller;
//
//  private ApiResponse apiResponse;
//
//  @BeforeEach
//  public void setUp() {
//    apiResponse = Mockito.mock(ApiResponse.class);
//  }
//
//  @Test
//  void testGetSunriseAndSunsetInfo_NewCoordinatesAndDate() {
//    String lat = "40.7128";
//    String lng = "-74.0060";
//    String date = "2023-04-01";
//    String formatted = "true";
//
//    when(coordinatesRepositoryService.findAll()).thenReturn(Collections.emptyList());
//    when(dateRepositoryService.findByDate(date)).thenReturn(null);
//
//    String result = controller.getSunriseAndSunsetInfo(lat, lng, date, formatted);
//
//    verify(coordinatesRepositoryService, times(1)).save(Mockito.any(Coordinates.class));
//    verify(dateRepositoryService, times(1)).save(Mockito.any(Date.class));
//  }
//
//  @Test
//  void testGetSunriseAndSunsetInfo_ExistingCoordinatesNewDate() {
//    // Подготовка
//    String lat = "40.7128";
//    String lng = "-74.0060";
//    String date = "2023-04-01";
//    String formatted = "true";
//
//    Coordinates existingCoordinates = new Coordinates();
//    existingCoordinates.setLat(lat);
//    existingCoordinates.setLng(lng);
//
//    when(coordinatesRepositoryService.findAll()).thenReturn(Arrays.asList(existingCoordinates));
//    when(dateRepositoryService.findByDate(date)).thenReturn(null);
//    String result = controller.getSunriseAndSunsetInfo(lat, lng, date, formatted);
//
//    Mockito.verify(coordinatesRepositoryService).save(Mockito.any(Coordinates.class));
//    Mockito.verify(dateRepositoryService).save(Mockito.any(Date.class));
//  }
//
////  @Test
////  void testAddCoordinatesSuccess() throws IllegalAccessException {
////    String countryName = "TestCountry";
////    String lat = "12.34";
////    String lng = "56.78";
////    when(countryRepositoryService.findByCountryName(countryName)).thenReturn(null);
////
////    String result = controller.addCoordinates(countryName, lat, lng);
////
////    assertEquals("Created successfully!", result);
////    verify(countryRepositoryService).save(any(Country.class));
////    verify(coordinatesRepositoryService).save(any(Coordinates.class));
////  }
//
//  @Test
//  void testGetSunriseAndSunsetInfo_ExistingCoordinatesExistingDate() {
//    // Подготовка
//    String lat = "40.7128";
//    String lng = "-74.0060";
//    String date = "2023-04-01";
//    String formatted = "true";
//
//    Coordinates existingCoordinates = new Coordinates();
//    existingCoordinates.setLat(lat);
//    existingCoordinates.setLng(lng);
//
//    Date existingDate = new Date();
//    existingDate.setCoordinatesDate(date);
//    existingDate.getCoordinates().add(existingCoordinates);
//
//    when(coordinatesRepositoryService.findAll()).thenReturn(Arrays.asList(existingCoordinates));
//    when(dateRepositoryService.findByDate(date)).thenReturn(existingDate);
//
//    String expectedJson = "{\"sunrise\": \"10:38:04 AM\", \"sunset\": \"11:21:44 PM\"}";
//    String result = controller.getSunriseAndSunsetInfo(lat, lng, date, formatted);
//
//    Mockito.verify(coordinatesRepositoryService, Mockito.times(1))
//        .save(Mockito.any(Coordinates.class));
//    Mockito.verify(dateRepositoryService, Mockito.times(1)).save(Mockito.any(Date.class));
//
//    assertEquals(expectedJson, result);
//  }
//
//
//  @Test
//  void testGetSunriseAndSunsetInfo_ExceptionHandling() {
//    // Подготовка
//    String lat = "40.7128";
//    String lng = "-74.0060";
//    String date = "2023-04-01";
//    String formatted = "true";
//
//    try (MockedStatic<UrlGenerator> mocked = Mockito.mockStatic(UrlGenerator.class)) {
//      mocked.when(() -> UrlGenerator.generateNewUrl(lat, lng, date, formatted))
//          .thenThrow(new WrongFormatException("Wrong format"));
//
//      // Вызов тестируемого метода
//      String result = controller.getSunriseAndSunsetInfo(lat, lng, date, formatted);
//
//      // Проверка
//      assertEquals("Wrong format", result);
//    }
//  }
//
//
////  @Test
////  void testAddCoordinatesInvalidCoordinates() {
////    // Подготовка
////    String countryName = "TestCountry";
////    String lat = "null";
////    String lng = "null";
////
////    // Выполнение и проверка
////    assertThrows(IllegalAccessException.class,
////        () -> controller.addCoordinates(countryName, lat, lng));
////  }
//
//  @Test
//  void testFindAllDates() {
//    List<Date> expectedDates = Arrays.asList(new Date(), new Date());
//    when(dateRepositoryService.findAll()).thenReturn(expectedDates);
//
//    List<Date> actualDates = dateRepositoryService.findAll();
//
//    assertEquals(expectedDates, actualDates);
//  }
//
////  @Test
////  void testGetSunsetAndSunriseInformationByDate() {
////    Long dateId = 1L;
////    String coordinatesDate = "2023-04-01";
////    String expectedJson = "{\"coordinatesDate\": \"2023-04-01\",\"result\":[\"sunInfo\":{\"sunrise\":\"06:00\",\"sunset\":\"20:00\"}]}";
////
////    Date date = new Date();
////    date.setId(dateId);
////    date.setCoordinatesDate(coordinatesDate);
////    Coordinates coordinates = new Coordinates();
////    coordinates.setLng("12.34");
////    coordinates.setLat("56.78");
////
////    when(dateRepositoryService.findDateById(dateId)).thenReturn(date);
////    when(dateRepositoryService.getCoordinatesByDateId(dateId)).thenReturn(Set.of(coordinates));
////
////    when(apiResponse.getJsonInString(Mockito.anyString())).thenReturn("{\"sunrise\":\"06:00\",\"sunset\":\"20:00\"}");
////
////    ResponseEntity<String> response =
////        (ResponseEntity<String>) controller.getSunsetAndSunriseInformationByDate(dateId);
////
////    assertEquals(expectedJson, response.getBody());
////  }
//
//  @Test
//  void testFindAllCoordinates() {
//    List<Coordinates> expectedCoordinates = Arrays.asList(new Coordinates(), new Coordinates());
//    when(coordinatesRepositoryService.findAll()).thenReturn(expectedCoordinates);
//
//    List<Coordinates> actualCoordinates = coordinatesRepositoryService.findAll();
//
//    assertEquals(expectedCoordinates, actualCoordinates);
//  }
//
//  @Test
//  void testBulkCountryInsert() {
//    // Подготовка
//    List<Country> countries = Arrays.asList(new Country(), new Country());
//
//    // Выполнение
//    ResponseEntity<String> result = controller.bulkCountryInsert(countries);
//
//    // Проверка
//    assertEquals(HttpStatus.OK, result.getStatusCode());
//    assertEquals("Country has been saved", result.getBody());
//    verify(countryRepositoryService, times(2)).saveCountryWithCoordinates(any(Country.class));
//  }
//
////  @Test
////  void testGetAllCountriesInfo() {
////    // Подготовка
////    List<Country> countries = Arrays.asList(new Country(), new Country());
////    when(countryRepositoryService.findAll()).thenReturn(countries);
////
////    // Выполнение
////    ResponseEntity<List<Country>> result = controller.getAllCountriesInfo();
////
////    // Проверка
////    assertEquals(HttpStatus.OK, result.getStatusCode());
////    assertEquals(countries, result.getBody());
////  }
//
////  @Test
////  void testUpdateCountryNameSuccess() throws IllegalAccessException {
////    String oldCountryName = "OldCountry";
////    String newCountryName = "NewCountry";
////    Country country = new Country();
////    country.setCountryName(oldCountryName);
////    when(countryRepositoryService.findByCountryName(oldCountryName)).thenReturn(country);
////    when(countryRepositoryService.findByCountryName(newCountryName)).thenReturn(null);
////
////    String result = controller.updateCountryName(oldCountryName, newCountryName);
////
////    assertEquals("Country name updated successfully!", result);
////    verify(countryRepositoryService).save(country);
////  }
//
////  @Test
////  void testUpdateCountryNameCountryNotFound() {
////    String oldCountryName = "OldCountry";
////    String newCountryName = "NewCountry";
////    when(countryRepositoryService.findByCountryName(oldCountryName)).thenReturn(null);
////
////    assertThrows(IllegalAccessException.class,
////        () -> controller.updateCountryName(oldCountryName, newCountryName));
////  }
//
////  @Test
////  void testUpdateCoordinatesSuccess() throws IllegalAccessException {
////    Long coordinatesId = 1L;
////    String lat = "12.34";
////    String lng = "56.78";
////    Coordinates coordinates = new Coordinates();
////    when(coordinatesRepositoryService.findCoordinatesById(coordinatesId)).thenReturn(coordinates);
////
////    String result = controller.updateCountryName(coordinatesId, lat, lng);
////
////    assertEquals("Coordinates updated successfully!", result);
////    verify(coordinatesRepositoryService).save(coordinates);
////  }
//
////  @Test
////  void testUpdateCoordinatesInvalidCoordinates() {
////    Long coordinatesId = 1L;
////    String lat = "null";
////    String lng = "null";
////
////    assertThrows(IllegalAccessException.class,
////        () -> controller.updateCountryName(coordinatesId, lat, lng));
////  }
//
//  @Test
//  void testUpdateDateValueSuccess() throws IllegalAccessException {
//    Long dateId = 1L;
//    String newDateValue = "2023-04-01";
//    Date dateToUpdate = new Date();
//    when(dateRepositoryService.findDateById(dateId)).thenReturn(dateToUpdate);
//    when(dateRepositoryService.findByDate(newDateValue)).thenReturn(null);
//
//    String result = controller.updateDateValue(dateId, newDateValue);
//
//    assertEquals("Country name updated successfully!", result);
//    verify(dateRepositoryService).save(dateToUpdate);
//  }
//
//  @Test
//  void testDeleteByDateSuccess() throws IllegalAccessException {
//    // Arrange
//    Long dateId = 1L;
//    Date removableDate = new Date();
//    removableDate.setId(dateId);
//
//    Coordinates coordinate = new Coordinates();
//    coordinate.setId(1L);
//    Set<Date> dates = new HashSet<>();
//    dates.add(removableDate);
//    coordinate.setDates(dates);
//
//    Set<Coordinates> coordinates = new HashSet<>();
//    coordinates.add(coordinate);
//
//    when(dateRepositoryService.findDateById(dateId)).thenReturn(removableDate);
//    when(dateRepositoryService.getCoordinatesByDateId(dateId)).thenReturn(coordinates);
//
//    // Act
//    String result = controller.deleteByDate(dateId);
//
//    // Assert
//    assertEquals("Deleted successfully", result);
//    assertTrue(removableDate.getCoordinates().isEmpty());
//    assertTrue(coordinate.getDates().isEmpty());
//    verify(dateRepositoryService, times(1)).save(removableDate);
//    verify(dateRepositoryService, times(1)).deleteById(dateId);
//    verify(coordinatesRepositoryService, times(1)).deleteById(coordinate.getId());
//  }
//
//  @Test
//  void testDeleteByDateThrowsIllegalAccessException() {
//    // Arrange
//    Long dateId = 1L;
//    when(dateRepositoryService.findDateById(dateId)).thenReturn(null);
//
//    // Act and Assert
//    assertThrows(IllegalAccessException.class, () -> controller.deleteByDate(dateId));
//  }
//
////  @Test
////  public void testGetSunsetAndSunriseInformationByDate() {
////    Long dateId = 1L;
////    String coordinatesDate = "2023-04-01";
////    String expectedJson = "{\"coordinatesDate\": \"2023-04-01\",\"result\":[\"sunInfo\":{\"sunrise\":\"06:00\",\"sunset\":\"20:00\"}]}";
////
////    Date date = new Date();
////    date.setId(dateId);
////    date.setCoordinatesDate(coordinatesDate);
////
////    Coordinates coordinate = new Coordinates();
////    coordinate.setId(1L);
////    when(dateRepositoryService.findDateById(dateId)).thenReturn(date);
////    when(dateRepositoryService.getCoordinatesByDateId(dateId)).thenReturn(Set.of(new Coordinates(1.0, 1.0)));
////    when(ApiResponse.getJsonInString(Mockito.anyString())).thenReturn("{\"sunrise\":\"06:00\",\"sunset\":\"20:00\"}");
////
////    ResponseEntity<Object> response = controller.getSunsetAndSunriseInformationByDate(dateId);
////
////    assertEquals(expectedJson, response.getBody());
////  }
//
//  @Test
//  void testGetHistoryByDate() {
//    List<Date> expectedDates = Arrays.asList(new Date(), new Date());
//    when(dateRepositoryService.findAll()).thenReturn(expectedDates);
//
//    ResponseEntity<List<Date>> response = controller.getHistoryByDate();
//
//    assertEquals(HttpStatus.OK, response.getStatusCode());
//    assertEquals(expectedDates, response.getBody());
//  }
//
//  @Test
//  void testGetHistoryByCoordinates() {
//    Coordinates coordinates1 = new Coordinates();
//    Coordinates coordinates2 = new Coordinates();
//    List<Coordinates> coordinatesList = Arrays.asList(coordinates1, coordinates2);
//    when(coordinatesRepositoryService.findAll()).thenReturn(coordinatesList);
//    assertEquals(controller.getHistoryByCoordinates(),
//        new ResponseEntity<>(coordinatesList, HttpStatus.OK));
//  }
//
//
//  @Test
//  void testUpdateDateValueDateNotFound() {
//    Long dateId = 1L;
//    String newDateValue = "2023-04-01";
//    Date dateToUpdate = new Date();
//    when(dateRepositoryService.findDateById(dateId)).thenReturn(dateToUpdate);
//    when(dateRepositoryService.findByDate(newDateValue)).thenReturn(dateToUpdate);
//
//    assertThrows(IllegalAccessException.class,
//        () -> controller.updateDateValue(dateId, newDateValue));
//  }
//
////  @Test
////  void testDeleteCoordinatesCountrySuccess() throws IllegalAccessException {
////    String countryName = "TestCountry";
////    Country country = new Country();
////    country.setCountryName(countryName);
////    when(countryRepositoryService.findByCountryName(countryName)).thenReturn(country);
////    when(countryRepositoryService.getCoordinatesByCountryName(countryName)).thenReturn(
////        new ArrayList<>());
////
////    String result = controller.deleteCoordinates(countryName);
////
////    assertEquals("Deleted successfully!", result);
////    verify(countryRepositoryService).delete(country);
////  }
//
////  @Test
////  void testDeleteCoordinatesSuccess() throws IllegalAccessException {
////    String countryName = "TestCountry";
////    Long coordinatesId = 1L;
////    Country country = new Country();
////    country.setCountryName(countryName);
////    Coordinates coordinates = new Coordinates();
////    coordinates.setId(coordinatesId);
////    when(countryRepositoryService.findByCountryName(countryName)).thenReturn(country);
////    when(countryRepositoryService.getCoordinatesByCountryName(countryName)).thenReturn(
////        List.of(coordinates));
////
////    String result = controller.deleteCoordinates(countryName, coordinatesId);
////
////    assertEquals("Deleted successfully!", result);
////    verify(coordinatesRepositoryService).deleteById(coordinatesId);
////  }
//
//  @Test
//  void testDeleteByCoordinatesSuccess() throws IllegalAccessException {
//    Long coordinatesId = 1L;
//    Coordinates coordinates = new Coordinates();
//    coordinates.setId(coordinatesId);
//    when(coordinatesRepositoryService.findCoordinatesById(coordinatesId)).thenReturn(coordinates);
//    when(coordinatesRepositoryService.getDateByCoordinatesId(coordinatesId)).thenReturn(
//        new ArrayList<>());
//
//    String result = controller.deleteByCoordinates(coordinatesId);
//
//    assertEquals("Deleted successfully", result);
//    verify(coordinatesRepositoryService).deleteById(coordinatesId);
//  }
//
//}
