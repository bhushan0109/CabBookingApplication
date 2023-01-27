package com.cg.mts.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.mts.dto.LoginResponseDto;
import com.cg.mts.entities.Admin;
import com.cg.mts.entities.Customer;
import com.cg.mts.entities.TripBooking;
import com.cg.mts.entities.User;
import com.cg.mts.exception.AdminNotFoundException;
import com.cg.mts.exception.CustomerNotFoundException;
import com.cg.mts.exception.InvalidUserOrPasswordException;
import com.cg.mts.exception.UserAlreadyExistException;
import com.cg.mts.jwt.JwtProvider;
import com.cg.mts.repository.IUserRepository;
import com.cg.mts.service.IAdminService;
import com.cg.mts.service.ICustomerService;
import com.cg.mts.util.LoginService;


@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

	@Autowired
	IAdminService ias;

	@Autowired
	LoginService ls;

	@Autowired
	ICustomerService cusService;

	@Autowired
	private IUserRepository iUserRepository;

	@Autowired(required = false)
	AuthenticationManager authenticationManager;

	@Autowired
	JwtProvider jwtProvider;

	/**
	 * validateAdmin
	 * 
	 * @param admin
	 * @return String
	 * @throws InvalidUserOrPasswordException
	 * @throws UserAlreadyExistException 
	 */
	@PostMapping("/login")
	public String validateAdmin(@RequestBody Admin user) throws InvalidUserOrPasswordException, UserAlreadyExistException {
		Integer adminId;

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtProvider.generateJwtToken(authentication);
//		LoginResponseDto loginResDto = new LoginResponseDto();

		Optional<User> findByUsername = iUserRepository.findByUsername(user.getUsername());
		if (findByUsername.isPresent()) {
			throw new UserAlreadyExistException("Admin with given " + user.getUsername() + " already exist");
		}
//		User user2 = findByUsername.get();
		
//		loginResDto.setRole(user2.getRole());
//		loginResDto.setToken(jwt);
//		loginResDto.setUserId(user2.getUserId());
//		loginResDto.setUsername(user2.getUsername());
		
		JSONObject jsonObject = new JSONObject();
		try {
			adminId = ls.validateCredintials(user);
			if (adminId != null) {
				jsonObject.put("status", "success");
				jsonObject.put("adminId", adminId);
				jsonObject.put("jwtToken", jwt);
			} else {
				jsonObject.put("status", "failure");
			}
		} catch (Exception e) {
			throw new InvalidUserOrPasswordException("Invalid Username/Password");
		}
		return jsonObject.toString();
	}

	/**
	 * viewAllAdmin
	 * 
	 * @return List<Admin>
	 */

	@GetMapping
	public List<Admin> viewALlAdmin() {
		return ias.viewALlAdmin();
	}

	/**
	 * insertAdmin
	 * 
	 * @param admin
	 * @return Admin
	 * @throws UserAlreadyExistException
	 */

	@PostMapping
	public Admin insertAdmin(@RequestBody Admin admin) throws UserAlreadyExistException {
		Optional<User> findByUsername = iUserRepository.findByUsername(admin.getUsername());
		if (findByUsername.isPresent()) {
			throw new UserAlreadyExistException("Admin with given " + admin.getUsername() + " already exist");
		}

		return ias.insertAdmin(admin);
	}

	/**
	 * deleteAdmin
	 * 
	 * @param adminId
	 * @return List<Admin>
	 * @throws AdminNotFoundException
	 */

	@DeleteMapping("/{adminId}")
	public List<Admin> deleteAdmin(@PathVariable int adminId) throws AdminNotFoundException {
		List<Admin> s = null;
		try {
			s = ias.deleteAdmin(adminId);

		} catch (Exception e) {
			throw new AdminNotFoundException("Admin with given ID: " + adminId + " Not Found to Delete");
		}
		return s;

	}

	/**
	 * updateAdmin
	 * 
	 * @param admin
	 * @return Admin
	 * @throws AdminNotFoundException
	 */

	@PutMapping
	public Admin updateAdmin(@RequestBody Admin admin) throws AdminNotFoundException {
		Admin a = null;

		try {
			a = ias.getAdminById(admin.getAdminId());
			ias.updateAdmin(admin);
		} catch (Exception e) {
			throw new AdminNotFoundException("Admin Not Found to Update");
		}
		return a;
	}

	/**
	 * GetAdminById
	 * 
	 * @param adminId
	 * @return Admin
	 * @throws AdminNotFoundException
	 */
	@GetMapping("/{adminId}")
	public Admin GetAdminById(@PathVariable int adminId) throws AdminNotFoundException {
		Admin a = null;

		try {
			a = ias.getAdminById(adminId);

		} catch (Exception e) {
			throw new AdminNotFoundException("Admin with ID: " + adminId + " not found!");
		}
		return a;
	}

	/**
	 * getAllTrips
	 * 
	 * @param customerId
	 * @return List<TripBooking>
	 * @throws CustomerNotFoundException
	 */

	@SuppressWarnings("unused")
	@GetMapping("/alltrips/{customerId}")
	public List<TripBooking> getAllTrips(@PathVariable int customerId) throws CustomerNotFoundException {

		Customer c = null;
		List<TripBooking> t = null;
		try {
			c = cusService.viewCustomer(customerId);
			t = ias.getAllTrips(customerId);
		} catch (Exception e) {
			throw new CustomerNotFoundException("Can not find trips of Customer ID: " + customerId);
		}
		return t;
	}

	/**
	 * getTripsCabwise(
	 * 
	 * @return List<TripBooking>
	 */

	@GetMapping("/cabwise")
	public List<TripBooking> getTripsCabwise() {
		return ias.getTripsCabwise();
	}

	/**
	 * getTripsCustomerwise
	 * 
	 * @return List<TripBooking>
	 */

	@GetMapping("/customerwise")
	public List<TripBooking> getTripsCustomerwise() {
		return ias.getTripsCustomerwise();
	}

	/**
	 * getTripsDatewise
	 * 
	 * @return List<TripBooking>
	 */

	@GetMapping("/datewise")
	public List<TripBooking> getTripsDatewise() {
		return ias.getTripsDatewise();
	}

	/**
	 * getAllTripsForDays
	 * 
	 * @param customerId
	 * @param fromDate
	 * @param toDate
	 * @return List<TripBooking>
	 * @throws CustomerNotFoundException
	 */

	@GetMapping("fordays/{customerId}/{fromDate}/{toDate}")
	public List<TripBooking> getAllTripsForDays(@PathVariable("customerId") int customerId,
			@PathVariable("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
			@PathVariable("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate)
			throws CustomerNotFoundException {
		return ias.getAllTripsForDays(customerId, fromDate, toDate);
	}

}