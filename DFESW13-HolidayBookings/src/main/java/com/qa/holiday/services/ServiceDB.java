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
	
	public List<HolidayBooking> addArrayBookings(List<HolidayBooking> bookingArray) {
		List<HolidayBooking>bookingList = new ArrayList<>();
		for (HolidayBooking booking: bookingArray) {
			bookingList.add(booking);
		}
		return (List<HolidayBooking>) repo.saveAll(bookingList);
	
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

	public HolidayBooking update(long id, HolidayBooking booking) {
		
		//Find the object we want to update
		HolidayBooking oldBooking = getById(id);
		
		//Update the properties of this object with new properties
		oldBooking.setCountry(booking.getCountry());
		oldBooking.setWeather(booking.getWeather());
		oldBooking.setPrice(booking.getPrice());
		oldBooking.setAllInclusive(booking.isAllInclusive());
		
		//Saving this old Booking
		return repo.save(oldBooking);
		
		
	}

	// Query to return all objects with a country value of x 
		public List<HolidayBooking> getByCountry(String country){
			// Get a list of all holiday bookings
			// looping through all of the objects
			// If holidayBooking.getCountry == country -> save it to a list
			// Use JpaRepository custom queries -> Repo file
			for (HolidayBooking booking: repo.findAll()) {
				if (booking.getCountry()==country) {
				} 
			}return repo.findByCountry(country);
			
		}
		
		public List<HolidayBooking> getByPriceGreater(float price){
			
			for (HolidayBooking booking: repo.findAll()) {
				if (booking.getPrice()>price) {
					
				}
			}return repo.findByPriceGreaterThan(price);
			
		}
				
		public List<HolidayBooking> getByAllInclusive(boolean bool){
			for (HolidayBooking booking: repo.findAll()) {
				if (booking.isAllInclusive() == bool) {
			
		}
				}return repo.findByAllInclusive(bool);
		}
		
		
	
		public List<HolidayBooking> findByWeather (String weather){
			for (HolidayBooking booking: repo.findAll()) {
				if (booking.getWeather()==weather) {
					
				}
			}return repo.findByWeather(weather);
			
		}
	
		public List<HolidayBooking>getAllOrderByCountry(){
			return repo.findByOrderByCountryAsc();
		}
		
		public List<HolidayBooking>getByOrderByPriceDesc(){
			return repo.findByOrderByPriceDesc();
		}


}
