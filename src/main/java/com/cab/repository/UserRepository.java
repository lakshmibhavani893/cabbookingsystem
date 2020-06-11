package com.cab.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cab.model.User;



public interface UserRepository extends JpaRepository<User, Long>{

	User findByEmailAndPassword(String email, String password);

}
