package com.example.springbootlab1.controller;

import com.example.springbootlab1.model.Coordinates;
import com.example.springbootlab1.model.Date;
import com.example.springbootlab1.service.*;
import com.example.springbootlab1.model.Country;
import com.example.springbootlab1.data.APIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RestController
public class SunriseAndSunsetController {
    private static final String ERROR_MESSAGE_1 = "Please specify a valid path";
    private static final Logger logger = LoggerFactory.getLogger(SunriseAndSunsetController.class);
    private static final String LOGGER_ERROR_MESSAGE_1 = "Missed lng/lat arguments, or they can't be found";
    private static final String LOGGER_ERROR_MESSAGE_2 = "Country entity with such countryName can't be found in data base";

    private final CountryRepositoryService countryRepositoryService;
    private final CoordinatesRepositoryService coordinatesRepositoryService;
    private final DateRepositoryService dateRepositoryService;

    public SunriseAndSunsetController(CountryRepositoryService countryRepositoryService,
                                      CoordinatesRepositoryService coordinatesRepositoryService,
                                      DateRepositoryService dateRepositoryService){
        this.countryRepositoryService = countryRepositoryService;
        this.coordinatesRepositoryService = coordinatesRepositoryService;
        this.dateRepositoryService = dateRepositoryService;
    }


    //CREATE
    @PostMapping("/sunInfo/country/{countryName}/coordinates")
    public String addCoordinates(@PathVariable String countryName,
                                                      @RequestParam(value = "lat", defaultValue = "null") String lat,
                                                      @RequestParam(value = "lng", defaultValue = "null") String lng){
        Country country = countryRepositoryService.findByCountryName(countryName);
        if(country == null){
            country = new Country();
            country.setCountryName(countryName);
            countryRepositoryService.save(country);
            logger.info("New country has been saved to data base");
        }
        if(Objects.equals(lng, "null") || Objects.equals(lat, "null")){
            logger.error(LOGGER_ERROR_MESSAGE_1);
            return "Variables \"lng\" and \"lat\" are obligatory!";
        }
        Coordinates coordinates = new Coordinates();
        coordinates.setLat(lat);
        coordinates.setLng(lng);
        coordinates.setCountry(country);
        coordinatesRepositoryService.save(coordinates);
        logger.info("New coordinates have been saved to data base");
        return "Created successfully!";
    }

    @PostMapping(value = "/**")
    public ResponseEntity<String> defaultPostMethod() {
        logger.error("Incorrect endpoint in POST method");
        return new ResponseEntity<>(ERROR_MESSAGE_1, HttpStatus.BAD_REQUEST);
    }


