package com.example.springbootlab1.repository;

import com.example.springbootlab1.model.Coordinates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoordinatesRepository extends JpaRepository<Coordinates, Long> {
    Coordinates getCoordinatesByLngAndLat(String lat, String lng);
    Coordinates findCoordinatesById(Long id);

    @Query("select c.date from Coordinates c where c.id = :coordinatesId")
    List<Date> getDateByCoordinatesId(Long coordinatesId);
}
