package com.cg.mts.entities;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity(name = "user_table")
public class User {

	@Id
	private int userId;

	private String password;

	private String role;
	
	private String username;

	private String mobileNumber;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", password=" + password + ", role=" + role + ", username=" + username
				+ ", mobileNumber=" + mobileNumber + "]";
	}
	

}
