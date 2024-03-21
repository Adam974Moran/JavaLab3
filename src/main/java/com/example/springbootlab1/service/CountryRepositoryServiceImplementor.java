package com.example.springbootlab1.service;

import com.example.springbootlab1.model.Coordinates;
import com.example.springbootlab1.model.Country;
import com.example.springbootlab1.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Service
public class CountryRepositoryServiceImplementor implements CountryRepositoryService {

    private final CountryRepository countryRepository;

    public CountryRepositoryServiceImplementor(CountryRepository countryRepository){
        this.countryRepository = countryRepository;
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
}
