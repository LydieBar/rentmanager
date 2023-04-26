package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.modeles.Client;
import com.epf.rentmanager.modeles.Reservation;
import com.epf.rentmanager.modeles.Vehicle;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.modeles.Vehicle;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/reservationCreate")
public class ReservationCreateServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private ClientService clientService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {

        try {
            request.setAttribute("users", clientService.findAll());
            request.setAttribute("vehicles", vehicleService.findAll());

        } catch (ServiceException e) {
            e.printStackTrace();
        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);

    }
    @Override
    protected void doPost(HttpServletRequest   request,   HttpServletResponse response) throws ServletException, IOException {
        // traitement du formulaire (appel à la méthode de sauvegarde)}

/*        Reservation reservation = new Reservation(
                Long.parseLong(request.getParameter("vehicles").toString()),
                Long.parseLong(request.getParameter("clients").toString()),
                LocalDate.parse((request.getParameter("begin") ), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                LocalDate.parse((request.getParameter("end") ), DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        );*/

        try {
            //reservationService.create(reservation);

            Long client =Long.parseLong(request.getParameter("client").toString());
            Long voiture =Long.parseLong(request.getParameter("car").toString());
            LocalDate debut = LocalDate.parse((request.getParameter("begin") ), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalDate fin = LocalDate.parse((request.getParameter("end") ), DateTimeFormatter.ofPattern("dd/MM/yyyy"));


            reservationService.create(new Reservation(client,voiture,debut,fin));

        }
        catch (ServiceException e) {
            throw new RuntimeException(e);
        }

        response.sendRedirect("/rentmanager/home");


    }

}
