package com.example.springbootlab1.dao;

import com.example.springbootlab1.server.JsonRemaster;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ResponseGetter {
    private ResponseGetter(){}

    public static String gettingFinalResponse(String url){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        String jsonResponse = response.getBody();

        return JsonRemaster.gettingJsonKeyValues(jsonResponse);
    }
}
