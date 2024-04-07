package com.example.springbootlab1.repository;

import com.example.springbootlab1.model.Coordinates;
import com.example.springbootlab1.model.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * The interface Coordinates repository.
 */
@Repository
public interface CoordinatesRepository extends JpaRepository<Coordinates, Long> {
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
  @Query("select c.date from Coordinates c where c.id = :coordinatesId")
  List<Date> getDateByCoordinatesId(Long coordinatesId);
}
