package com.cg.mts.dto;

import javax.validation.constraints.NotNull;


public class LoginResponseDto {
	private String token;
	private String role;
	private Integer userId;

	private String username;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public String toString() {
		return "LoginResponseDto [token=" + token + ", role=" + role + ", userId=" + userId + ", username=" + username
				+ "]";
	}

	
}

