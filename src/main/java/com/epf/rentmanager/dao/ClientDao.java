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
import java.util.Optional;

import com.epf.rentmanager.modeles.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.modeles.Client;
import org.springframework.stereotype.Repository;
@Repository
public class ClientDao {

	private ClientDao() {}
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";


	public long create(Client client)throws DaoException {

		try {

			Connection connection = ConnectionManager.getConnection();

			PreparedStatement ps = connection.prepareStatement(CREATE_CLIENT_QUERY);
			ps.setString(1, client.getNom());

			ps.setString(2, client.getPrenom());


			ps.setString(3, client.getEmail());

			ps.setDate(4, Date.valueOf(client.getNaissance()));

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
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CLIENT_QUERY);
		) {
			preparedStatement.setLong(1, id);
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}

	}

	public Optional<Client> findById(long id) throws DaoException {
	try{
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement ps = connection.prepareStatement(FIND_CLIENT_QUERY);
		ps.setLong(1,id);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			String nom = rs.getString("nom");
			String prenom = rs.getString("prenom");
			String email = rs.getString("email");
			LocalDate naissance = rs.getDate("naissance").toLocalDate();



			//connection.close();
			return Optional.ofNullable(new Client(id,nom,prenom,email,naissance));
		}
		ps.execute();
		ps.close();

	} catch (SQLException e) {
		e.printStackTrace();
		//throw new DaoException();
	}
	return null;
	}

	public List<Client> findAll() throws DaoException {
		List<Client>clients = new ArrayList<Client>();
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_CLIENTS_QUERY);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Long id =rs.getLong("id");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String email = rs.getString("email");
				LocalDate naissance = rs.getDate("naissance").toLocalDate();
				clients.add(new Client(id,nom,prenom,email,naissance));
			}

			ps.execute();
			ps.close();


		} catch (SQLException e) {
			e.printStackTrace();
		}

		return clients;
	}

}
