package com.cab.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cab.model.Booking;
import com.cab.model.User;



public interface BookingRepository extends JpaRepository<Booking, Integer>{
	Booking findBookingByBookingIdAndUser(long bookingId, User user);

	List<Booking> findByUserAndBookingTimeContaining(User user, Date bookingTime);

	List<Booking> findByUserAndBookingTimeGreaterThan(User user, Date bookingTime);

	List<Booking> findByBookingTimeGreaterThan(Date bookingTime);

	List<Booking> findBookingByUserAndBookingTimeGreaterThan(User user, Date bookingTime);
	List<Booking> findBookingByUser(User user);


}
