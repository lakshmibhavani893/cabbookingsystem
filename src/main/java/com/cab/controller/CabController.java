package com.cab.controller;


import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cab.dto.BookingDto;
import com.cab.dto.BookingRequestDto;
import com.cab.dto.CabResponseDto;
import com.cab.dto.CancelDto;
import com.cab.exception.BookingNotFoundException;
import com.cab.exception.CabNotFoundException;
import com.cab.exception.InvalidBookingException;
import com.cab.exception.InvalidCancellationException;
import com.cab.exception.UserNotFoundException;
import com.cab.service.CabService;

/**
 * 
 * @author Bhavani
 * @version 1.0
 * @since 2020-06-11
 */
@RestController
@RequestMapping("/cabs")
public class CabController {


	Logger logger = LoggerFactory.getLogger(CabController.class);
	@Autowired
	CabService cabService;

	/**
	 * This method for getting cab details
	 * 
	 * @param date
	 * @return List<CabResponseDto>
	 */
	@GetMapping("/search")
	public ResponseEntity<List<CabResponseDto>> getCabDetails(@RequestParam(name = "localDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date date) {
		logger.info("get the cab details");
		List<CabResponseDto> cabs=cabService.getCabDetails(date);
		return new ResponseEntity<>(cabs, HttpStatus.OK);
	}
	
	/**
	 * This method for booking the cab
	 * 
	 * @param bookingRequestDto
	 * @return BookingDto
	 * @throws ParseException
	 * @throws UserNotFoundException
	 * @throws InvalidBookingException
 */
	@PostMapping("")
	public ResponseEntity<BookingDto> saveBooking(@Valid @RequestBody BookingRequestDto bookingRequestDto) throws ParseException,UserNotFoundException,CabNotFoundException,InvalidBookingException{
		logger.info("booking the cab");
		logger.warn("Invalid booking exception");
		BookingDto bookingDto=cabService.saveBooking(bookingRequestDto);
		return new ResponseEntity<>(bookingDto, HttpStatus.OK);
	}
	
	/**
	 * This method for cancel the cab
	 * 
	 * @param cancelDto
	 * @return BookingDto
	 * @throws UserNotFoundException
	 * @throws BookingNotFoundException
	 * @throws InvalidCancellationException
	 */
	@DeleteMapping("/cancel")
	public ResponseEntity<BookingDto> cancel(@Valid @RequestBody CancelDto cancelDto) throws UserNotFoundException,BookingNotFoundException,InvalidCancellationException {
		logger.info("cancelling the cab");
		BookingDto bookingDto=cabService.cancelling(cancelDto);
		return new ResponseEntity<>(bookingDto, HttpStatus.OK);
		
	}
}
