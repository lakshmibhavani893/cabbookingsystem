package com.cab.service;

import com.cab.dto.UserDto;
import com.cab.dto.UserLoginDto;
import com.cab.exception.UserNotFoundException;
import com.cab.model.User;

public interface UserService {
	

	public UserLoginDto createUser(UserDto userDto);

	public User login(UserLoginDto userLoginDto) throws UserNotFoundException;

}
