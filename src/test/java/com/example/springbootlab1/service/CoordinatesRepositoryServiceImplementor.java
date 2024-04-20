package com.example.springbootlab1.service;

import com.example.springbootlab1.model.Coordinates;
import com.example.springbootlab1.model.Date;
import com.example.springbootlab1.repository.CoordinatesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CoordinatesRepositoryImplementorTest {

  @Mock
  private CoordinatesRepository coordinatesRepository;

  @InjectMocks
  private CoordinatesRepositoryImplementor coordinatesRepositoryImplementor;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testSave() {
    Coordinates coordinates = new Coordinates();
    coordinatesRepositoryImplementor.save(coordinates);
    verify(coordinatesRepository, times(1)).save(coordinates);
  }

  @Test
  void testDeleteAll() {
    List<Coordinates> coordinatesList = Arrays.asList(new Coordinates(), new Coordinates());
    coordinatesRepositoryImplementor.deleteAll(coordinatesList);
    verify(coordinatesRepository, times(1)).deleteAll(coordinatesList);
  }

  @Test
  void testDeleteById() {
    Long id = 1L;
    coordinatesRepositoryImplementor.deleteById(id);
    verify(coordinatesRepository, times(1)).deleteById(id);
  }

  @Test
  void testFindAll() {
    List<Coordinates> coordinatesList = Arrays.asList(new Coordinates(), new Coordinates());
    when(coordinatesRepository.findAll()).thenReturn(coordinatesList);

    List<Coordinates> result = coordinatesRepositoryImplementor.findAll();
    verify(coordinatesRepository, times(1)).findAll();
    assertEquals(coordinatesList, result);
  }

  @Test
  void testFindCoordinatesById() {
    Long id = 1L;
    Coordinates coordinates = new Coordinates();
    when(coordinatesRepository.findCoordinatesById(id)).thenReturn(coordinates);

    Coordinates result = coordinatesRepositoryImplementor.findCoordinatesById(id);
    verify(coordinatesRepository, times(1)).findCoordinatesById(id);
    assertEquals(coordinates, result);
  }

  @Test
  void testGetDateByCoordinatesId() {
    Long coordinatesId = 1L;
    List<Date> dateList = Arrays.asList(new Date(), new Date());
    when(coordinatesRepository.getDateByCoordinatesId(coordinatesId)).thenReturn(dateList);

    List<Date> result = coordinatesRepositoryImplementor.getDateByCoordinatesId(coordinatesId);
    verify(coordinatesRepository, times(1)).getDateByCoordinatesId(coordinatesId);
    assertEquals(dateList, result);
  }
}
