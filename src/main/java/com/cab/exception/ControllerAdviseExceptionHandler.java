package com.cab.exception;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import javax.persistence.EntityNotFoundException;



@ControllerAdvice
public class ControllerAdviseExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDate.now());
		body.put("status", status.value());

		List<String> FeildErrors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getField())
				.collect(Collectors.toList());

		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());
		body.put("FeildErrors", FeildErrors);
		body.put("errors", errors);

		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
		
	@ExceptionHandler(CabBookingHistoryNotFound.class)
	public ResponseEntity<Object> handleNoCabBookingHistoryNotFound(CabBookingHistoryNotFound ex, WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("message", " no cabs are available");
		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Object> handleUserNotfoundException(UserNotFoundException ex, WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("message", ex.getMessage());
		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}
	
	
	
	
	
	
	@ExceptionHandler(InvalidBookingException.class)
	public ResponseEntity<Object> handleUserNotFoundException(InvalidBookingException ex, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());

		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(InvalidCancellationException.class)
	public ResponseEntity<Object> handleUserNotFoundException(InvalidCancellationException ex, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());

		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}
	 @ExceptionHandler(ConstraintViolationException.class)
	  public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex,
	  WebRequest request) {
		 Map<String, Object> body = new LinkedHashMap<>();
			body.put("timestamp", LocalDateTime.now());
			body.put("message", "does not enter duplicate values");

			return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
	    
	    }
	 @ExceptionHandler(EmptyResultDataAccessException.class)
	  public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex,
	  WebRequest request) {
		 Map<String, Object> body = new LinkedHashMap<>();
			body.put("timestamp", LocalDateTime.now());
			body.put("message", "no data found with id");

			return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
	    
	    }
	 @ExceptionHandler(EntityNotFoundException.class)
	  public ResponseEntity<Object> EntityNotFoundException(EntityNotFoundException ex,
	  WebRequest request) {
		 Map<String, Object> body = new LinkedHashMap<>();
			body.put("timestamp", LocalDateTime.now());
			body.put("message", "no data found ");

			return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
	    
	    }

	
	

}
