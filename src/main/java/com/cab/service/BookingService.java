package com.cab.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cab.dto.BookingSearchDto;
import com.cab.dto.BookingServiceDto;
import com.cab.exception.CabBookingHistoryNotFound;
import com.cab.exception.UserNotFoundException;
import com.cab.model.Booking;
import com.cab.model.User;
import com.cab.repository.BookingRepository;
import com.cab.repository.UserRepository;
@Service
public class BookingService {
	Logger logger = LoggerFactory.getLogger(BookingService.class);
	@Autowired
	BookingRepository bookingRepository;
	@Autowired
	UserRepository userRepository;
	/**
	 * 
	 * @param bookingSearchDto
	 * @return BookingServiceDto
	 * @throws CabBookingHistoryNotFound
	 * @throws ParseException
	 * @throws UserNotFoundException
	 */
	public List<BookingServiceDto> getHistory(BookingSearchDto bookingSearchDto) throws CabBookingHistoryNotFound, ParseException, UserNotFoundException {
		logger.info("getting the booking history");
		 User user = userRepository.findById(bookingSearchDto.getUserId()).orElseThrow(()->new UserNotFoundException("user with given details not found"));
		 List<BookingServiceDto> list=new ArrayList<>();
		 List<Booking> bookings= bookingRepository.findBookingByUser(user);
		 if(bookings.isEmpty()) {
			 throw new CabBookingHistoryNotFound();
		 }
		 else {
			 BookingServiceDto bookingServiceDto = null;
				for (Booking booking : bookings) {
					bookingServiceDto = new BookingServiceDto();
						if(booking.getBookingTime().getMonthValue()==bookingSearchDto.getMonth()) {
							if(booking.getBookingTime().getYear()==bookingSearchDto.getYear()){
								BeanUtils.copyProperties(booking, bookingServiceDto);
								list.add(bookingServiceDto);
							}
						}
					}
					return list;
				}
				
		 } 
		


}
