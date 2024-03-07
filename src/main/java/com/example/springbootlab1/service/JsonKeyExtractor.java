package com.example.springbootlab1.service;

import com.google.gson.Gson;

public class JsonKeyExtractor {
    private JsonKeyExtractor() {
    }

    public static String getFormattedJsonKeys(String jsonResponse){
        Gson json = new Gson();
        Results sunrise = json.fromJson(jsonResponse, Results.class);
        return "Sunrise " + sunrise.sunriseAndSunsetResults.sunrise + ", sunset " + sunrise.sunriseAndSunsetResults.sunset;
    }
}
