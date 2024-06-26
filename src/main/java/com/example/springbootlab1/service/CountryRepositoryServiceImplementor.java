package com.example.springbootlab1.service;

import com.example.springbootlab1.model.Coordinates;
import com.example.springbootlab1.model.Country;
import com.example.springbootlab1.repository.CoordinatesRepository;
import com.example.springbootlab1.repository.CountryRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * The type Country repository service implementor.
 */
@Service
public class CountryRepositoryServiceImplementor implements CountryRepositoryService {

  private final CountryRepository countryRepository;
  private final CoordinatesRepository coordinatesRepository;

  /**
   * Instantiates a new Country repository service implementor.
   *
   * @param countryRepository     the country repository
   * @param coordinatesRepository the coordinates repository
   */
  public CountryRepositoryServiceImplementor(CountryRepository countryRepository,
                                             CoordinatesRepository coordinatesRepository) {
    this.countryRepository = countryRepository;
    this.coordinatesRepository = coordinatesRepository;
  }

  @Override
  public void save(Country country) {
    countryRepository.save(country);
  }

  @Override
  public Country findByCountryName(String countryName) {
    return countryRepository.findByCountryName(countryName);
  }

  @Override
  public void delete(Country country) {
    countryRepository.delete(country);
  }

  @Override
  public List<Country> findAll() {
    return countryRepository.findAll();
  }

  @Override
  public List<Coordinates> getCoordinatesByCountryName(String countryName) {
    return countryRepository.getCoordinatesByCountryName(countryName);
  }

  @Override
  public Country saveCountryWithCoordinates(Country country) {
    Country savedCountry = countryRepository.findByCountryName(country.getCountryName());
    if (savedCountry != null) {
      return null;
    }
    countryRepository.save(country);
    List<Coordinates> savedCoordinates = country.getCoordinates().stream()
        .map(coordinate -> {
          coordinate.setCountry(country);
          return coordinatesRepository.save(coordinate);
        })
        .toList();
    country.setCoordinates(savedCoordinates);
    return country;
  }
}
