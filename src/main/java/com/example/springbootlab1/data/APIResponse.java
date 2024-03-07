package com.example.springbootlab1.data;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class APIResponse {
    private APIResponse(){}

    public static String getJsonInString(String url){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }
}
