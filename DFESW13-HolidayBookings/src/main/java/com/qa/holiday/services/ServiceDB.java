package com.qa.holiday.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.qa.holiday.model.HolidayBooking;
import com.qa.holiday.repo.Repo;

@Service
public class ServiceDB {
	
	private Repo repo;

	public ServiceDB(Repo repo) {
		super();
		this.repo = repo;
	}
	
	public boolean createBooking(HolidayBooking booking) {
		repo.save(booking); // Takes in an entity, and puts in the DB
		return true;
	}

	public HolidayBooking getById(long id) {
		return repo.findById(id).get();
	}
	
	//Returns all the objects as a list
	public List<HolidayBooking> getBookings() {
		return repo.findAll();
	}

	
	public boolean deleteById(long id) {
		repo.deleteById(id);
		return true;
		
	}

	public boolean deleteAll() {
		repo.deleteAll();
		return true;
		
	}

	public void update(long id, HolidayBooking booking) {
		
		//Find the object we want to update
		HolidayBooking oldBooking = getById(id);
		
		//Update the properties of this object with new properties
		oldBooking.setCountry(booking.getCountry());
		oldBooking.setWeather(booking.getWeather());
		oldBooking.setPrice(booking.getPrice());
		oldBooking.setAllInclusive(booking.isAllInclusive());
		
		//Saving this old Booking
		repo.save(oldBooking);
		
	}

	public ArrayList<HolidayBooking> addArrayBookings(HolidayBooking[] bookingArray) {
		
		ArrayList<HolidayBooking>bookingList = new ArrayList<>();
		
		for (HolidayBooking booking: bookingArray) {
			bookingList.add(booking);
		}
		return (ArrayList<HolidayBooking>) repo.saveAll(bookingList);
	
}


	

}
