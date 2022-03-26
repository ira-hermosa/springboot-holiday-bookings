package com.qa.holiday.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qa.holiday.model.HolidayBooking;

//Repo class has all the basic methods stored we may need to access our database
//e.g. repo.findAll() - return all data
//e.g. repo.save(Object)-Create an object and save it into the db

//JpaRepository takes in our object type (entity) AND the type for our id
public interface Repo extends JpaRepository<HolidayBooking, Long> {
	
	//Abstract methods
	//Naming convention: Use 'findby' + the order of the properties indicating what we want the methods to do.
	
	public List<HolidayBooking> findByCountry (String country);
	public List<HolidayBooking> findByWeather (String weather);
	public List<HolidayBooking> findByPriceGreaterThan(float price);
	public List<HolidayBooking> findByAllInclusive(boolean bool);
	public List<HolidayBooking> findByOrderByCountryAsc();
	public List<HolidayBooking> findByOrderByPriceDesc();
	

}
