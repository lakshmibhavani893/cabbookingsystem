package com.cab.dto;

import javax.validation.constraints.NotNull;

public class BookingRequestDto {

	@NotNull
	private Long userId;

	@NotNull
	private Long cabId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCabId() {
		return cabId;
	}

	public void setCabId(Long cabId) {
		this.cabId = cabId;
	}

}
