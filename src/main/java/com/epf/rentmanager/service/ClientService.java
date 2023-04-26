package com.epf.rentmanager.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.modeles.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@Service
public class ClientService {

	public static int minLength = 3;
	private ClientDao clientDao;


	public static ClientService instance;

	private ClientService(ClientDao clientDao){this.clientDao = clientDao;}
	
/*	private ClientService() {
		this.clientDao = ClientDao.getInstance();
	}
	
	public static ClientService getInstance() {
		if (instance == null) {
			instance = new ClientService();
		}
		
		return instance;
	}*/
	
	
	public long create(Client client) throws ServiceException {
		// TODO: créer un client

		try {
			confirmation(client);
			return clientDao.create(client);
		} catch ( DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public long delete(long id) throws ServiceException {
		// TODO: créer un client

		try {
			return clientDao.delete(id);
		} catch ( DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public Client findById(long id) throws ServiceException {
		// TODO: récupérer un client par son id

		try{
			return clientDao.findById(id).get();

		} catch (DaoException e) {
			throw new RuntimeException(e);
		}

	}

	public List<Client> findAll() throws ServiceException {
		// TODO: récupérer tous les clients
		try{
			return clientDao.findAll();
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}
	}

	private void confirmation(Client client) throws ServiceException {
		if (vide(client.getNom()) || vide(client.getPrenom()))
			throw new ServiceException("Le nom ou le prénom n'a pas été renseigné");
		if (!confirmationEmail(client))
			throw new ServiceException("Email non valide");
		if (!majeur(client))
			throw new ServiceException("L'utilisateur doit avoir au moins 18 ans");
		if (!confirmationNom(client))
			throw new ServiceException("Le nom et le prénom de l'utilsateur doivent contenir au moins 3 caractères");
	}

	private boolean majeur(Client client) {
		return client.getNaissance().until(LocalDate.now(), ChronoUnit.YEARS) >= 18;
	}

	private boolean confirmationNom(Client client) {
		return client.getNom().length() >= minLength && client.getPrenom().length() >= minLength;
	}

	public boolean adresseValide(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}
	private boolean confirmationEmail(Client client) {
		final boolean formatValide = adresseValide(client.getEmail());
		if (!formatValide) return false;
		// Peut être davantage optimisé
		try {
			List<Client> clients = this.findAll();
			for (Client iclient : clients) {
				if (iclient.getEmail().equals(client.getEmail())) return false;
			}
			return true;
		} catch (ServiceException e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean vide(String input) {
		return input == null || input.trim().length() == 0;
	}

	private boolean getAge(Client client) {
		return client.getNaissance().until(LocalDate.now(), ChronoUnit.YEARS) >= 18;
	}

}
