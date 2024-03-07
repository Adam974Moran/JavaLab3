package com.example.springbootlab1.service;

import com.google.gson.Gson;

public class JsonKeyExtractor {
    private JsonKeyExtractor() {
    }

    public static String getFormattedJsonKeys(String jsonResponse){
        Gson json = new Gson();
        SunriseAndSunsetKeys sunrise = json.fromJson(jsonResponse, SunriseAndSunsetKeys.class);
        return "Sunrise " + sunrise.results.sunrise + ", sunset " + sunrise.results.sunset;
    }
}
