package com.cg.mts.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.mts.entities.Admin;
import com.cg.mts.entities.Customer;
import com.cg.mts.entities.Driver;
import com.cg.mts.exception.InvalidUserOrPasswordException;
import com.cg.mts.util.LoginServiceImpl;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/login/")
public class LoginController {
	@Autowired
	LoginServiceImpl lServiceImpl;

	@GetMapping("/customer/{username}/{password}")
	public String customerLogin(@PathVariable("username") String username,
			@PathVariable("password") String password) throws InvalidUserOrPasswordException {

		Integer costomerId;
		JSONObject jsonObject = new JSONObject();
		try {
			Customer customer = new Customer();
			customer.setUsername(username);
			customer.setPassword(password);
			costomerId = lServiceImpl.validateCredintials(customer);
			if (costomerId != null) {
				jsonObject.put("status", "success");
				jsonObject.put("costomerId", costomerId);
			} else {
				jsonObject.put("status", "failure");
			}
		} catch (Exception e) {
			throw new InvalidUserOrPasswordException("Invalid Username/Password");
		}
		return jsonObject.toString();
	}

	@GetMapping("/admin/{username}/{password}")
	public String adminLogin(@PathVariable("username") String username, @PathVariable("password") String password)
			throws InvalidUserOrPasswordException {

		Integer adminId;
		JSONObject jsonObject = new JSONObject();
		try {
			Admin admin = new Admin();
			admin.setUsername(username);
			admin.setPassword(password);
			adminId = lServiceImpl.validateCredintials(admin);
			if (adminId != null) {
				jsonObject.put("status", "success");
				jsonObject.put("adminId", adminId);
			} else {
				jsonObject.put("status", "failure");
			}
		} catch (Exception e) {
			throw new InvalidUserOrPasswordException("Invalid Username/Password");
		}
		return jsonObject.toString();
	}

	@GetMapping("/driver/{username}/{password}")
	public String driverLogin(@PathVariable("username") String username, @PathVariable("password") String password)
			throws InvalidUserOrPasswordException {
		Integer driverId;
		JSONObject jsonObject = new JSONObject();
		try {
			Driver driver = new Driver();
			driver.setUsername(username);
			driver.setPassword(password);
			driverId = lServiceImpl.validateCredintials(driver);
			if (driverId != null) {
				jsonObject.put("status", "success");
				jsonObject.put("driverId", driverId);
			} else {
				jsonObject.put("status", "failure");
			}
		} catch (Exception e) {
			throw new InvalidUserOrPasswordException("Invalid Username/Password");
		}
		return jsonObject.toString();
	}
}
