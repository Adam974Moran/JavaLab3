package com.example.springbootlab1.service;

import com.example.springbootlab1.model.Coordinates;
import com.example.springbootlab1.model.Country;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface CountryRepositoryService {
  void save(Country country);

  Country findByCountryName(String countryName);

  void delete(Country country);

  java.util.List<Country> findAll();

  List<Coordinates> getCoordinatesByCountryName(String countryName);
}
