package com.qa.holiday.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qa.holiday.model.HolidayBooking;

//Repo class has all the basic methods stored we may need to access our database
//e.g. repo.findAll() - return all data
//e.g. repo.save(Object)-Create an object and save it into the db

//JpaRepository takes in our object type (entity) AND the type for our id
public interface Repo extends JpaRepository<HolidayBooking, Long> {

}
