package com.cab.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cab.model.Cab;

@Repository
public interface CabRepository extends JpaRepository<Cab, Long> {

	List<Cab> findAllByDateTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

}
