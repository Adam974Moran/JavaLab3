package com.example.springbootlab1.service;

import com.example.springbootlab1.model.Coordinates;
import com.example.springbootlab1.model.Date;
import com.google.gson.Gson;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class JsonFormatter {
    private JsonFormatter() {
    }

    public static String getFormattedJsonKeys(String jsonResponse){
        Gson json = new Gson();
        SunriseAndSunsetKeys sunrise = json.fromJson(jsonResponse, SunriseAndSunsetKeys.class);
        return "Sunrise " + sunrise.results.sunrise + ", sunset " + sunrise.results.sunset;
    }

    public static StringBuilder getFormattedJsonForDates(List<Date> datesList){
        StringBuilder result = new StringBuilder("[\n");
        for (Date date : datesList){
            result.append("\t{\n\t\t\"id\": ").append(date.getId()).append(", \n\t\t\"date\": \"").append(date.getCoordinatesDate()).append("\",\n\t\t\"coordinates\":[\n");
            Set<Coordinates> coordinatesSet = date.getCoordinates();
            for (Coordinates coordinates : coordinatesSet){
                result.append("\t\t\t{\n\t\t\t\t\"id\": ").append(coordinates.getId()).append(", \n\t\t\t\t\"lat\": \"").append(coordinates.getLat()).append("\", \n\t\t\t\t\"lng\": \"").append(coordinates.getLng()).append("\"\n\t\t\t},");
            }
            result.deleteCharAt(result.length()-1).append("\n\t\t]\n\t}\n");
        }
        if(datesList.isEmpty()){
            result.deleteCharAt(result.length()-1);
        }
        result.append("]");
        return result;
    }

    public static StringBuilder getFormattedJsonForCoordinates(List<Coordinates> coordinatesList){
        StringBuilder result = new StringBuilder("[\n");
        for (Coordinates coordinates : coordinatesList){
            Set<Date> dateSet = coordinates.getDates();
            if(Objects.equals(dateSet.size(), 0)){
                continue;
            }
            result.append("\t{\n\t\t\"id\": ").append(coordinates.getId()).append(", \n\t\t\"lat\": \"").append(coordinates.getLat()).append("\", \n\t\t\"lng\": \"").append(coordinates.getLng()).append("\",\n\t\t\"dates\":[");
            for (Date date : dateSet){
                result.append("\n\t\t\t{\n\t\t\t\t\"id\": ").append(date.getId()).append(", \n\t\t\t\t\"date\": \"").append(date.getCoordinatesDate()).append("\"\n\t\t\t},");
            }
            result.deleteCharAt(result.length()-1).append("\n\t\t]\n\t}\n");
        }
        if(result.length() == 2){
            result.deleteCharAt(result.length()-1);
        }
        result.append("]");
        return result;
    }
}
