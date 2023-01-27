package com.cg.mts.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cg.mts.entities.Driver;
import com.cg.mts.entities.RoleName;
import com.cg.mts.entities.User;
import com.cg.mts.exception.DriverNotFoundException;
import com.cg.mts.repository.IDriverRepository;
import com.cg.mts.repository.IUserRepository;

@Service
public class IDriverServiceImpl implements IDriverService {
	@Autowired
	IDriverRepository dDao;
	
	@Autowired
	private IUserRepository iUserRepository;
	
	@Autowired
	PasswordEncoder encoder;


	/**
	 * 
	 * @return List<Driver>
	 */
	@Override
	public List<Driver> displayAllDriver() {
		return dDao.findAll();
	}

	/**
	 * @param driverId
	 * @return Driver
	 * @throws DriverNotFoundException
	 */

	@Override
	public Driver viewDriver(int driverId) throws DriverNotFoundException {
		return dDao.findById(driverId).get();
	}

	/**
	 * @return List<Driver>
	 * @throws DriverNotFoundException
	 */

	@Override
	public List<Driver> viewBestDrivers() throws DriverNotFoundException {
		List<Driver> bestDrivers = dDao.findAll();
		return bestDrivers.stream().filter((d) -> d.getRating() >= 4.5).collect(Collectors.toList());
	}

	/**
	 * @param driver
	 * @return List<Driver>
	 */

	@Override
	public List<Driver> insertDriver(Driver driver) {
		Driver saveAndFlush = dDao.saveAndFlush(driver);
		
		User user =new User();
		user.setPassword(encoder.encode(driver.getPassword()));
		user.setRole(RoleName.DRIVER.name());
		user.setMobileNumber(driver.getMobileNumber());
		user.setUserId(saveAndFlush.getDriverId());
		user.setUsername(driver.getUsername());
		iUserRepository.save(user);
		return dDao.findAll();
	}

	/**
	 * @return Driver
	 * @param driver
	 * @throws DriverNotFoundException
	 */

	@Override
	public Driver updateDriver(Driver driver) throws DriverNotFoundException {

		return dDao.saveAndFlush(driver);
	}

	/**
	 * @param driverId
	 * @return List<Driver>
	 * @throws DriverNotFoundException
	 */

	@Override
	public List<Driver> deleteDriver(int driverId) throws DriverNotFoundException {
		dDao.deleteById(driverId);
		return dDao.findAll();
	}

}
