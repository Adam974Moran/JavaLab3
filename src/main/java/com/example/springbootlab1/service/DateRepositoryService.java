package com.example.springbootlab1.service;

import com.example.springbootlab1.model.Coordinates;
import com.example.springbootlab1.model.Date;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * The interface Date repository service.
 */
@Service
public interface DateRepositoryService {
  /**
   * Save.
   *
   * @param date the date
   */
  void save(Date date);

  /**
   * Delete by id.
   *
   * @param id the id
   */
  void deleteById(Long id);

  /**
   * Find all list.
   *
   * @return the list
   */
  List<Date> findAll();

  /**
   * Find by date date.
   *
   * @param date the date
   * @return the date
   */
  Date findByDate(String date);

  /**
   * Find date by id date.
   *
   * @param dateId the date id
   * @return the date
   */
  Date findDateById(Long dateId);

  /**
   * Gets coordinates by date id.
   *
   * @param dateId the date id
   * @return the coordinates by date id
   */
  List<Coordinates> getCoordinatesByDateId(Long dateId);
}
