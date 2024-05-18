package com.example.springbootlab1.service;

import com.example.springbootlab1.model.Coordinates;
import com.example.springbootlab1.model.Country;
import com.example.springbootlab1.repository.CoordinatesRepository;
import com.example.springbootlab1.repository.CountryRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CountryRepositoryServiceImplementorTest {

  @Mock
  private CountryRepository countryRepository;

  @Mock
  private CoordinatesRepository coordinatesRepository;


  @InjectMocks
  private CountryRepositoryServiceImplementor countryService;

  @Test
  void testSave() {
    Country country = new Country();
    countryService.save(country);
    verify(countryRepository).save(country);
  }

  @Test
  void testFindByCountryName() {
    String countryName = "TestCountry";
    Country expectedCountry = new Country();
    expectedCountry.setCountryName(countryName);

    Mockito.when(countryRepository.findByCountryName(countryName)).thenReturn(expectedCountry);

    Country actualCountry = countryService.findByCountryName(countryName);

    assertEquals(expectedCountry, actualCountry);
  }

  @Test
  void testDelete() {
    Country country = new Country();
    countryService.delete(country);
    verify(countryRepository).delete(country);
  }

  @Test
  void testFindAll() {
    List<Country> expectedCountries = new ArrayList<>();
    expectedCountries.add(new Country());
    expectedCountries.add(new Country());

    Mockito.when(countryRepository.findAll()).thenReturn(expectedCountries);

    List<Country> actualCountries = countryService.findAll();

    assertEquals(expectedCountries, actualCountries);
  }

  @Test
  void testGetCoordinatesByCountryName() {
    String countryName = "TestCountry";
    List<Coordinates> expectedCoordinates = new ArrayList<>();
    expectedCoordinates.add(new Coordinates());
    expectedCoordinates.add(new Coordinates());

    Mockito.when(countryRepository.getCoordinatesByCountryName(countryName)).thenReturn(expectedCoordinates);

    List<Coordinates> actualCoordinates = countryService.getCoordinatesByCountryName(countryName);

    assertEquals(expectedCoordinates, actualCoordinates);
  }

  @Test
  void testSaveCountryWithCoordinates() {
    Country country = new Country();
    country.setCountryName("TestCountry");
    List<Coordinates> coordinates = new ArrayList<>();
    coordinates.add(new Coordinates());
    country.setCoordinates(coordinates);

    Mockito.when(countryRepository.findByCountryName(country.getCountryName())).thenReturn(null);
    Mockito.when(countryRepository.save(country)).thenReturn(country);
    Mockito.when(coordinatesRepository.save(Mockito.any(Coordinates.class))).thenAnswer(invocation -> invocation.getArgument(0));

    Country savedCountry = countryService.saveCountryWithCoordinates(country);

    assertNotNull(savedCountry);
    verify(countryRepository).save(country);
    verify(coordinatesRepository, Mockito.times(coordinates.size())).save(Mockito.any(Coordinates.class));
  }

}

