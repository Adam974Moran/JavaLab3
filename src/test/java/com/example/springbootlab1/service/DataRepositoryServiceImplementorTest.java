//package com.example.springbootlab1.service;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import com.example.springbootlab1.model.Coordinates;
//import com.example.springbootlab1.model.Date;
//import com.example.springbootlab1.repository.DateRepository;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Set;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//@SpringBootTest
//class DateRepositoryImplementorTest {
//
//  @Autowired
//  private DateRepositoryImplementor dateRepositoryImplementor;
//
//  @MockBean
//  private DateRepository dateRepository;
//
//  @Test
//  void testSave() {
//    Date date = new Date();
//    date.setId(1L);
//    dateRepositoryImplementor.save(date);
//    verify(dateRepository, times(1)).save(date);
//  }
//
//  @Test
//  void testDeleteById() {
//    Long id = 1L;
//    dateRepositoryImplementor.deleteById(id);
//    verify(dateRepository, times(1)).deleteById(id);
//  }
//
//  @Test
//  void testFindAll() {
//    Date date1 = new Date();
//    date1.setId(1L);
//    Date date2 = new Date();
//    date2.setId(2L);
//    List<Date> dates = Arrays.asList(date1, date2);
//    when(dateRepository.findAll()).thenReturn(dates);
//
//    List<Date> result = dateRepositoryImplementor.findAll();
//    assertEquals(dates, result);
//  }
//
//  @Test
//  void testFindByDate() {
//    String date = "2023-01-01";
//    Date expectedDate = new Date();
//    expectedDate.setId(1L);
//    when(dateRepository.findDateByCoordinatesDate(date)).thenReturn(expectedDate);
//
//    Date result = dateRepositoryImplementor.findByDate(date);
//    assertEquals(expectedDate, result);
//  }
//
//
//  @Test
//  void testGetCoordinatesByDateId() {
//    Long id = 1L;
//    Coordinates coordinates1 = new Coordinates();
//    coordinates1.setId(1L);
//    Coordinates coordinates2 = new Coordinates();
//    coordinates2.setId(2L);
//    Set<Coordinates> coordinatesSet = Set.of(coordinates1, coordinates2);
//    when(dateRepository.getCoordinatesByDateId(id)).thenReturn(coordinatesSet);
//
//    Set<Coordinates> result = dateRepositoryImplementor.getCoordinatesByDateId(id);
//    assertEquals(coordinatesSet, result);
//  }
//}
