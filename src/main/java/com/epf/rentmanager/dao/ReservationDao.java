package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.modeles.Client;
import com.epf.rentmanager.modeles.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.modeles.Reservation;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDao {

/*	private static ReservationDao instance = null;
	private ReservationDao() {}
	public static ReservationDao getInstance() {
		if(instance == null) {
			instance = new ReservationDao();
		}
		return instance;
	}*/
	private ReservationDao() {}
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
		
	public long create(Reservation reservation) throws DaoException {

		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(CREATE_RESERVATION_QUERY);

			ps.setLong(1, reservation.getClient_id());
			ps.setLong(2, reservation.getVehicle_id());
			ps.setDate(3, Date.valueOf(reservation.getDebut()));
			ps.setDate(4, Date.valueOf(reservation.getFin()));
			long id = ps.executeUpdate();
			ps.close();
			connection.close();
			return id;
		} catch (SQLException e) {

			throw new DaoException();

		}
	}
	
	public long delete(long id) throws DaoException {
		/*try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(DELETE_RESERVATION_QUERY);
		) {
			ps.setLong(1, reservation.getId());
			return ps.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException();
		}*/
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_RESERVATION_QUERY);
		) {
			preparedStatement.setLong(1, id);
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}

	}

	
	public List<Reservation> findByClientId(long client_id) throws DaoException {

		List<Reservation> reservations = new ArrayList<Reservation>();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);
			ps.setLong(1, client_id);
			ResultSet rs = ps.executeQuery();

			if(rs.next()){

				long id = rs.getLong("id");
				long vehicle_id = rs.getLong("vehicle_id");
				LocalDate debut = rs.getDate("debut").toLocalDate();
				LocalDate fin = rs.getDate("fin").toLocalDate();

				//connection.close();
				reservations.add(new Reservation(id,client_id,vehicle_id,debut,fin));

			}

			ps.close();
			connection.close();
		} catch (SQLException e) {
			throw new DaoException();
		}
		return reservations;
	}
	
	public List<Reservation> findByVehicleId(long vehicle_id) throws DaoException {
		List<Reservation> reservations = new ArrayList<Reservation>();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);
			ps.setLong(1, vehicle_id);
			ResultSet rs = ps.executeQuery();

			if(rs.next()){

				long id = rs.getLong("id");
				long client_id = rs.getLong("client_id");
				LocalDate debut = rs.getDate("debut").toLocalDate();
				LocalDate fin = rs.getDate("fin").toLocalDate();

				//connection.close();
				reservations.add(new Reservation(id,client_id,vehicle_id,debut,fin));

			}

			ps.close();
			connection.close();
		} catch (SQLException e) {

			throw new DaoException();
		}
		return reservations;
	}

	public List<Reservation> findAll() throws DaoException {
		List<Reservation>reservations = new ArrayList<Reservation>();
		try {
			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(FIND_RESERVATIONS_QUERY);

			while (rs.next()){

				long id = rs.getLong("id");
				long client_id = rs.getLong("client_id");
				long vehicle_id = rs.getLong("vehicle_id");
				LocalDate debut = rs.getDate("debut").toLocalDate();
				LocalDate fin = rs.getDate("fin").toLocalDate();


				reservations.add(new Reservation(id,client_id,vehicle_id,debut,fin));

			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
		return reservations;
	}

}
