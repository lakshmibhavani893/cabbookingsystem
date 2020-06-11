package com.cab.controller;

import java.text.ParseException;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cab.dto.BookingSearchDto;
import com.cab.dto.BookingServiceDto;
import com.cab.exception.CabBookingHistoryNotFound;
import com.cab.exception.UserNotFoundException;
import com.cab.service.BookingService;
@RestController
@RequestMapping("/bookings")
public class BookingController {

	Logger logger = LoggerFactory.getLogger(BookingController.class);
	@Autowired
	BookingService bookingService;
	
	/**
	 * 
	 * @param bookingSearchDto
	 * @return List<BookingServiceDto>
	 * @throws UserNotFoundException
	 * @throws CabBookingHistoryNotFound
	 * @throws ParseException
	 */
	@PostMapping("/cabhistory")
	public ResponseEntity<List<BookingServiceDto>> history(@Valid @RequestBody BookingSearchDto bookingSearchDto ) throws UserNotFoundException, CabBookingHistoryNotFound, ParseException{
		logger.info("getting the booking history");
		List<BookingServiceDto> bookings= bookingService.getHistory(bookingSearchDto);
		return new ResponseEntity<>(bookings,HttpStatus.OK);
	}
}
