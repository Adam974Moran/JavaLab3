package com.example.springbootlab1.repository;

import com.example.springbootlab1.model.Coordinates;
import com.example.springbootlab1.model.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DateRepository extends JpaRepository<Date, Long> {
  Date findDateByCoordinatesDate(String coordinatesDate);

  Date findDateById(Long id);

  @Query("select d.coordinates from Date d where d.id = :dateId")
  List<Coordinates> getCoordinatesByDateId(Long dateId);
}
