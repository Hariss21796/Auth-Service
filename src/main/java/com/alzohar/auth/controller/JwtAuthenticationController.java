package com.alzohar.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alzohar.auth.entity.User;
import com.alzohar.auth.security.JWTResponse;
import com.alzohar.auth.security.JWTTokenUtil;
import com.alzohar.auth.security.JWTUser;
import com.alzohar.auth.service.UserService;
import com.alzohar.auth.service.impl.JWTUserDetailsService;

@RestController
public class JwtAuthenticationController {

	@Autowired
	private JWTUserDetailsService jwtUserSrv;

	@Autowired
	private JWTTokenUtil jwtToken;

	@Autowired
	private UserService userService;

	@PostMapping("/auth")
	public ResponseEntity<?> createAuthToken(@RequestBody JWTUser user) throws Exception {
		String token = null;
		UserDetails userDetails = jwtUserSrv.loadUserByUsername(user.getUsername());
		if (userDetails != null) {
			token = jwtToken.generateToken(userDetails);
			return ResponseEntity.ok(new JWTResponse(token));
		}
		throw new UsernameNotFoundException("User not found !");
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody User user) throws Exception {
		User userCreated = userService.register(user);
		if (userCreated != null) {
			return ResponseEntity.ok(userCreated);
		}
		throw new UsernameNotFoundException("User Registration failed !");
	}

}
