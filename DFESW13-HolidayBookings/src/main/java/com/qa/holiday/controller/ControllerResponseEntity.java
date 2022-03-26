package com.qa.holiday.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.qa.holiday.model.HolidayBooking;
import com.qa.holiday.services.ServiceDB;


//If you have multiple classes with annotation @RestController, Spring will use the first one it finds

@RestController
public class ControllerResponseEntity {
	
// Not using the ArrayList in the controller anymore. This has now been added to the Services class
// private ArrayList<HolidayBooking> bookingList = new ArrayList<>();
	
// Response Entities offer more info when sending a response back
// Response includes a message AND a status code we can specify AND the data it requested
		
// Sending a Delete Request, we respond with "Deleted ID of x" AND code 202 
// Sending a Get request, we respond with the Data requested AND a status code 200
	
// Tell our Controller to use the Services Object
// When Spring creates our Controller, it passes in the Service object	
private ServiceDB service;


// Constructor
public ControllerResponseEntity(ServiceDB service) {
	super();
	this.service = service;
}

//Controller methods	
@PostMapping ("/createBooking")
public ResponseEntity<String> createBooking(@RequestBody HolidayBooking booking) {
// Call the method in the Services class
service.createBooking(booking);

		
//returns a response entity<data it contains>
ResponseEntity<String> response = new ResponseEntity<String>("Booking added with ID: " + booking.getId(), HttpStatus.CREATED);
return response;
	}
	
	
@GetMapping("/get/{id}")
public ResponseEntity<HolidayBooking> getById(@PathVariable("id") long id) {
//Making an object variable called result = the data we're retrieving
HolidayBooking result = service.getById(id);
		
// Making a ResponseEntity that contains the data we're sending
ResponseEntity<HolidayBooking> response = new ResponseEntity<>(result, HttpStatus.ACCEPTED);
return response;
		
	}
	

@GetMapping("/getBookings")
public ResponseEntity<List<HolidayBooking>> getBookings(){
	List<HolidayBooking> response = service.getBookings();
	return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
}



@DeleteMapping("/delete/{id}")
public ResponseEntity<String>deleteById(@PathVariable("id") long id) {
	service.deleteById(id);
	String response = "Booking of id: " + id + " " + "deleted";
	return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	
			
	}
	
	
@PutMapping("/update/{id}")
public ResponseEntity<String>update(@PathVariable("id")long id, @RequestBody HolidayBooking booking) {
	service.update(id, booking);
	String response = "Booking of id: " + id + " " + "has been updated";
	return new ResponseEntity<>(response, HttpStatus.OK);
	
}

@DeleteMapping("/deleteAll")
public ResponseEntity<String>deleteAll(){
	service.deleteAll();
	String response = "All bookings have been deleted";
	return new ResponseEntity<>(response, HttpStatus.CREATED);
}

@PostMapping("/postArray")
public ResponseEntity<List<HolidayBooking>> addArrayBookings(@RequestBody HolidayBooking[] bookingArray) {
	ArrayList<HolidayBooking> response = service.addArrayBookings(bookingArray);
	return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}

//Method to get all bookings with a country value of x
	@GetMapping("/getCountry/{country}")
	public ResponseEntity<List<HolidayBooking>> getByCountry(@PathVariable("country") String country) {
		
		List<HolidayBooking> response = service.getByCountry(country);
		
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
		
	}
	
	// Method to get all bookings with a country value of x
	@GetMapping("/getPrice/{price}")
	public ResponseEntity<List<HolidayBooking>> getByPriceGreater(@PathVariable("price") float price) {
		
		List<HolidayBooking> response = service.getByPriceGreater(price);
		
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
		
	}
	
	@GetMapping("/getAllIncl/{bool}")
	public ResponseEntity<List<HolidayBooking>> getByAllInclusive(@PathVariable("bool") boolean bool) {
		
		List<HolidayBooking> response = service.getByAllInclusive(bool);
		
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
		
	}
	
	@GetMapping("/getWeather/{weather}")
	public ResponseEntity<List<HolidayBooking>> findByWeather(@PathVariable("weather") String weather) {
		
		List<HolidayBooking> response = service.findByWeather(weather);
		
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
		
	}
	
	
}



