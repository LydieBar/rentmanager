package com.epf.rentmanager;

import com.epf.rentmanager.Configuration.AppConfiguration;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.modeles.Client;
import com.epf.rentmanager.modeles.Reservation;
import com.epf.rentmanager.modeles.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class main {
    public static void main(String[] args) throws DaoException {

        ApplicationContext context=new AnnotationConfigApplicationContext(AppConfiguration.class);
        ClientService clientService = context.getBean(ClientService.class);
        VehicleService vehicleService = context.getBean(VehicleService.class);


       // Client client1 = new Client(1,null,null,null,null);
try{
    System.out.println(clientService.findAll());
    //Client client1 = new Client(1,null,null,null,null);

}
 catch (ServiceException e) {
    throw new RuntimeException(e);
}
    }
}
