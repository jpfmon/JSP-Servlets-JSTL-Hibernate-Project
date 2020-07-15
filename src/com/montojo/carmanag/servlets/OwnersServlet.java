package com.montojo.carmanag.servlets;

import com.montojo.carmanag.model.Car;
import com.montojo.carmanag.model.DatabaseUtil;
import com.montojo.carmanag.model.Owner;
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


@WebServlet("/owners")
public class OwnersServlet extends HttpServlet {

    private DatabaseUtil databaseUtil;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (!checkLog(req)) {
            req.setAttribute("error", "Please, log in to get access");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/index.jsp");
            dispatcher.forward(req, resp);

        } else {
            String ownersaction = req.getParameter("ownersaction");

            if (ownersaction != null) {
                switch (ownersaction) {
                    case "newowner":
                        System.out.println("Send to new owner form");
                        RequestDispatcher dispatcherNewOwner = req.getRequestDispatcher("/newownerform.jsp");
                        dispatcherNewOwner.forward(req, resp);
                        break;
                    case "addthis":
                        String newOwnerFullName = req.getParameter("fullName");
                        int idCard = 0;
                        int phone = 0;
                        try {
                            idCard = Integer.parseInt(req.getParameter("idCard"));
                            phone = Integer.parseInt(req.getParameter("phoneNumber"));
                        } catch (Exception e) {
                            System.out.println("Exception parsing in save page");
                            resp.sendRedirect("/owners?error=noSave");
                            break;
                        }
                        String email = req.getParameter("email");

                        System.out.println("Name from form: " + newOwnerFullName);
                        System.out.println("IdCard from form: " + idCard);
                        System.out.println("Phone from form: " + phone);
                        System.out.println("Email from form: " + email);
                        Owner newOwner = new Owner(newOwnerFullName, idCard, phone, email);
                        saveNewOwner(newOwner);
                        resp.sendRedirect("/owners");
                        break;
                    case "viewOwner":
                        int ownerId = Integer.parseInt(req.getParameter("ownerId"));
                        System.out.println("Id of owner to view: " + ownerId);
                        Owner viewOwner = retrieveOwner(ownerId);
                        ArrayList<Car> carsList = retrieveOwnerCars(ownerId);
//                        if fetch type is eager for services property in Car class, no need to retrieve Services as a separate list.
//                        I'm doing it here as fetch type lazy.
                        ArrayList<Services> servicesList = retrieveOwnerServices(ownerId);

                        req.setAttribute("viewOwner", viewOwner);
                        req.setAttribute("carsList", carsList);
                        req.setAttribute("servicesList", servicesList);

                        RequestDispatcher dispatcherViewOwner = req.getRequestDispatcher("/viewowner.jsp");
                        dispatcherViewOwner.forward(req, resp);
                        break;
                    case "updateThis":
                        System.out.println("Request to update this owner");
                        int updatedOwnerId = Integer.parseInt(req.getParameter("ownerId"));
                        String updatedOwnerFullName = req.getParameter("fullName");
                        int updatedIdCard = 0;
                        int updatedPhone = 0;
                        try {
                            updatedIdCard = Integer.parseInt(req.getParameter("idCard"));
                            updatedPhone = Integer.parseInt(req.getParameter("phoneNumber"));
                        } catch (Exception e) {
                            System.out.println("Beautiful exception parsing in update page");
                            resp.sendRedirect("/owners?error=noUpdate");
                            break;
                        }
                        String updatedEmail = req.getParameter("email");

                        updateOwner(updatedOwnerId,updatedOwnerFullName,updatedIdCard,updatedPhone,updatedEmail);
                        resp.sendRedirect("/owners");
                        break;
                    case "deleteThis":
                        System.out.println("Request to delete this owner: " + req.getParameter("ownerId"));
                        int deleteOwnerId = Integer.parseInt(req.getParameter("ownerId"));
                        deleteOwner(deleteOwnerId);
                        resp.sendRedirect("/owners");
                        break;
                    default:
                        showMainContent(req, resp);
                }
            } else {
                showMainContent(req, resp);
            }
        }
    }

    private ArrayList<Services> retrieveOwnerServices(int ownerId) {
        ArrayList<Services> servicesList;
        servicesList = databaseUtil.retrieveOwnerServices(ownerId);
        return servicesList;
    }

    private ArrayList<Car> retrieveOwnerCars(int ownerId) {
        ArrayList<Car> carsList;
        carsList = databaseUtil.retrieveOwnerCars(ownerId);
        return carsList;
    }

    private void deleteOwner(int deleteOwnerId) {
        databaseUtil.deleteOwner(deleteOwnerId);
    }

    private void updateOwner(int id,String fullName,int idCardNumber, int phone, String email) {

        databaseUtil.updateOwner(id,fullName,idCardNumber,phone,email);
    }

    private Owner retrieveOwner(int ownerId) {
        Owner retrievedOwner = databaseUtil.getOwner(ownerId);
        return retrievedOwner;
    }

    private void saveNewOwner(Owner newOwner) {
        databaseUtil.saveNewOwner(newOwner);
    }

    private void showMainContent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameter("error") != null) {
            String message = req.getParameter("error") + " due to wrong data type";
            req.setAttribute("error", message);
        }
        List<Owner> ownersList;
        ownersList = listOwners();
        req.setAttribute("ownerslist", ownersList);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/owners.jsp");
        dispatcher.forward(req, resp);

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

    private List<Owner> listOwners() {
        return databaseUtil.getOwners();
    }


}
