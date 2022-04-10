package com.alzohar.auth.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.alzohar.auth.entity.User;
import com.alzohar.auth.repository.UserRepository;
import com.alzohar.auth.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LogManager.getLogger(UserService.class);

	@Autowired
	private UserRepository repository;

	@Autowired
	PasswordEncoder encoder;

	@Override
	public User register(User user) {

		User fetchUser = repository.getUserByUsername(user.getUsername());
		if (fetchUser != null) {
			LOGGER.error("User already exists for username :: " + user.getUsername());
			throw new UsernameNotFoundException("User already exists for this email");
		}

		encodePassword(user);
		LOGGER.info(user.getUsername() + " user is registred successfuly. ");

		return repository.save(user);
	}

	private void encodePassword(User user) {
		String encodePassword = encoder.encode(user.getPassword());
		user.setPassword(encodePassword);

	}

}
