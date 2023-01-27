package com.cg.mts.controller;

import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.mts.entities.Customer;
import com.cg.mts.entities.User;
import com.cg.mts.exception.CustomerNotFoundException;
import com.cg.mts.exception.InvalidUserOrPasswordException;
import com.cg.mts.exception.UserAlreadyExistException;
import com.cg.mts.repository.IUserRepository;
import com.cg.mts.service.ICustomerService;
import com.cg.mts.util.LoginService;
import com.fasterxml.jackson.databind.util.JSONPObject;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	ICustomerService cusService;

	@Autowired
	LoginService ls;
	@Autowired
	private IUserRepository iUserRepository;

	/**
	 * validateCustomer
	 * 
	 * @param customer
	 * @return String
	 * @throws InvalidUserOrPasswordException
	 */

	@PostMapping("/login")
	public String validateCustomer(@RequestBody Customer customer) throws InvalidUserOrPasswordException {
		Integer customerId;
		JSONObject jsonObject = new JSONObject();
		try {
			customerId = ls.validateCredintials(customer);
			if (customerId != null) {
				jsonObject.put("status", "success");
				jsonObject.put("customerId", customerId);
			} else {
				jsonObject.put("status", "failure");
			}
		} catch (Exception e) {
			throw new InvalidUserOrPasswordException("Invalid Username/Password");
		}
		return jsonObject.toString();
	}

	/**
	 * insertCustomer
	 * 
	 * @param customer
	 * @return Customer
	 * @throws UserAlreadyExistException 
	 */

	@PostMapping

	public Customer insertCustomer(@RequestBody Customer customer) throws UserAlreadyExistException {
		Optional<User> findByUsername = iUserRepository.findByUsername(customer.getUsername());
		if (findByUsername.isPresent()) {
			throw new UserAlreadyExistException("Admin with given " + customer.getUsername() + " already exist");
		}
		return cusService.insertCustomer(customer);
	}

	/**
	 * 
	 * @param customer
	 * @return Customer
	 * @throws CustomerNotFoundException
	 */

	@SuppressWarnings("unused")
	@PutMapping
	public Customer updateCustomer(@RequestBody Customer customer) throws CustomerNotFoundException {
		Customer cViewer = null;
		Customer c = null;
		try {
			cViewer = viewCustomer(customer.getCustomerId());
			c = cusService.updateCustomer(customer);
		} catch (Exception e) {
			throw new CustomerNotFoundException("Customer Not Found to perform Update Operation!");
		}
		return c;
	}

	/**
	 * deleteCustomer
	 * 
	 * @param customer
	 * @return Customer
	 * @throws CustomerNotFoundException
	 */

	@SuppressWarnings("unused")
	@DeleteMapping
	public Customer deleteCustomer(Customer customer) throws CustomerNotFoundException {
		Customer cViewer = null;
		Customer c = null;
		try {
			cViewer = viewCustomer(customer.getCustomerId());
			c = cusService.deleteCustomer(customer);
		} catch (Exception e) {
			throw new CustomerNotFoundException("Customer Not Found to perform Delete Operation!");
		}
		return c;
	}

	/**
	 * viewCustomers
	 * 
	 * @return List<Customer>
	 */

	@GetMapping(value = "all")
	public List<Customer> viewCustomers() {
		return cusService.viewCustomers();
	}

	/**
	 * viewCustomer
	 * 
	 * @param customerId
	 * @return Customer
	 * @throws CustomerNotFoundException
	 */

	@GetMapping(value = "/{customerId}")
	public Customer viewCustomer(@PathVariable int customerId) throws CustomerNotFoundException {
		Customer c = null;
		try {
			c = cusService.viewCustomer(customerId);
		} catch (Exception e) {
			throw new CustomerNotFoundException("Customer with Id: " + customerId + " Not Found!");
		}
		return c;
	}

}
