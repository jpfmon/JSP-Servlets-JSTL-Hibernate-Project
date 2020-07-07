package com.montojo.carmanag.controllers;

import com.montojo.carmanag.model.Car;
import com.montojo.carmanag.model.DatabaseUtil;
import com.montojo.carmanag.model.Owner;
import com.montojo.carmanag.model.Services;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private DatabaseUtil databaseUtil;

    @Override
    public void init() throws ServletException {
        System.out.println("Init DashboardServlet");
        super.init();
        databaseUtil = new DatabaseUtil();

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        gettingSession(req, resp);
        List<Car> carsList;
        List<Owner> ownersList;
        List<Services> servicesList;
        /*
          checking if client is logged in, else forward again to log in
         */
        if (checkLog(req)) {

            carsList = listCars();

            ownersList = listOwners();

            servicesList = listServices();

            String user = (String) req.getSession().getAttribute("userName");
            System.out.println(user);
            Cookie cookieUser = new Cookie("userName", user);
            resp.addCookie(cookieUser);

            req.setAttribute("carslist", carsList);
            req.setAttribute("ownerslist", ownersList);
            req.setAttribute("serviceslist", servicesList);

            RequestDispatcher dispatcher = req.getRequestDispatcher("/dashboard.jsp");
            dispatcher.forward(req, resp);
        } else {
            req.setAttribute("error", "Please, log in to get access");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/index.jsp");
            dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private List<Owner> listOwners() {

        return databaseUtil.getOwners();
    }

    private List<Car> listCars() {

        return databaseUtil.getCars();
    }

    private List<Services> listServices() {

        return databaseUtil.getServices();
    }

    /**
     * method to check whether client is logged in or not
     */
    private boolean checkLog(HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.setAttribute("databaseutil", databaseUtil);
        Boolean loggedIn = (Boolean) session.getAttribute("login");

        if (loggedIn == null) {
            return false;
        }
        return loggedIn;
    }

    private void gettingSession(HttpServletRequest req, HttpServletResponse res) {
        HttpSession session = req.getSession();
        System.out.println("This is Session: " + session.getId());
    }


}
