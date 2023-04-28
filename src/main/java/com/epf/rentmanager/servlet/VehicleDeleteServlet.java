package com.epf.rentmanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.exception.ServiceException;

import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet("/vehicleDelete")
public class VehicleDeleteServlet extends HttpServlet {
    @Autowired
    private VehicleService vehicleService;


    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        try {
            vehicleService.delete(Long.parseLong(request.getParameter("id").toString()));
            request.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/list.jsp").forward(request, response);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

    }

}
