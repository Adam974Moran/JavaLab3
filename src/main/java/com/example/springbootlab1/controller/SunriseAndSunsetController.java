package com.example.springbootlab1.controller;

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

import java.util.List;

@RestController
public class SunriseAndSunsetController {
    @GetMapping(value = "/sunInfo")
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

    @Autowired
    private CountryRepository countryRepository;

    @GetMapping("/")
    public List<Country> userRequests(){
        return countryRepository.findAll();
    }



    @GetMapping(value = "/**")
    public ResponseEntity<String> defaultMethod() {
        return new ResponseEntity<>("Please specify a valid path", HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException() {
        return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
