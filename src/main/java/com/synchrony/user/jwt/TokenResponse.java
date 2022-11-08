package com.synchrony.user.jwt;

import com.synchrony.user.model.Response;

import lombok.Data;

@Data
public class TokenResponse extends Response {

	private String token;

	public TokenResponse() {

	}

	public TokenResponse(String token) {
		super();
		this.token = token;
	}

}
