package com.example.springbootlab1.repository;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Date {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String coordinatesDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCoordinatesDate() {
        return coordinatesDate;
    }

    public void setCoordinatesDate(String date) {
        this.coordinatesDate = date;
    }

    @ManyToMany
    @JoinTable(name = "date_coordinates",
            joinColumns = @JoinColumn(name = "date_id"),
            inverseJoinColumns = @JoinColumn(name = "coordinates_id"))
    private Set<Coordinates> coordinates;

    public Date(){
        coordinates = new HashSet<>();
    }

    public Set<Coordinates> getCoordinates() {
        return coordinates;
    }
}
