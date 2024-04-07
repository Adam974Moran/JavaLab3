package com.example.springbootlab1.repository;

import com.example.springbootlab1.model.Coordinates;
import com.example.springbootlab1.model.Country;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * The interface Country repository.
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
  /**
   * Find by country name country.
   *
   * @param countryName the country name
   * @return the country
   */
  Country findByCountryName(String countryName);

  /**
   * Gets coordinates by country name.
   *
   * @param countryName the country name
   * @return the coordinates by country name
   */
  @Query("select c.coordinates from Country c where c.countryName = :countryName")
  List<Coordinates> getCoordinatesByCountryName(String countryName);
}
