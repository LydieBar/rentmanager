package com.epf.rentmanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.modeles.Client;
import com.epf.rentmanager.service.ClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet("/clientDelete")
public class ClientDeleteServlet extends HttpServlet {
    @Autowired
    private ClientService clientService;


    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        //Client client = new Client(Long.parseLong(request.getParameter("id").toString()));
        try {
            clientService.delete(Long.parseLong(request.getParameter("id").toString()));
            request.getServletContext().getRequestDispatcher("/WEB-INF/views/users/list.jsp").forward(request, response);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        //response.sendRedirect("/rentmanager/users/list.jsp");
    }

}
