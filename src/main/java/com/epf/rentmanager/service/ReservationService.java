package com.epf.rentmanager.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.modeles.Reservation;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    public static int duree = 30;
    private ReservationDao reservationDao;


    private ReservationService(ReservationDao reservationDao){this.reservationDao = reservationDao;}


    public long create(Reservation reservation) throws ServiceException {
        // TODO: créer un client

        try {
            confirmation(reservation);
            return reservationDao.create(reservation);
        } catch ( DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }


    public long delete(long id) throws ServiceException {
        try {
            return reservationDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public List<Reservation> findByClientId(long client_id) throws ServiceException {
        // TODO: récupérer un client par son id

        try{
            return reservationDao.findByClientId(client_id);

        } catch (DaoException e) {
            throw new RuntimeException(e);
        }

    }

        public List<Reservation> findByVehicleId(long vehicle_id) throws ServiceException {
        // TODO: récupérer un client par son id

        try{
            return reservationDao.findByVehicleId(vehicle_id);

        } catch (DaoException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Reservation> findAll() throws ServiceException {
        // TODO: récupérer tous les clients
        try{
            return reservationDao.findAll();
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }


    private void confirmation(Reservation reservation) throws ServiceException {
        if(!reserve(reservation))
            throw new ServiceException("On ne peut pas réserver une même voiture deux fois le même jour");
        if(!reserveSemaine(reservation))
            throw new ServiceException("On ne peut pas réserver une même voiture 7 jours de suite par le même utilisateur");
        if (!reserveMois(reservation.getVehicle_id(), duree - reservation.getDebut().until(reservation.getFin(), ChronoUnit.DAYS)))
            throw new ServiceException("On ne peut pas réserver une même voiture 30 jours de suite.");
    }

    private boolean reserveSemaine(Reservation reservation) {
        return reservation.getDebut().until(reservation.getFin(), ChronoUnit.DAYS) < 7;
    }

    private boolean reserve(Reservation reservation) {
        List<Reservation> reservations;
        try {
            reservations = this.findByVehicleId(reservation.getVehicle_id());
            for (Reservation ireservation : reservations) {
                if (ireservation.getFin().until(reservation.getFin(), ChronoUnit.DAYS) > 0 && reservation.getDebut().until(ireservation.getFin(), ChronoUnit.DAYS) > 0 ||
                        reservation.getFin().until(ireservation.getFin(), ChronoUnit.DAYS) > 0 && ireservation.getDebut().until(reservation.getFin(), ChronoUnit.DAYS) > 0) return false;
            }
            return true;
        } catch (ServiceException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean reserveMois(long vehicle_id, long max) {
        try {
            List<Reservation> reservations = reservationDao.findByVehicleId(vehicle_id);
            reservations.sort((r1, r2) -> r2.getDebut().compareTo(r1.getDebut()));
            LocalDate now = LocalDate.now();
            LocalDate prevDate = now;
            for (Reservation r : reservations) {
                if (r.getDebut().until(now, ChronoUnit.DAYS) >= max) return false;
                if (r.getFin().until(prevDate, ChronoUnit.DAYS) > 1) return true;
                prevDate = r.getDebut();
            }
            return true;
        } catch (DaoException e) {
            e.printStackTrace();
            return false;
        }
    }

}
