package com.cab.service;

import java.util.Random;

import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cab.dto.UserDto;
import com.cab.dto.UserLoginDto;
import com.cab.exception.UserNotFoundException;
import com.cab.model.User;
import com.cab.repository.UserRepository;


@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	UserRepository userRepository;
	
	/**
	 * @param userDto
	 * return UserLoginDto
	 */

	@Override
	public UserLoginDto createUser(UserDto userDto) {
		UserLoginDto userLoginDto=new UserLoginDto();
		logger.info("registration");
		Random random = new Random();
		User user = new User();
		BeanUtils.copyProperties(userDto, user);
		int leftLimit = 97;
		int rightLimit = 122;
		int targetStringLength = 10;

		String generatedString = random.ints(leftLimit, rightLimit + 1).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		user.setPassword(generatedString);
		user = userRepository.save(user);
		BeanUtils.copyProperties(user, userLoginDto);
		return userLoginDto;
	}
	
/**
 * @throws UserNotFoundException 
 * @param userLoginDto
 * return user
 */
	@Override
	public User login(UserLoginDto userLoginDto) throws UserNotFoundException   {
	
		logger.info("login into the application");
		User user = userRepository.findByEmailAndPassword(userLoginDto.getEmail(), userLoginDto.getPassword());
		if (user==null) {
			throw new UserNotFoundException("User does not exists");
		}
		return user;
		
	}
}
