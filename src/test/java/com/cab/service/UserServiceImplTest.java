package com.cab.service;

 


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import com.cab.model.User;
import com.cab.repository.UserRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserServiceImplTest {
    
	@InjectMocks
    UserServiceImpl userServiceImpl;
    
    @Mock
    UserRepository userRepository;
    
    @Test
    public void testCreateUserForPositive() {
        User user = new User();
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Assert.assertEquals(user, userRepository.save(user));
    }

 
    @Test
    public void testCreateStudentForNegative() {
        User user = new User();
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Assert.assertEquals(user, userRepository.save(user));
    }
    
   

 

}
 