    //READ
    @GetMapping("/sunInfo")
    public String getSunriseAndSunsetInfo(@RequestParam(value = "lat", defaultValue = "null") String lat,
                                          @RequestParam(value = "lng", defaultValue = "null") String lng,
                                          @RequestParam(value = "date", defaultValue = "null") String date,
                                          @RequestParam(value = "formatted", defaultValue = "null") String formatted){
        String url;
        try {
            url = UrlGenerator.generateNewUrl(lat, lng, date, formatted);
        }
        catch (WrongFormatException w){
            logger.error(LOGGER_ERROR_MESSAGE_1);
            return w.getExceptionMessage();
        }

        //"CREATING" DATE AND COORDINATES
        if(Objects.equals(date, "null") || Objects.equals(date, "today")){
            date = LocalDate.now().toString();
        }
        Coordinates coordinates = null;
        List<Coordinates> coordinatesToCheck = coordinatesRepositoryService.findAll();
        for(Coordinates coordinate : coordinatesToCheck){
            if(Objects.equals(coordinate.getLat(), lat) && Objects.equals(coordinate.getLng(), lng)){
                coordinates = coordinate;
            }
        }
        Date coordinatesDate = dateRepositoryService.findByDate(date); //Ищем дату в репозитории
        if(Objects.equals(coordinates, null)){ //Если координаты не существует
            coordinates = new Coordinates();
            coordinates.setLat(lat);
            coordinates.setLng(lng);
            coordinatesRepositoryService.save(coordinates);
            logger.info("New coordinates have been saved to data base");

            //Проверяем нужно ли дополнительно сохранять и дату
            if(Objects.equals(coordinatesDate, null)){
                coordinatesDate = new Date();
                coordinatesDate.setCoordinatesDate(date);
                logger.info("New date has been saved to data base");
            }
            coordinatesDate.getCoordinates().add(coordinates);
            dateRepositoryService.save(coordinatesDate);
            coordinates.getDates().add(coordinatesDate);
        }
        else if(!Objects.equals(coordinatesDate, null) && !coordinates.getDates().contains(coordinatesDate)) {
            //Координата существует и существует дата, то мы проверяем привязвны ли эти данные к друг гругу
            coordinatesDate.getCoordinates().add(coordinates);
            dateRepositoryService.save(coordinatesDate);
            coordinates.getDates().add(coordinatesDate);
            coordinatesRepositoryService.save(coordinates);
        }
        else if(Objects.equals(coordinatesDate, null)){
            coordinatesDate = new Date();
            coordinatesDate.setCoordinatesDate(date);
            coordinatesDate.getCoordinates().add(coordinates);
            dateRepositoryService.save(coordinatesDate);
            coordinates.getDates().add(coordinatesDate);
            coordinatesRepositoryService.save(coordinates);
            logger.info("New date has been saved to data base");
        }

        return JsonFormatter.getFormattedJsonKeys(APIResponse.getJsonInString(url));
    }

    @GetMapping("/sunInfo/country/{countryName}")
    public String getCoordinatesByCountry(@PathVariable String countryName,
                                          @RequestParam(value = "date", defaultValue = "null") String date){

        StringBuilder results = new StringBuilder("Results(");
        Country country = countryRepositoryService.findByCountryName(countryName);
        if(country == null){
            logger.warn(LOGGER_ERROR_MESSAGE_2);
            return "No such country!";
        }
        results.append(country.getCountryName()).append("):\n");

        List<Coordinates> coordinatesList = countryRepositoryService.getCoordinatesByCountryName(countryName);
        for(Coordinates coordinate : coordinatesList){
            String url;
            try {
                url = UrlGenerator.generateNewUrl(coordinate.getLat(), coordinate.getLng(), date, null);
            }
            catch (WrongFormatException w){
                logger.error(LOGGER_ERROR_MESSAGE_1);
                return w.getExceptionMessage();
            }
            results.append(JsonFormatter.getFormattedJsonKeys(APIResponse.getJsonInString(url))).append("\n");
        }
        logger.info("Service response has been created");
        return results.toString();
    }

