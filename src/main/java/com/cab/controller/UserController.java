package com.cab.controller;

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

import com.cab.dto.ResponseMessageDto;
import com.cab.dto.UserDto;
import com.cab.dto.UserLoginDto;
import com.cab.exception.UserNotFoundException;
import com.cab.service.UserService;
import com.cab.service.UserServiceImpl;



@RestController
@RequestMapping("/users")
public class UserController {
	
	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	UserService userService;
	

	@PostMapping("/register")
	public ResponseEntity<UserLoginDto> userRegister(@Valid @RequestBody UserDto userDto) throws Exception {
		logger.info("user registration");
		UserLoginDto userLoginDto = userService.createUser(userDto);

		return new ResponseEntity<>(userLoginDto, HttpStatus.OK);

	}
	
	@PostMapping("/login")
	public ResponseEntity<ResponseMessageDto> login(@Valid @RequestBody UserLoginDto userLoginDto) throws UserNotFoundException  {
		logger.info("user login");
		ResponseMessageDto responseMessageDto = new ResponseMessageDto();
		userService.login(userLoginDto);
		responseMessageDto.setMessage("user Logged in Sucessfully");
		return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);

	}


}
