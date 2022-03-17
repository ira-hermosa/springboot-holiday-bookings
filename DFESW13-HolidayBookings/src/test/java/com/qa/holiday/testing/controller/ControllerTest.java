package com.qa.holiday.testing.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
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

@Sql(scripts = {"classpath:data.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class ControllerTest {
	
	//Plugs our MockMvc into the spring app directly
	@Autowired
	private MockMvc mvc; //Object that performs a mock request (like postman), imports in springFramework
	
	@Autowired
	private ObjectMapper mapper;//Converts Java Objects to JSON Strings {"country": "wales", "weather": "rainy"}
	
	//Test Object
	HolidayBooking testBooking = new HolidayBooking("test country", "test weather", 1, true);
	
	//This will run before all other tests once
	@BeforeAll
	public void setup() throws Exception {
		String bookingJson = mapper.writeValueAsString(testBooking);
		RequestBuilder req = post("/createBooking").contentType(MediaType.APPLICATION_JSON).content(bookingJson);
		mvc.perform(req);
	}
	
	
	
	@Test
	public void testCreate() throws Exception {
		//Arrange
		//Converting our Test Object into a JSON string called bookingJson
		String bookingJson = mapper.writeValueAsString(testBooking);
		
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
	public void testGetId() throws Exception {
		//Arrange
		HolidayBooking testBookingId = testBooking; //Making anew booking object equal to our testBooking Object cause our testBooking doesn't have id yet
		testBookingId.setId(5l);// Setting the id of our object to be 6, identical to how it will be 
		
		String testBookingIDJson = mapper.writeValueAsString(testBookingId); //converting our obj (with id) to JSON
		
		//Act
		//Passing in the path variable5, but is added as part of the string
		
		RequestBuilder req = get("/get/5");
		ResultMatcher checkStatus = status().isAccepted(); //is the response status accepted?
		ResultMatcher checkBody = content().json(testBookingIDJson);
		
		//Assert
		mvc.perform(req).andExpect(checkStatus).andExpect(checkBody);
	}
	
	@Test
	public void testDeleteId() throws Exception{
		//Arrange
		HolidayBooking testBookingId = testBooking;
		testBookingId.setId(5l);
		
		
		//Act
		RequestBuilder req = delete("/delete/5");
		ResultMatcher checkStatus = status(). isAccepted(); //Is the status code of our request created(201)
		ResultMatcher checkBody = content().string("Booking of index: 5deleted"); 
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
	
	

}
