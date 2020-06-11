package com.cab.dto;

import javax.validation.constraints.NotNull;

public class BookingSearchDto {
	
	@NotNull
	private long userId;
	
	@NotNull
	private int month;
	
	@NotNull
	private int year;
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
}
