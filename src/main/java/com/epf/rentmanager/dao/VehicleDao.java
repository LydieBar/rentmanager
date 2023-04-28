package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.modeles.Client;
import com.epf.rentmanager.modeles.Reservation;
import com.epf.rentmanager.persistence.ConnectionManager;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.modeles.Vehicle;
import com.epf.rentmanager.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository
public class VehicleDao {

	@Autowired
	private ReservationService reservationService;

	private VehicleDao() {}
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, nb_places) VALUES(?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur,  nb_places FROM Vehicle;";
	
	public long create(Vehicle vehicle) throws DaoException {

		try {

			Connection connection = ConnectionManager.getConnection();

			PreparedStatement ps = connection.prepareStatement(CREATE_VEHICLE_QUERY);

			ps.setString(1, vehicle.getConstructeur());

			ps.setInt(2, vehicle.getNb_places());
			long id = ps.executeUpdate();
			ps.close();
			connection.close();
			return id;
		} catch (SQLException e) {

			throw new DaoException();

		}
	}

	public long delete(long id) throws DaoException {
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_VEHICLE_QUERY);
		) {
			Iterator<Reservation> reservations = reservationService.findByVehicleId(id).iterator();
			while(reservations.hasNext()) reservationService.delete((int) reservations.next().getId());
			preparedStatement.setLong(1, id);
			return preparedStatement.executeUpdate();
		} catch (SQLException | ServiceException e) {
			throw new DaoException(e.getMessage());
		}

	}

	public Vehicle findById(long id) throws DaoException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_VEHICLE_QUERY);
			ps.setLong(1,id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				String constructeur = rs.getString("constructeur");
				int nbPlaces = rs.getInt("nb_places");

				return new Vehicle(id,constructeur,nbPlaces);
			}
			ps.execute();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Vehicle> findAll() throws DaoException {
		List<Vehicle>vehicles = new ArrayList<Vehicle>();
		try{
			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(FIND_VEHICLES_QUERY);

			while (rs.next()){
				Long id = rs.getLong("id");
				String constructeur = rs.getString("constructeur");
				int nb_places = rs.getInt("nb_places");

				vehicles.add(new Vehicle(id,constructeur, nb_places));
			}
		}
		catch (SQLException e){
			e.printStackTrace();
			throw new DaoException();
		}
		return vehicles;
		
	}
	

}