    @GetMapping(value = "/allCountriesInfo", produces = "application/json")
    public ResponseEntity<List<Country>> getAllCountriesInfo() {
        logger.info("Creating allCountriesInfo response");
        return new ResponseEntity<>(countryRepositoryService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/historyByDate", produces = "application/json")
    public ResponseEntity<List<Date>> getHistoryByDate() {
        logger.info("Creating historyByDate response");
        List<Date> datesList = dateRepositoryService.findAll();
        return new ResponseEntity<>(datesList, HttpStatus.OK);
    }

    @GetMapping(value = "/historyByCoordinates", produces = "application/json")
    public ResponseEntity<List<Coordinates>> getHistoryByCoordinates() {
        logger.info("Creating historyByCoordinates response");
        List<Coordinates> coordinatesList = coordinatesRepositoryService.findAll();
        return new ResponseEntity<>(coordinatesList, HttpStatus.OK);
    }

    //END POINT FOR LAB 3
    @GetMapping("sunInfo/date")
    public Object getSunsetAndSunriseInformationByDate(@RequestParam(value = "dateId", defaultValue = "null") Long dateId){
        logger.info("Getting sun information by dateId");
        StringBuilder result = new StringBuilder("Result:\n");
        List<Coordinates> coordinatesSet = dateRepositoryService.getCoordinatesByDateId(dateId);
        for(Coordinates coordinates : coordinatesSet){
            String url;
            try {
                url = UrlGenerator.generateNewUrl(coordinates.getLat(), coordinates.getLng(), dateRepositoryService.findDateById(dateId).getCoordinatesDate(), null);
            }
            catch (WrongFormatException w){
                logger.error(LOGGER_ERROR_MESSAGE_1);
                return w.getExceptionMessage();
            }
            result.append("\n").append(JsonFormatter.getFormattedJsonKeys(APIResponse.getJsonInString(url)));
        }
        logger.info("Getting result");
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/**")
    public ResponseEntity<String> defaultGetMethod() {
        logger.error("Incorrect endpoint in GET method");
        return new ResponseEntity<>(ERROR_MESSAGE_1, HttpStatus.BAD_REQUEST);
    }

    //PUT
    @PutMapping("/country/{countryName}/{newCountryName}")
    public String updateCountryName(@PathVariable String countryName, @PathVariable String newCountryName){
        logger.info("Updating information of Country entity in data base");
        Country country = countryRepositoryService.findByCountryName(countryName);
        Country countryCheck = countryRepositoryService.findByCountryName(newCountryName);
        if(Objects.equals(country, null) || !Objects.equals(countryCheck, null)){
            logger.error("Country entity with such dateId can't be found in data base or it's already exists");
            return "Error in changing country name!";
        }
        else{
            country.setCountryName(newCountryName);
            countryRepositoryService.save(country);
            return "Country name updated successfully!";
        }
    }

    @PutMapping("/coordinates/{coordinatesId}")
    public String updateCountryName(@PathVariable Long coordinatesId,
                                    @RequestParam(value = "lat", defaultValue = "null") String lat,
                                    @RequestParam(value = "lng", defaultValue = "null") String lng){
        logger.info("Updating information of Coordinates entity in data base");
        Coordinates coordinates = coordinatesRepositoryService.findCoordinatesById(coordinatesId);
        if(Objects.equals(coordinates, null) || Objects.equals(lat, "null") || Objects.equals(lng, "null")){
            logger.error("Coordinates entity with such coordinatesId can't be found in data base, or lat/lng parameters were missed");
            return "Nonexistent coordinates or wrong parameters!";
        }
        else{
            coordinates.setLat(lat);
            coordinates.setLng(lng);
            coordinatesRepositoryService.save(coordinates);
            return "Coordinates updated successfully!";
        }
    }

    @PutMapping("/date/{dateId}/{newDateValue}")
    public String updateDateValue(@PathVariable Long dateId, @PathVariable String newDateValue){
        logger.info("Updating information of Date entity in data base");
        Date dateToUpdate = dateRepositoryService.findDateById(dateId);
        Date dateToCheck = dateRepositoryService.findByDate(newDateValue);
        if(Objects.equals(dateToUpdate, null) || !Objects.equals(dateToCheck, null)){
            logger.error("Date entity with such dateId can't be found in data base or it's already exists");
            return "Error in changing date!";
        }
        else{
            dateToUpdate.setCoordinatesDate(newDateValue);
            dateRepositoryService.save(dateToUpdate);
            return "Country name updated successfully!";
        }
    }

    @PutMapping (value = "/**")
    public ResponseEntity<String> defaultPutMethod() {
        logger.error("Incorrect endpoint in PUT method");
        return new ResponseEntity<>(ERROR_MESSAGE_1, HttpStatus.BAD_REQUEST);
    }
    
    //DELETE
    @DeleteMapping("/sunInfo/country/{countryName}")
    public String deleteCoordinates(@PathVariable String countryName){
        logger.info("Deleting Country entity from data base");
        Country country = countryRepositoryService.findByCountryName(countryName);
        if(country == null){
            logger.error(LOGGER_ERROR_MESSAGE_2);
            return "There is no such country!";
        }
        List<Coordinates> coordinates = countryRepositoryService.getCoordinatesByCountryName(countryName);
        coordinatesRepositoryService.deleteAll(coordinates);
        countryRepositoryService.delete(country);
        return "Deleted successfully!";
    }

    @DeleteMapping("/sunInfo/country/{countryName}/coordinates/{coordinatesId}")
    public String deleteCoordinates(@PathVariable String countryName, @PathVariable Long coordinatesId){
        logger.info("Deleting Coordinates entity from data base by coordinatesId and countryName");
        Country country = countryRepositoryService.findByCountryName(countryName);
        if(country == null){
            logger.error(LOGGER_ERROR_MESSAGE_2);
            return "There is no such country!";
        }
        List<Coordinates> coordinates = countryRepositoryService.getCoordinatesByCountryName(countryName);
        for (Coordinates coordinate : coordinates){
            if(coordinate.checkId(coordinatesId)){
                coordinatesRepositoryService.deleteById(coordinatesId);
                return "Deleted successfully!";
            }
        }
        logger.error("Coordinates entity with such coordinatesId can't be found in data base");
        return "No coordinates with such id!";
    }

    @DeleteMapping("/history/date/{dateId}")
    public String deleteByDate(@PathVariable Long dateId){
        logger.info("Deleting Date entity from data base");
        Date removableDate = dateRepositoryService.findDateById(dateId);
        if(Objects.equals(removableDate, null)){
            logger.error("Date entity with such coordinatesId can't be found in data base");
            return "No such date id!";
        }
        List<Coordinates> coordinates = dateRepositoryService.getCoordinatesByDateId(dateId);
        for (Coordinates coordinate : coordinates){
            if(coordinate.getDates().size() == 1 && Objects.equals(coordinate.getCountry(), null)){
                removableDate.getCoordinates().remove(coordinate);
                coordinate.getDates().remove(removableDate);
                dateRepositoryService.save(removableDate);
                coordinatesRepositoryService.deleteById(coordinate.getId());
            }
            else{
                removableDate.getCoordinates().remove(coordinate);
                coordinate.getDates().remove(removableDate);
                dateRepositoryService.save(removableDate);
            }
        }
        dateRepositoryService.deleteById(removableDate.getId());
        return "Deleted successfully";
    }

    @DeleteMapping("/history/coordinates/{coordinatesId}")
    public String deleteByCoordinates(@PathVariable Long coordinatesId){
        logger.info("Deleting Coordinates entity from data base by coordinatesId");
        Coordinates removableCoordinates = coordinatesRepositoryService.findCoordinatesById(coordinatesId);
        if(Objects.equals(removableCoordinates, null)){
            logger.error("Coordinates entity with such coordinatesId can't be found in data base");
            return "No such date id!";
        }
        List<Date> dateList = coordinatesRepositoryService.getDateByCoordinatesId(coordinatesId);
        for (Date date : dateList){
            if(date.getCoordinates().size() == 1){
                removableCoordinates.getDates().remove(date);
                date.getCoordinates().remove(removableCoordinates);
                coordinatesRepositoryService.save(removableCoordinates);
                dateRepositoryService.deleteById(date.getId());
            }
            else{
                removableCoordinates.getDates().remove(date);
                date.getCoordinates().remove(removableCoordinates);
                coordinatesRepositoryService.save(removableCoordinates);
            }
        }
        coordinatesRepositoryService.deleteById(removableCoordinates.getId());
        return "Deleted successfully";
    }

    @DeleteMapping(value = "/**")
    public ResponseEntity<String> defaultDeleteMethod() {
        logger.error("Incorrect endpoint in DELETE method");
        return new ResponseEntity<>(ERROR_MESSAGE_1, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        logger.error("Internal server error");
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
