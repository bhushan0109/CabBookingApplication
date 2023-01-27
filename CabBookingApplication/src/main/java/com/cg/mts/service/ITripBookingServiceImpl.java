package com.cg.mts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.mts.entities.TripBooking;
import com.cg.mts.repository.ITripBookingRepository;

@Service
public class ITripBookingServiceImpl implements ITripBookingService {

	@Autowired
	ITripBookingRepository tbDao;

	/**
	 * @param tripBooking
	 * @return TripBooking
	 */
	@Override
	public TripBooking insertTripBooking(TripBooking tripBooking) {
		TripBooking save = tbDao.saveAndFlush(tripBooking);
		return save;
	}

	/**
	 * @param tripBooking
	 * @return TripBooking
	 */

	@Override
	public TripBooking updateTripBooking(TripBooking tripBooking) {
		 TripBooking save = tbDao.saveAndFlush(tripBooking);
		return save;
	}

	/**
	 * @param tripBooking
	 * @return List<TripBooking>
	 */

	@Override
	public List<TripBooking> deleteTripBooking(TripBooking tripBooking) {
		tbDao.deleteById(tripBooking.getTripBookingId());
		return tbDao.findAll();
	}

	/**
	 * @param customerId
	 * @return List<TripBooking>
	 */

	@Override
	public List<TripBooking> viewAllTripsCustomer(int customerId) {
		return tbDao.viewAllTripsCustomer(customerId);
	}

	/**
	 * @param customerId
	 * @return float
	 */

	@Override
	public float calculateBill(int customerId) {
		return tbDao.calculateBill(customerId);
	}

	/**
	 * @param tripBookingId
	 * @return List<TripBooking>
	 */

	@Override
	public List<TripBooking> viewAllTripsById(int tripbookingId) {
		// TODO Auto-generated method stub
		return tbDao.viewAllTripsById(tripbookingId);
	}

}
