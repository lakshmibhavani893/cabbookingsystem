package com.cab.dto;

import javax.validation.constraints.NotNull;

public class CancelDto {
	
	@NotNull
	private Integer bookingId;
	
	@NotNull
	private Long userId;

	public Integer getBookingId() {
		return bookingId;
	}

	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	

}
