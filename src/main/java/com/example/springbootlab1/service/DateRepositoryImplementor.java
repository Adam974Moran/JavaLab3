package com.example.springbootlab1.service;


import com.example.springbootlab1.model.Coordinates;
import com.example.springbootlab1.model.Date;
import com.example.springbootlab1.repository.DateRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class DateRepositoryImplementor implements DateRepositoryService{

    private final DateRepository dateRepository;

    public DateRepositoryImplementor(DateRepository dateRepository){
        this.dateRepository = dateRepository;
    }

    @Override
    public void save(Date date) {
        dateRepository.save(date);
    }

    @Override
    public void deleteById(Long id) {
        dateRepository.deleteById(id);
    }

    @Override
    public List<Date> findAll() {
        return dateRepository.findAll();
    }

    @Override
    public Set<Coordinates> getCoordinatesListByDateId(Long dateId) {
        return dateRepository.getCoordinatesListByDateId(dateId);
    }

    @Override
    public Date findByDate(String date) {
        return dateRepository.findDateByCoordinatesDate(date);
    }

    @Override
    public Date findDateById(Long dateId) {
        return dateRepository.findDateById(dateId);
    }

    @Override
    public List<Coordinates> getCoordinatesByDateId(Long dateId) {
        return dateRepository.getCoordinatesByDateId(dateId);
    }
}
