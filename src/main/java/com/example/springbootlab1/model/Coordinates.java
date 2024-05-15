package com.example.springbootlab1.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * The type Coordinates.
 */
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Coordinates {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String lat;

  private String lng;
  @JsonBackReference
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "country_id")
  private Country country;
  @ManyToMany(mappedBy = "coordinates")
  private Set<Date> date;

  /**
   * Instantiates a new Coordinates.
   */
  public Coordinates() {
    date = new HashSet<>();
  }

  /**
   * Gets id.
   *
   * @return the id
   */
  public Long getId() {
    return id;
  }

  public String getStringId() { return this.id.toString();}

  /**
   * Sets id.
   *
   * @param id the id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets lat.
   *
   * @return the lat
   */
  public String getLat() {
    return lat;
  }

  /**
   * Sets lat.
   *
   * @param lat the lat
   */
  public void setLat(String lat) {
    this.lat = lat;
  }

  /**
   * Gets lng.
   *
   * @return the lng
   */
  public String getLng() {
    return lng;
  }

  /**
   * Sets lng.
   *
   * @param lng the lng
   */
  public void setLng(String lng) {
    this.lng = lng;
  }

  /**
   * Gets country.
   *
   * @return the country
   */
  public Country getCountry() {
    return country;
  }

  /**
   * Sets country.
   *
   * @param country the country
   */
  public void setCountry(Country country) {
    this.country = country;
  }

  /**
   * Gets dates.
   *
   * @return the dates
   */
  public Set<Date> getDates() {
    return date;
  }

  public void setDates(Set<Date> singletonList) {
    this.date = singletonList;
  }

  /**
   * Check id boolean.
   *
   * @param id the id
   * @return the boolean
   */
  public boolean checkId(Long id) {
    return Objects.equals(this.id, id);
  }
}
