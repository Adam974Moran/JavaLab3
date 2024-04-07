package com.example.springbootlab1.service;

import com.example.springbootlab1.model.Coordinates;
import com.example.springbootlab1.model.Date;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface DateRepositoryService {
  void save(Date date);

  void deleteById(Long id);

  List<Date> findAll();

  Date findByDate(String date);

  Date findDateById(Long dateId);

  List<Coordinates> getCoordinatesByDateId(Long dateId);
}
