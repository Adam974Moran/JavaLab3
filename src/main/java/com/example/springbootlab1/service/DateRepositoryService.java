package com.example.springbootlab1.service;

import com.example.springbootlab1.repository.Coordinates;
import com.example.springbootlab1.repository.Date;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface DateRepositoryService {
    void save(Date date);
    void deleteById(Long id);
    List<Date> findAll();
    Date findByDate(String date);
    Date findDateById(Long dateId);
    List<Coordinates> getCoordinatesByDateId(Long dateId);
}
