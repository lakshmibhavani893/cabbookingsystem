package com.cab.service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cab.dto.BookingDto;
import com.cab.dto.BookingRequestDto;
import com.cab.dto.CabResponseDto;
import com.cab.dto.CancelDto;
import com.cab.exception.BookingNotFoundException;
import com.cab.exception.CabNotFoundException;
import com.cab.exception.InvalidBookingException;
import com.cab.exception.InvalidCancellationException;
import com.cab.exception.UserNotFoundException;
import com.cab.model.Booking;
import com.cab.model.Cab;
import com.cab.model.User;
import com.cab.repository.BookingRepository;
import com.cab.repository.CabRepository;
import com.cab.repository.UserRepository;




@Service
public class CabService {

	Logger logger = LoggerFactory.getLogger(CabService.class);
	@Autowired
	CabRepository cabRepository;
	@Autowired
	BookingRepository bookingRepository;
	@Autowired
	UserRepository userRepository;

/**
 * This method for getting cab details
 * @param date
 * @return CabResponseDto
 */
	public List<CabResponseDto> getCabDetails(Date date) {
		logger.info("<---------------------- from get cab details -------------------------------------------------->");
		logger.info("date =========>>"+date);
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		logger.info("date =========>>"+localDate);
		List<CabResponseDto> cabResponseDtos = new ArrayList<>();
		List<Cab> cabs = null;
		if (LocalDate.now().isEqual(localDate)) {
			LocalDateTime startLocalDateTime = LocalDateTime.now();
			LocalDateTime endLocalDateTime = localDate.atTime(23, 59);
			logger.info("is true =========>> "+ endLocalDateTime);
			cabs = cabRepository.findAllByDateTimeBetween(startLocalDateTime, endLocalDateTime);
		} else {
			LocalDateTime startLocalDateTime = localDate.atStartOfDay();
			LocalDateTime endLocalDateTime = localDate.atTime(23, 59);;
			logger.info("is false =========>> "+endLocalDateTime);
			cabs = cabRepository.findAllByDateTimeBetween(startLocalDateTime, endLocalDateTime);
		}
		CabResponseDto cabResponseDto = null;
		for (Cab cab : cabs) {
			cabResponseDto = new CabResponseDto();
			LocalDateTime dbLocalDateTime = cab.getDateTime();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
			BeanUtils.copyProperties(cab, cabResponseDto);
			cabResponseDto.setDateTime(dbLocalDateTime.format(formatter));
			cabResponseDtos.add(cabResponseDto);
		}
		return cabResponseDtos;

	}
	/**
	 * This method for booking the cab
	 * 
	 * @param bookingRequestDto
	 * @return BookingDto
	 * @throws ParseException
	 */
	@Transactional
	public BookingDto saveBooking(BookingRequestDto bookingRequestDto) throws ParseException,UserNotFoundException,CabNotFoundException,InvalidBookingException{
		logger.info("from creating the cab");
		BookingDto bookingDto=new BookingDto();
		User user=userRepository.findById(bookingRequestDto.getUserId()).orElseThrow(()->new UserNotFoundException("user not found"));
		Cab cab=cabRepository.findById(bookingRequestDto.getCabId()).orElseThrow(()->new CabNotFoundException("user not found"));
		LocalDateTime startLocalDateTime = LocalDateTime.now();
		Booking booking=new Booking();
	    int hours=cab.getDateTime().getHour()-startLocalDateTime.getHour();
	  
	    
	    if(cab.getStatus().equals("available")) 
	    {
	    	 if(hours>2||(startLocalDateTime.compareTo(cab.getDateTime())<0))
	    	 {
			booking.setBookingTime(startLocalDateTime);
			booking.setCab(cab);
			booking.setUser(user);
			booking.setStatus("success");
			bookingRepository.save(booking);
			cab.setStatus("not available");
			cabRepository.save(cab);
			}
			else {
				logger.warn("Invalid booking exception");
				throw new InvalidBookingException("You should booked the cab before 2 hours");
			}
	    }
	    else 
	    {
	    	logger.warn("UserNotFoundException");
	    	throw new UserNotFoundException("Sorry, cab is not available");
	    }
	    BeanUtils.copyProperties(booking, bookingDto);
		return bookingDto;
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
	@Transactional
	public BookingDto cancelling(CancelDto cancelDto) throws UserNotFoundException,BookingNotFoundException,InvalidCancellationException {
		logger.info("from cancelling the cab");
		BookingDto bookingDto=new BookingDto();
		Booking booking=bookingRepository.findById(cancelDto.getBookingId()).orElseThrow(()->new BookingNotFoundException("booking not found"));
		User user=userRepository.findById(cancelDto.getUserId()).orElseThrow(()->new UserNotFoundException("user not found"));
		LocalDateTime startLocalDateTime = LocalDateTime.now();
		if(booking.getUser().equals(user)) {
		int hours=booking.getBookingTime().getHour()-startLocalDateTime.getHour();
		if(hours<1||(startLocalDateTime.compareTo(booking.getBookingTime())<0)) {
			logger.info("cancelling the cab before one hour");
			booking.setStatus("canceled");
			booking.setCab(booking.getCab());
			booking.getCab().setStatus("available");
			bookingRepository.save(booking);
			}
		else {
			logger.warn("InvalidCancellationException");
			throw new InvalidCancellationException("You should canceled the cab before one hour");
		}
		}
		else {
			logger.warn("UserNotFoundException");
			throw new UserNotFoundException("the user is not related with this booking");
		}
		BeanUtils.copyProperties(booking,bookingDto);
		return bookingDto;
	}

}

