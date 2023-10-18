package com.practice.jwt.model;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class AuthResponse {

	private String authToken;
	private Object issuer;
	private String expirationTime;

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public Object getIssuer() {
		return issuer;
	}

	public void setIssuer(Object issuer) {
		this.issuer = issuer;
	}

	public String getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(String expirationTime) {
		this.expirationTime = expirationTime;
	}

	@Override
	public String toString() {
		return "AuthResponse [authToken=" + authToken + ", issuer=" + issuer + ", expirationTime=" + expirationTime
				+ "]";
	}

}
