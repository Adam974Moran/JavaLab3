package com.example.springbootlab1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DateRepository extends JpaRepository<Date, Long> {
    Date findDateByCoordinatesDate(String coordinatesDate);
    Date findDateById(Long id);
}
