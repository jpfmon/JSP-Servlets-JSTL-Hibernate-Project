package com.montojo.carmanag.controllers;

import com.montojo.carmanag.model.Car;
import com.montojo.carmanag.model.DatabaseUtil;
import com.montojo.carmanag.model.Services;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/services")
public class ServicesServlet extends HttpServlet {

    private DatabaseUtil databaseUtil;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (!checkLog(req)) {
            req.setAttribute("error", "Please, log in to get access");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/index.jsp");
            dispatcher.forward(req, resp);

        } else {
            String serviceaction = req.getParameter("serviceaction");

            if (serviceaction != null) {
                switch (serviceaction) {
                    case "viewService":
//                        int serviceId = Integer.parseInt(req.getParameter("serviceId"));
//                        System.out.println("Id of service to view: " + serviceId);
//                        Services viewedService = retrieveService(serviceId);
//
//                        req.setAttribute("viewedService", viewedService);
//                        RequestDispatcher dispatcherViewOwner = req.getRequestDispatcher("/viewservice.jsp");
//                        dispatcherViewOwner.forward(req, resp);
                        break;
                    case "newservice":
//                        System.out.println("Send to new service form");
//
//                        List<Car> carsList;
//                        carsList = databaseUtil.getCars();
//                        req.setAttribute("carsList", carsList);
//
//                        RequestDispatcher dispatcherNewOwner = req.getRequestDispatcher("/newserviceform.jsp");
//                        dispatcherNewOwner.forward(req, resp);
                        break;
                    case "addThis":
//                        System.out.println("Adding this new service");
//                        Car carId = null;
//                        String name;
//                        String date;
//                        String notes;
//                        Float price;
//                        try {
//                            carId = Integer.parseInt(req.getParameter("carId"));
//                            System.out.println("parsing carId correct");
//                            price = Float.parseFloat(req.getParameter("price"));
//                            System.out.println("parsing price correct");
//                        } catch (Exception e) {
//                            System.out.println("Exception parsing in save page of new service");
//                            resp.sendRedirect("/services?error=noSave");
//                            break;
//                        }
//                        name = req.getParameter("name");
//                        date = req.getParameter("date");
//                        notes = req.getParameter("notes");
//
//                        Services newService = new Services(name, carId, date, notes, price);
//                        System.out.println(newService.toString());
//                        saveNewService(newService);
//                        resp.sendRedirect("/services");
                        break;
                    case "deleteThis":
//                        int deleteServiceId = Integer.parseInt(req.getParameter("serviceId"));
//                        System.out.println("You want to delete service " + deleteServiceId);
//                        deleteService(deleteServiceId);
//                        resp.sendRedirect("/services");
                        break;
                    case "updateThis":
                        /**
                         * Logic to update the record
                         */
                        break;
                    default:
                        showMainContent(req, resp);
                }
            } else {
                showMainContent(req, resp);
            }
        }
    }

    private void deleteService(int deleteServiceId) {
        databaseUtil.deleteService(deleteServiceId);
    }

    private void saveNewService(Services newService) {
        databaseUtil.saveNewService(newService);
    }

    private Services retrieveService(int serviceId) {
        return databaseUtil.getService(serviceId);
    }

    private ArrayList<Services> retrieveOwnerServices(int ownerId) {
        ArrayList<Services> servicesList = new ArrayList();
        servicesList = databaseUtil.retrieveOwnerServices(ownerId);
        return servicesList;
    }

    private ArrayList<Car> retrieveOwnerCars(int ownerId) {
        ArrayList<Car> carsList = new ArrayList();
        carsList = databaseUtil.retrieveOwnerCars(ownerId);
        return carsList;
    }


    private void showMainContent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameter("error") != null) {
            String message = req.getParameter("error") + " due to wrong data type";
            req.setAttribute("error", message);
        }
        List<Services> servicesList;
        servicesList = listServices(); //retrieve services from database
        req.setAttribute("serviceslist", servicesList);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/services.jsp");
        dispatcher.forward(req, resp);

    }

    private List<Services> listServices() {
        return databaseUtil.getServices();
    }

    private boolean checkLog(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Boolean loggedIn = (Boolean) session.getAttribute("login");
        databaseUtil = (DatabaseUtil) session.getAttribute("databaseutil");
        if (databaseUtil == null || loggedIn == null || !loggedIn) {
            return false;
        }
        return true;
    }
}
