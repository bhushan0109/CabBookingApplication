package com.cg.mts.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cg.mts.entities.Customer;
import com.cg.mts.entities.RoleName;
import com.cg.mts.entities.User;
import com.cg.mts.repository.ICustomerRepository;
import com.cg.mts.repository.IUserRepository;

@Service
public class ICustomerServiceImpl implements ICustomerService {

	@Autowired
	ICustomerRepository cDao;
	@Autowired
	private IUserRepository iUserRepository;

	@Autowired
	PasswordEncoder encoder;

	/**
	 * @param customer
	 * @return Customer
	 */
	@Transactional
	@Override
	public Customer insertCustomer(Customer customer) {
		Customer saveAndFlush = cDao.saveAndFlush(customer);

		User user = new User();
		user.setMobileNumber(customer.getMobileNumber());
		String encodestring = encoder.encode(customer.getPassword());
		user.setPassword(encodestring);
		user.setRole(RoleName.CUSTOMER.name());
		user.setMobileNumber(customer.getMobileNumber());
		user.setUserId(saveAndFlush.getCustomerId());
		user.setUsername(customer.getUsername());
		iUserRepository.save(user);
		return saveAndFlush;
	}

	/**
	 * @param customer
	 * @return Customer
	 */

	@Override
	public Customer updateCustomer(Customer customer) {
		Customer cus = cDao.findById(customer.getCustomerId()).get();
		if (cus != null) {
			cus.setEmail(customer.getEmail());
			cus.setMobileNumber(customer.getMobileNumber());
			cus.setUsername(customer.getUsername());
			cus.setPassword(customer.getPassword());
			cDao.save(cus);
		}
		return cus;
	}

	/**
	 * @param customer
	 * @return Customer
	 */
	@Override
	public Customer deleteCustomer(Customer customer) {
		cDao.delete(customer);
		return customer;
	}

	/**
	 * 
	 * @return List<Customer>
	 */

	@Override
	public List<Customer> viewCustomers() {

		return cDao.findAll();
	}

	/**
	 * @param customerId
	 * @return Customer
	 */

	@Override
	public Customer viewCustomer(int customerId) {
		return cDao.findById(customerId).get();
	}

	@Override
	public Customer validateCustomer(String username, String password) {

		return null;
	}

}
