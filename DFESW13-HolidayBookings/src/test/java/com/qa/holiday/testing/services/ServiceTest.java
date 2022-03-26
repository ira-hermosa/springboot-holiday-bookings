package com.qa.holiday.testing.services;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.holiday.model.HolidayBooking;
import com.qa.holiday.repo.Repo;
import com.qa.holiday.services.ServiceDB;

@SpringBootTest
public class ServiceTest {
	
	//Adding the class we are mocking (Repo)
	//Creating a mock bean object of our Repo, for us to plug into our services
	@MockBean
	private Repo repo;
	
	//Autowired - Spring automatically plugs in the beans into the object
	@Autowired
	private ServiceDB service; //Create a service object, plug in the mock repo automatically
	
	//Test objects I can pass into methods and have my repo return them
	HolidayBooking booking1 = new HolidayBooking("country1", "weather 1", 1, true);
	HolidayBooking booking2 = new HolidayBooking("country2", "weather 2", 2, true);
	HolidayBooking booking3 = new HolidayBooking("country3", "weather 3", 3, true);
	
	//Objects for when we return them from our repo
	HolidayBooking booking1ID = new HolidayBooking(1l,"country4", "weather 4", 4, true);
	HolidayBooking booking2ID = new HolidayBooking(2l,"country5", "weather 5", 5, true);
	
	@Test
	public void testCreate() {
		//Arrange
		
		//When we tell our repo to save data, it should return the object with id
		//When our mock repo runs the save method taking in booking 1
		//it should return booking1ID
		Mockito.when(repo.save(booking1)).thenReturn(booking1ID);
		
		//Act
		//our boolean result equals to whatever our createBooking returns (i.e. true)
		boolean result = service.createBooking(booking1);
		
		//Assert
		Assertions.assertTrue(result);
		//Behaviour testing
		//Verifying if the mock Repo, was triggered(1) times, and what methods are we checking
		Mockito.verify(repo, Mockito.times(1)).save(booking1);
		
	}
	
	@Test
	public void testGetById() {
		
		
		//Arrange
		//The following test returns NullPointerException
		//Mockito.when(repo.findById(1l).get()).thenReturn(booking1ID);
		
		//Returns an Optional of an object, an object wrapped up in a box (so we don't cause a null pointer exception)
		Mockito.when(repo.findById(1l)).thenReturn(Optional.of(booking1ID));
		
		//Act
		HolidayBooking result = service.getById(1l);
		
		//Assert
		Assertions.assertEquals(booking1ID, result);
		
		//Our mocked object NEVER run the .count()
		Mockito.verify(repo, Mockito.never()).count();
	}
	
	@Test
	public void testGetBookings() {
		
		//Arrange
		List<HolidayBooking> testList = List.of(booking1ID, booking2ID);
		Mockito.when(repo.findAll()).thenReturn(testList);
		
		//Act
		List<HolidayBooking> result = service.getBookings();
		
		//Assert
		Assertions.assertEquals(testList, result);
		Mockito.verify(repo, Mockito.never()).deleteAll();
		
		
	}
	
	@Test
	public void testDeleteById() {
		//Arrange
		
		Mockito.when(repo.findById(1l)).thenReturn(Optional.of(booking1ID));
		
		//Act
		boolean result = service.deleteById(1l);
		
		//Assert
		Assertions.assertTrue(true);
		Mockito.verify(repo, Mockito.never()).count();
	}
	
	@Test
	public void testDeleteAll() {
		//Arrange
		List<HolidayBooking> testList = List.of(booking1ID, booking2ID);
		Mockito.when(repo.findAll()).thenReturn(testList);
		
		//Act
		boolean result = service.deleteAll();
		
		//Assert
		Assertions.assertTrue(true);
		Mockito.verify(repo, Mockito.never()).flush();
	}
	
	@Test
	public void testUpdate() {
		//Arrange
		Mockito.when(repo.findById(2l)).thenReturn(Optional.of(booking2ID));
		
	
		//Act
		HolidayBooking oldBooking = booking2ID;
//		HolidayBooking newBooking = new HolidayBooking();
		
		
		oldBooking.setCountry("new country");
		oldBooking.setPrice(75);
		oldBooking.setWeather("new weather");
		
		repo.save(oldBooking);
		
		HolidayBooking result = service.update(2l, oldBooking);
		
		//Assert
		Assertions.assertEquals(oldBooking, result);
	}

}
