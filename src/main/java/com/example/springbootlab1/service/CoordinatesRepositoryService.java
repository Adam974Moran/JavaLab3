package com.example.springbootlab1.service;


import com.example.springbootlab1.model.Coordinates;
import com.example.springbootlab1.model.Date;
import java.util.List;

/**
 * The interface Coordinates repository service.
 */
public interface CoordinatesRepositoryService {
  /**
   * Save.
   *
   * @param coordinates the coordinates
   */
  void save(Coordinates coordinates);

  /**
   * Delete all.
   *
   * @param coordinatesList the coordinates list
   */
  void deleteAll(List<Coordinates> coordinatesList);

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
  List<Coordinates> findAll();

  /**
   * Find coordinates by id coordinates.
   *
   * @param id the id
   * @return the coordinates
   */
  Coordinates findCoordinatesById(Long id);

  /**
   * Gets date by coordinates id.
   *
   * @param coordinatesId the coordinates id
   * @return the date by coordinates id
   */
  List<Date> getDateByCoordinatesId(Long coordinatesId);
}
