package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.modeles.Reservation;
import com.epf.rentmanager.modeles.Vehicle;
import com.epf.rentmanager.dao.VehicleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {


	@Autowired
	private VehicleDao vehicleDao;

	@Autowired
	private ReservationService reservationService;

	private VehicleService(VehicleDao vehicleDao){this.vehicleDao = vehicleDao;}


	
	
	public long create(Vehicle vehicle) throws ServiceException {
		// TODO: créer un véhicule

		try {
			confirmation(vehicle);
			return vehicleDao.create(vehicle);
		} catch ( DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public long delete(long id) throws ServiceException {
		try {
			List<Reservation> reservations = reservationService.findByVehicleId(id);
			for(Reservation reservation : reservations) {
				reservationService.delete(reservation.getId());
			}
			return vehicleDao.delete(id);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public Vehicle findById(long id) throws ServiceException {
		// TODO: récupérer un véhicule par son id

		return new Vehicle();
	}

	public List<Vehicle> findAll() throws ServiceException {
		// TODO: récupérer tous les clients

		try{
			return vehicleDao.findAll();
		}
		catch (DaoException e) {
			throw new RuntimeException(e);
		}
	}
	private boolean vide(String input) {
		return input == null || input.trim().length() == 0;
	}
	private void confirmation(Vehicle vehicle) throws ServiceException {
		if (vide(vehicle.getConstructeur()))
			throw new ServiceException("Le constructeur est vide");
		if(vehicle.getNb_places() < 2 || vehicle.getNb_places() > 9)
			throw new ServiceException("Le véhicule a un nombre de place qui doit être compris entre 2 et 9");
	}

}
	

