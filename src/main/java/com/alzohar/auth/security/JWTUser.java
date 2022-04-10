package com.alzohar.auth.security;

import java.io.Serializable;

import lombok.Data;

@Data
public class JWTUser implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;

}
