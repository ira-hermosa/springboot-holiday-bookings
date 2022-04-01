package com.qa.holiday.testing.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.holiday.model.HolidayBooking;

//Telling Spring that this is a test class
//Our tests will run on a random port. If the port is already in use, choose a new one
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)

//Set up our Mock MVC(replacing postman) to work with the project
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)//Allows us to use @BeforeAll in a static manner

//The following statement means that everytime we run the test, it will create SQL table
@Sql(scripts = {"classpath:test-data.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("dev")//When running tests it will use the dev profile


public class ControllerTest {
	
	//Plugs our MockMvc into the spring app directly
	@Autowired
	private MockMvc mvc; //Object that performs a mock request (like postman), imports in springFramework
	
	@Autowired
	private ObjectMapper mapper;//Converts Java Objects to JSON Strings {"country": "wales", "weather": "rainy"}
	
	//Test Objects
	HolidayBooking booking1 = new HolidayBooking("country1", "weather1", 1, true);
	HolidayBooking booking2 = new HolidayBooking("country2", "weather2", 2, true);
	HolidayBooking booking3 = new HolidayBooking("country3", "weather3", 3, false);
	HolidayBooking booking4 = new HolidayBooking("country4", "weather4", 4, false);
	
	//The following objects will be used to get data out of database, hence we need the id. These should reflect the test-data.sql
	HolidayBooking booking1ID = new HolidayBooking(1l,"country1", "weather1", 1, true);
	HolidayBooking booking2ID = new HolidayBooking(2l,"country2", "weather2", 2, true);
	HolidayBooking booking3ID = new HolidayBooking(3l,"country3", "weather3", 3, false);
	HolidayBooking booking4ID = new HolidayBooking(4l,"country4", "weather4", 4, false);
	
	
	@Test
	public void testCreate() throws Exception {
		//Arrange
		//Converting our Test Object into a JSON string called bookingJson
		String bookingJson = mapper.writeValueAsString(booking1);
		
		//Import RequestBuilder from Spring framework
		//= Request type, e.g. get, push, post
		//("/request path)

		RequestBuilder req = post("/createBooking").contentType(MediaType.APPLICATION_JSON).content(bookingJson);
		//The above statement is equivalent to writing our request in postman BEFORE clicking send
		
		
		//Act
		//Making a ResultMatcher object (mvc thing), it is either true of false
		//this depends on the status
		ResultMatcher checkStatus = status(). isCreated(); //Is the status code of our request created(201)
		ResultMatcher checkBody = content().string("Booking added with ID: 5"); //import in result string, referring to your test object in relation to the existing data in database
		
		//Assert
		//tell our mvc(postman mock) to run the request (click send)
		//run the request and checkStatus SHOULD be strue (status code is correct)
		//AND checkBody SHOULD Be true (response body is correct)
		mvc.perform(req).andExpect(checkStatus).andExpect(checkBody);
	}


	@Test
	public void testUpdate() throws Exception {
		
		// Arrange
		// The updated object we'll be passing in
		HolidayBooking updatedBooking = new HolidayBooking("new country", "new weather", 10, false);
		
		// Converting our object to JSON
		String updatedBookingJson = mapper.writeValueAsString(updatedBooking);
		
		// requestBuilder takes in the , request type, Path and JSON object
		RequestBuilder req = put("/update/1").contentType(MediaType.APPLICATION_JSON).content(updatedBookingJson);
		
		ResultMatcher checkStatus = status().isOk();
		ResultMatcher checkBody = content().string("Booking of id: 1 has been updated");
		
		mvc.perform(req).andExpect(checkStatus).andExpect(checkBody);
		
	}
	
	
	@Test
	public void testGetBookings()throws Exception{
		//Arrange
		
		List<HolidayBooking> testListBookings = List.of(booking1ID, booking2ID, booking3ID, booking4ID);
		String testListBookingsJson = mapper.writeValueAsString(testListBookings);
		
		//Act
		RequestBuilder req = get("/getBookings");
		ResultMatcher checkStatus = status().isAccepted(); //is the response status accepted?
		ResultMatcher checkBody = content().json(testListBookingsJson);
		
		//Assert
		mvc.perform(req).andExpect(checkStatus).andExpect(checkBody);
	}
	
	@Test
	public void testDeleteId() throws Exception{
	
		//Act
		RequestBuilder req = delete("/delete/1");
		ResultMatcher checkStatus = status(). isAccepted(); //Is the status code of our request created(201)
		ResultMatcher checkBody = content().string("Booking of id: 1 deleted"); 
		//Assert
		
		mvc.perform(req).andExpect(checkStatus).andExpect(checkBody);
		
		
	}
	
	@Test
	public void testDeleteAll() throws Exception{
		//Arrange
		
		//Act
		RequestBuilder req = delete("/deleteAll");
		ResultMatcher checkStatus = status(). isCreated(); //Is the status code of our request created(201)
		ResultMatcher checkBody = content().string("All bookings have been deleted"); 
		
		//Assert
		mvc.perform(req).andExpect(checkStatus).andExpect(checkBody);
		
		
	}
	
	@Test
	public void testPostArray() throws Exception{
		List<HolidayBooking> testList = List.of(booking1, booking2);
		String bookingJson = mapper.writeValueAsString(testList);
		RequestBuilder req = post("/postArray").contentType(MediaType.APPLICATION_JSON).content(bookingJson);
		ResultMatcher checkStatus = status().isCreated();
		ResultMatcher checkBody = content().string("Bookings have been created");
		mvc.perform(req).andExpect(checkStatus).andExpect(checkBody);
		
	}
	
	@Test
	public void testGetId() throws Exception{
		String bookingJson = mapper.writeValueAsString(booking1ID);
		RequestBuilder req = get("/get/1").contentType(MediaType.APPLICATION_JSON).content(bookingJson);
		ResultMatcher checkStatus = status().isAccepted();
		ResultMatcher checkBody = content().json(bookingJson);
		mvc.perform(req).andExpect(checkStatus).andExpect(checkBody);
	}
	
}
