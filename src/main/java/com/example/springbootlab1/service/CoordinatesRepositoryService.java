package com.example.springbootlab1.service;

import com.example.springbootlab1.repository.Coordinates;
import com.example.springbootlab1.repository.Date;

import java.util.List;

public interface CoordinatesRepositoryService {
    void save(Coordinates coordinates);
    void deleteAll(List<Coordinates> coordinatesList);
    void deleteById(Long id);
    List<Coordinates> findAll();
    Coordinates getCoordinatesByLngAndLat(String lat, String lng);
    Coordinates findCoordinatesById(Long id);
    List<Date> getDateByCoordinatesId(Long coordinatesId);
}
