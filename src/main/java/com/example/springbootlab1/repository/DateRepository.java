package com.example.springbootlab1.repository;

import com.example.springbootlab1.model.Coordinates;
import com.example.springbootlab1.model.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * The interface Date repository.
 */
@Repository
public interface DateRepository extends JpaRepository<Date, Long> {
  /**
   * Find date by coordinates date date.
   *
   * @param coordinatesDate the coordinates date
   * @return the date
   */
  Date findDateByCoordinatesDate(String coordinatesDate);

  /**
   * Find date by id date.
   *
   * @param id the id
   * @return the date
   */
  Date findDateById(Long id);

  /**
   * Gets coordinates by date id.
   *
   * @param dateId the date id
   * @return the coordinates by date id
   */
  @Query("select d.coordinates from Date d where d.id = :dateId")
  List<Coordinates> getCoordinatesByDateId(Long dateId);
}
