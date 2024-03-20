package com.example.springbootlab1.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@JsonFilter("coordinatesFilter")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Coordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lat")
    private String lat;

    @Column(name = "lng")
    private String lng;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;


    @ManyToMany(mappedBy = "coordinates")
    private Set<Date> date;

    public Coordinates(){
        date = new HashSet<>();
    }

    public Country getCountry() {
        return country;
    }

    public Set<Date> getDates(){
        return date;
    }

    public void setCountry(Country country){
        this.country = country;
    }

    public boolean checkId(Long id){
        return Objects.equals(this.id, id);
    }


}
