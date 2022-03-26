package com.qa.holiday.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//Put in the annotation @Entity so Spring knows this is an entity
//Will Create at MySQL table called HolidayBooking with these exact columns
//To get all of the data from a database repo.getAll();

@Entity
public class HolidayBooking {
	
	//Tell Spring what property is our primary key
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//Telling spring it will auto generate and auto increment - maybe?
	
	private long id;
	
	//@Column tells Spring this will be a header for our table
	// (unique = true, otherOptions=value)
	
	@Column(nullable = false, length = 30) // value can't be null
	private String country;
	
	@Column(nullable = false, length = 25) // value can't be null
	private String weather;
	
	@Column(nullable = false) // value can't be null
	private float price;
	
	@Column(nullable = false) // value can't be null
	private boolean allInclusive;
	
	//when using Request bodies with Spring you need to include a default constructor
	//Generate using source-generate super constructor
	
	public HolidayBooking() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	//Overloading - Constructors with and without ID
	//One constructor WITH id
	public HolidayBooking(long id, String country, String weather, float price, boolean allInclusive) {
		super();
		this.id = id;
		this.country = country;
		this.weather = weather;
		this.price = price;
		this.allInclusive = allInclusive;
	}

	
	//One constructor without id
	public HolidayBooking(String country, String weather, float price, boolean allInclusive) {
		super();
		this.country = country;
		this.weather = weather;
		this.price = price;
		this.allInclusive = allInclusive;
	}

	//Getters and setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public boolean isAllInclusive() {
		return allInclusive;
	}

	public void setAllInclusive(boolean allInclusive) {
		this.allInclusive = allInclusive;
	}

	@Override
	public int hashCode() {
		return Objects.hash(allInclusive, country, id, price, weather);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HolidayBooking other = (HolidayBooking) obj;
		return allInclusive == other.allInclusive && Objects.equals(country, other.country) && id == other.id
				&& Float.floatToIntBits(price) == Float.floatToIntBits(other.price)
				&& Objects.equals(weather, other.weather);
	}


	@Override
	public String toString() {
		return "HolidayBooking [id=" + id + ", country=" + country + ", weather=" + weather + ", price=" + price
				+ ", allInclusive=" + allInclusive + "]";
	}
	
	

	

	
	

}
