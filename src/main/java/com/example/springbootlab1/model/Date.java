package com.example.springbootlab1.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Date {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coordinatesDate")
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
