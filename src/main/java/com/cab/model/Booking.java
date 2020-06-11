package com.cab.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table
public class Booking {
	@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bookingId;
	
	private LocalDateTime bookingTime;
	@ManyToOne(cascade = CascadeType.MERGE)
	@JsonIgnore
	private Cab cab;
	@ManyToOne(cascade = CascadeType.MERGE)
	@JsonIgnore
	private User user;
	
	private String status;
	public int getBookingId() {
		return bookingId;
	}
	
	public Booking() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}
	
	public Cab getCab() {
		return cab;
	}
	public void setCab(Cab cab) {
		this.cab = cab;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public LocalDateTime getBookingTime() {
		return bookingTime;
	}

	public void setBookingTime(LocalDateTime bookingTime) {
		this.bookingTime = bookingTime;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public Booking(LocalDateTime bookingTime, Cab cab, User user, String status) {
		super();
		this.bookingTime = bookingTime;
		this.cab = cab;
		this.user = user;
		this.status = status;
	}
	
	
}
