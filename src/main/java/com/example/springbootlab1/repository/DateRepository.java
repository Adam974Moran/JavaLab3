package com.example.springbootlab1.repository;

import com.example.springbootlab1.model.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DateRepository extends JpaRepository<Date, Long> {
    Date findDateByCoordinatesDate(String coordinatesDate);
    Date findDateById(Long id);

    @Query("select d.coordinates from Date d where d.id = :dateId")
    List<Coordinates> getCoordinatesByDateId(Long dateId);
}
