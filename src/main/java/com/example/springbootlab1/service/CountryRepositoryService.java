package com.example.springbootlab1.service;

import com.example.springbootlab1.model.Coordinates;
import com.example.springbootlab1.model.Country;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * The interface Country repository service.
 */
@Service
public interface CountryRepositoryService {
  /**
   * Save.
   *
   * @param country the country
   */
  void save(Country country);

  /**
   * Find by country name country.
   *
   * @param countryName the country name
   * @return the country
   */
  Country findByCountryName(String countryName);

  /**
   * Delete.
   *
   * @param country the country
   */
  void delete(Country country);

  /**
   * Find all java . util . list.
   *
   * @return the java . util . list
   */
  java.util.List<Country> findAll();

  /**
   * Gets coordinates by country name.
   *
   * @param countryName the country name
   * @return the coordinates by country name
   */
  List<Coordinates> getCoordinatesByCountryName(String countryName);
}
