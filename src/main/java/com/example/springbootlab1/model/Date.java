package com.example.springbootlab1.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * The type Date.
 */
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Date {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String coordinatesDate;
  @ManyToMany
  @JoinTable(name = "date_coordinates",
      joinColumns = @JoinColumn(name = "date_id"),
      inverseJoinColumns = @JoinColumn(name = "coordinates_id"))
  private Set<Coordinates> coordinates;

  /**
   * Instantiates a new Date.
   */
  public Date() {
    coordinates = new HashSet<>();
  }

  /**
   * Gets id.
   *
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets id.
   *
   * @param id the id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets coordinates date.
   *
   * @return the coordinates date
   */
  public String getCoordinatesDate() {
    return coordinatesDate;
  }

  /**
   * Sets coordinates date.
   *
   * @param date the date
   */
  public void setCoordinatesDate(String date) {
    this.coordinatesDate = date;
  }

  /**
   * Gets coordinates.
   *
   * @return the coordinates
   */
  public Set<Coordinates> getCoordinates() {
    return coordinates;
  }
}
