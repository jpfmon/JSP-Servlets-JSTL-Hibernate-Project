package com.montojo.carmanag.controllers;

import com.montojo.carmanag.model.Car;
import com.montojo.carmanag.model.DatabaseUtil;
import com.montojo.carmanag.model.Owner;
import com.montojo.carmanag.model.Services;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

//    @Resource(name = "jdbc/car_manag")
//    private DataSource dataSource;

    private SessionFactory sessionFactory;

    private DatabaseUtil databaseUtil;

    @Override
    public void init() throws ServletException {
        System.out.println("Init DashboardServlet");
        super.init();
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Car.class)
                .addAnnotatedClass(Owner.class)
                .addAnnotatedClass(Services.class)
                .buildSessionFactory();
        try {
            databaseUtil = new DatabaseUtil(sessionFactory);
        } catch (Exception e) {
            throw new ServletException(e);
        }
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
            /*
              retrieve cars from DB
             */
            carsList = listCars();
            /*
              retrieve owners from DB
             */
            ownersList = listOwners();
            /*
              retrieve services from DB
             */
            servicesList = listServices();

            String user= (String) req.getSession().getAttribute("userName");
            System.out.println(user);
            Cookie cookieUser = new Cookie("userName",user);
            resp.addCookie(cookieUser);

//            passing data to model, for jsp´s pages
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
