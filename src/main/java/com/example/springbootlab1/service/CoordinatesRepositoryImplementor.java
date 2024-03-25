package com.example.springbootlab1.service;

import com.example.springbootlab1.model.Coordinates;
import com.example.springbootlab1.model.Date;
import com.example.springbootlab1.repository.CoordinatesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoordinatesRepositoryImplementor implements CoordinatesRepositoryService {

    private final CoordinatesRepository coordinatesRepository;

    public CoordinatesRepositoryImplementor(CoordinatesRepository coordinatesRepository){
        this.coordinatesRepository = coordinatesRepository;
    }

    @Override
    public void save(Coordinates coordinates) {
        coordinatesRepository.save(coordinates);
    }

    @Override
    public void deleteAll(List<Coordinates> coordinatesList) {
        coordinatesRepository.deleteAll(coordinatesList);
    }

    @Override
    public void deleteById(Long id) {
        coordinatesRepository.deleteById(id);
    }

    @Override
    public List<Coordinates> findAll() {
        return coordinatesRepository.findAll();
    }

    @Override
    public Coordinates findCoordinatesById(Long id) {
        return coordinatesRepository.findCoordinatesById(id);
    }

    @Override
    public List<Date> getDateByCoordinatesId(Long coordinatesId) {
        return coordinatesRepository.getDateByCoordinatesId(coordinatesId);
    }
}
