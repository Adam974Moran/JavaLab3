package com.example.springbootlab1.controller;

import com.example.springbootlab1.service.JsonKeyExtractor;
import com.example.springbootlab1.service.UrlGenerator;
import com.example.springbootlab1.service.WrongFormatException;
import com.example.springbootlab1.data.SunriseAndSunset;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        return JsonKeyExtractor.getFormattedJsonKeys(SunriseAndSunset.getJsonInString(url));
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
