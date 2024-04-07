package com.example.springbootlab1.repository;

import com.example.springbootlab1.model.Coordinates;
import com.example.springbootlab1.model.Country;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
  Country findByCountryName(String countryName);

  @Query("select c.coordinates from Country c where c.countryName = :countryName")
  List<Coordinates> getCoordinatesByCountryName(String countryName);
}
