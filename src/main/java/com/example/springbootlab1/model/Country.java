package com.example.springbootlab1.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String countryName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @OneToMany(mappedBy = "country")
    private Set<Coordinates> coordinates;

    public Set<Coordinates> getCoordinates(){
        return coordinates;
    }
}
