package com.example.springbootlab1.controller;

import com.example.springbootlab1.server.UrlChecker;
import com.example.springbootlab1.server.WrongFormatException;
import com.example.springbootlab1.dao.ResponseGetter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {
    @RequestMapping(value = "/sunInfo", method = RequestMethod.GET)
    public String getSunInfo(@RequestParam(value = "lat", defaultValue = "null") String lat,
                                  @RequestParam(value = "lng", defaultValue = "null") String lng,
                                  @RequestParam(value = "date", defaultValue = "null") String date,
                                  @RequestParam(value = "formatted", defaultValue = "null") String formatted){
        String url;
        try {
            url = UrlChecker.checkUrl(lat, lng, date, formatted);
        }
        catch (WrongFormatException w){
            return w.getExceptionMessage();
        }
        return ResponseGetter.gettingFinalResponse(url);
    }

    @RequestMapping(value = "/**", method = RequestMethod.GET)
    public ResponseEntity<String> defaultMethod() {
        return new ResponseEntity<>("Please specify a valid path", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException() {
        return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
