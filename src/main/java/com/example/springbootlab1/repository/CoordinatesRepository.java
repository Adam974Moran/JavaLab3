package com.example.springbootlab1.repository;

import com.example.springbootlab1.model.Coordinates;
import com.example.springbootlab1.model.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordinatesRepository extends JpaRepository<Coordinates, Long> {
  Coordinates findCoordinatesById(Long id);

  @Query("select c.date from Coordinates c where c.id = :coordinatesId")
  List<Date> getDateByCoordinatesId(Long coordinatesId);
}
