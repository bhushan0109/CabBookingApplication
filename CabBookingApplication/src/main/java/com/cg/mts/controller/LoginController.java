package com.cg.mts.controller;

import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.mts.entities.Admin;
import com.cg.mts.entities.Customer;
import com.cg.mts.entities.Driver;
import com.cg.mts.entities.User;
import com.cg.mts.exception.InvalidUserOrPasswordException;
import com.cg.mts.exception.UserAlreadyExistException;
import com.cg.mts.jwt.JwtProvider;
import com.cg.mts.repository.IUserRepository;
import com.cg.mts.util.LoginServiceImpl;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/login")
public class LoginController {
	@Autowired
	LoginServiceImpl lServiceImpl;

	@Autowired(required = false)
	AuthenticationManager authenticationManager;
	@Autowired
	private IUserRepository iUserRepository;

	@Autowired
	JwtProvider jwtProvider;

	@GetMapping("/customer/{username}/{password}")
	public String customerLogin(@PathVariable("username") String username, @PathVariable("password") String password)
			throws InvalidUserOrPasswordException {

		Integer costomerId;
		JSONObject jsonObject = new JSONObject();
		Customer customer = new Customer();
		customer.setUsername(username);
		customer.setPassword(password);
		costomerId = lServiceImpl.validateCredintials(customer);
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(customer.getUsername(), customer.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtProvider.generateJwtToken(authentication);
//				LoginResponseDto loginResDto = new LoginResponseDto();

		Optional<User> findByUsername = iUserRepository.findByUsername(customer.getUsername());
		if (findByUsername.isPresent()) {
			jsonObject.put("status", "failure");
			// throw new UserAlreadyExistException("Admin with given " + admin.getUsername()
			// + " already exist");
		}
//				User user2 = findByUsername.get();

//				loginResDto.setRole(user2.getRole());
//				loginResDto.setToken(jwt);
//				loginResDto.setUserId(user2.getUserId());
//				loginResDto.setUsername(user2.getUsername());

		try {
			costomerId = lServiceImpl.validateCredintials(customer);
			if (costomerId != null) {
				jsonObject.put("status", "success");
				jsonObject.put("adminId", costomerId);
				jsonObject.put("jwtToken", "Bearer " +jwt);
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
			throws InvalidUserOrPasswordException, UserAlreadyExistException {

		Integer adminId;
		JSONObject jsonObject = new JSONObject();
		Admin admin = new Admin();
		admin.setUsername(username);
		admin.setPassword(password);
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(admin.getUsername(), admin.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtProvider.generateJwtToken(authentication);
//			LoginResponseDto loginResDto = new LoginResponseDto();

		Optional<User> findByUsername = iUserRepository.findByUsername(admin.getUsername());
		if (findByUsername.isPresent()) {
			jsonObject.put("status", "failure");
			// throw new UserAlreadyExistException("Admin with given " + admin.getUsername()
			// + " already exist");
		}
//			User user2 = findByUsername.get();

//			loginResDto.setRole(user2.getRole());
//			loginResDto.setToken(jwt);
//			loginResDto.setUserId(user2.getUserId());
//			loginResDto.setUsername(user2.getUsername());

		try {
			adminId = lServiceImpl.validateCredintials(admin);
			if (adminId != null) {
				jsonObject.put("status", "success");
				jsonObject.put("adminId", adminId);
				jsonObject.put("jwtToken", "Bearer " +jwt);
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
		Driver driver = new Driver();
		driver.setUsername(username);
		driver.setPassword(password);
		driverId = lServiceImpl.validateCredintials(driver);
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(driver.getUsername(), driver.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtProvider.generateJwtToken(authentication);
//			LoginResponseDto loginResDto = new LoginResponseDto();

		Optional<User> findByUsername = iUserRepository.findByUsername(driver.getUsername());
		if (findByUsername.isPresent()) {
			jsonObject.put("status", "failure");
			// throw new UserAlreadyExistException("Admin with given " + admin.getUsername()
			// + " already exist");
		}
//			User user2 = findByUsername.get();

//			loginResDto.setRole(user2.getRole());
//			loginResDto.setToken(jwt);
//			loginResDto.setUserId(user2.getUserId());
//			loginResDto.setUsername(user2.getUsername());

		try {
			driverId = lServiceImpl.validateCredintials(driver);
			if (driverId != null) {
				jsonObject.put("status", "success");
				jsonObject.put("adminId", driverId);
				jsonObject.put("jwtToken", "Bearer " +jwt);
			} else {
				jsonObject.put("status", "failure");
			}
		} catch (Exception e) {
			throw new InvalidUserOrPasswordException("Invalid Username/Password");
		}
		return jsonObject.toString();
	}

}
