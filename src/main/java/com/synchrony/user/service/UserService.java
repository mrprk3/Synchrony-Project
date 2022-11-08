package com.synchrony.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synchrony.user.model.User;
import com.synchrony.user.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;

	public void save(User user) {
		userRepository.save(user);
	}

	public User userAuthenticate(String username, String password) {
		return userRepository.findByUsernameAndPassword(username, password);
	}

	public User searchUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

} 
