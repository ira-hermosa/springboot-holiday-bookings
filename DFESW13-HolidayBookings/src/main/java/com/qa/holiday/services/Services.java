package com.qa.holiday.services;

import java.util.ArrayList;
import org.springframework.stereotype.Service;
import com.qa.holiday.model.HolidayBooking;
import com.qa.holiday.repo.Repo;

//Annotation tells Spring this is our Services class
//Services is the business Logic, pushing data to database / arrayList, updating deleting etc.

@Service
public class Services {
	
	//Specify that we want to use our repo class
	
	private Repo repo;
	
	//When spring creates the Service Object/bean, it takes in our repo class
	public Services(Repo repo) {
		super();
		this.repo = repo;
	}
	
	//This method replaces SQL queries (INSERT INTO Holiday_bookings)
	public boolean createBookingDb(HolidayBooking booking) {
		repo.save(booking); // Takes in an entity, and puts in the DB
		return true;
	}

	private ArrayList<HolidayBooking>bookingList = new ArrayList<>();
	
	public boolean createBooking(HolidayBooking booking) {
		booking.setId(bookingList.size()+1);
		bookingList.add(booking);
		return true;
		
	}
	
	public HolidayBooking getByIndex(int index) {
		return bookingList.get(index);
	}
	
	public ArrayList<HolidayBooking> getBookings(){
		return bookingList;
	}
	
	public boolean deleteByIndex(int index) {
		bookingList.remove(index);
		return true;
	}
	
	public boolean update(int index, HolidayBooking booking){
		bookingList.set(index, booking);
		return true;
	}
	
	public ArrayList<HolidayBooking> addArrayBookings(HolidayBooking[] bookingArray) {
		for (HolidayBooking booking: bookingArray) {
			bookingList.add(booking);
		}
		return bookingList;
	}
	
	public boolean deleteAll() {
		bookingList.clear();
		return true;
	}

}
