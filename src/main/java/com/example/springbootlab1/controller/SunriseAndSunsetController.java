package com.example.springbootlab1.controller;

import com.example.springbootlab1.repository.Coordinates;
import com.example.springbootlab1.repository.CoordinatesRepository;
import com.example.springbootlab1.repository.CountryRepository;
import com.example.springbootlab1.service.JsonKeyExtractor;
import com.example.springbootlab1.repository.Country;
import com.example.springbootlab1.service.UrlGenerator;
import com.example.springbootlab1.service.WrongFormatException;
import com.example.springbootlab1.data.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class SunriseAndSunsetController {
    public static final String ERROR_MESSAGE_1 = "Please specify a valid path";

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CoordinatesRepository coordinatesRepository;

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
            return w.getExceptionMessage();
        }

        return JsonKeyExtractor.getFormattedJsonKeys(APIResponse.getJsonInString(url));
    }

    @PostMapping("/sunInfo/country/{countryName}")
    public ResponseEntity<Country> addCountry(@PathVariable String countryName){
        Country country = countryRepository.findByCountryName(countryName);
        if(country == null){
            country = new Country();
            country.setCountryName(countryName);
            countryRepository.save(country);
            return new ResponseEntity<>(country, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/sunInfo/country/{countryName}/coordinates")
    public ResponseEntity<Coordinates> addCoordinates(@PathVariable String countryName,
                                                      @RequestParam(value = "lat", defaultValue = "null") String lat,
                                                      @RequestParam(value = "lng", defaultValue = "null") String lng){
        Country country = countryRepository.findByCountryName(countryName);
        if(country == null){
            country = new Country();
            country.setCountryName(countryName);
            countryRepository.save(country);
        }
        if(Objects.equals(lng, "null") || Objects.equals(lat, "null")){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Coordinates coordinates = new Coordinates();
        coordinates.setLat(lat);
        coordinates.setLng(lng);
        coordinates.setCountry(country);
        coordinatesRepository.save(coordinates);
        return new ResponseEntity<>(coordinates, HttpStatus.CREATED);
    }

    @GetMapping("/sunInfo/country/{countryName}/coordinates")
    public String getCoordinatesByCountry(@PathVariable String countryName,
                                          @RequestParam(value = "date", defaultValue = "null") String date){
        StringBuilder results = new StringBuilder("Results:\n");
        Country country = countryRepository.findByCountryName(countryName);
        if(country == null){
            return "No such country!";
        }
        List<Coordinates> coordinatesList = new ArrayList<>(country.getCoordinates());
        for(Coordinates coordinate : coordinatesList){
            String url;
            try {
                url = UrlGenerator.generateNewUrl(coordinate.getLat(), coordinate.getLng(), date, null);
            }
            catch (WrongFormatException w){
                return w.getExceptionMessage();
            }
            results.append(JsonKeyExtractor.getFormattedJsonKeys(APIResponse.getJsonInString(url))).append("\n");
        }
        return results.toString();
    }

    @GetMapping("/sunInfo/")
    public List<Country> userRequests(){
        return countryRepository.findAll();
    }

    @DeleteMapping("/sunInfo/country/{countryName}")
    public String deleteCoordinates(@PathVariable String countryName){
        Country country = countryRepository.findByCountryName(countryName);
        if(country == null){
            return "There is no such country!";
        }
        List<Coordinates> coordinates = new ArrayList<>(country.getCoordinates());
        coordinatesRepository.deleteAll(coordinates);
        countryRepository.delete(country);
        return "Deleted successfully!";
    }

    @DeleteMapping("/sunInfo/country/{countryName}/coordinates/{coordinatesId}")
    public String deleteCoordinates(@PathVariable String countryName, @PathVariable Long coordinatesId){
        Country country = countryRepository.findByCountryName(countryName);
        if(country == null){
            return "There is no such country!";
        }
        List<Coordinates> coordinates = new ArrayList<>(country.getCoordinates());
        for (Coordinates coordinate : coordinates){
            if(coordinate.checkId(coordinatesId)){
                coordinatesRepository.deleteById(coordinatesId);
                return "Deleted successfully!";
            }
        }
        return "No coordinates with such id!";
    }

    @GetMapping(value = "/**")
    public ResponseEntity<String> defaultGetMethod() {
        return new ResponseEntity<>(ERROR_MESSAGE_1, HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/**")
    public ResponseEntity<String> defaultPostMethod() {
        return new ResponseEntity<>(ERROR_MESSAGE_1, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/**")
    public ResponseEntity<String> defaultDeleteMethod() {
        return new ResponseEntity<>(ERROR_MESSAGE_1, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException() {
        return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
