package com.montojo.carmanag.servlets;

import com.montojo.carmanag.model.Car;
import com.montojo.carmanag.model.DatabaseUtil;
import com.montojo.carmanag.model.Owner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


@WebServlet("/cars")
public class CarsServlet extends HttpServlet {

    private DatabaseUtil databaseUtil;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("In CarsServlet");

        if (!checkLog(req)) {
            req.setAttribute("error", "Please, log in to get access");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/index.jsp");
            dispatcher.forward(req, resp);

        } else {
            String carsaction = req.getParameter("carsaction");

            if (carsaction != null) {
                switch (carsaction) {
                    case "newcar":
                        List<Owner> ownerList;
                        ownerList = databaseUtil.getOwners();
                        req.setAttribute("ownersList", ownerList);
                        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/newcarform.jsp");
                        requestDispatcher.forward(req, resp);
                        break;
                    case "addthis":
                        System.out.println("Adding this new car");
                        int ownerId = 0;
                        String brand;
                        String model;
                        try {
                            ownerId = Integer.parseInt(req.getParameter("ownerId"));
                        } catch (Exception e) {
                            System.out.println("Beautiful exception parsing in save page of new car");
                            resp.sendRedirect("/cars?error=noSave");
                            break;
                        }
                        brand = req.getParameter("brand");
                        model = req.getParameter("model");

                        System.out.println("New car. Owner id: " + ownerId + " | Brand: " + brand + " | Model: " + model);

                        saveNewCar(ownerId,brand,model);
                        resp.sendRedirect("/cars");
                        break;
                    case "viewCar":
                        int carId = Integer.parseInt(req.getParameter("carId"));
                        System.out.println("Id of car to view: " + carId);
                        Car viewedCar = retrieveCar(carId);

                        req.setAttribute("viewedCar", viewedCar);
                        RequestDispatcher dispatcherViewOwner = req.getRequestDispatcher("/viewcar.jsp");
                        dispatcherViewOwner.forward(req, resp);
                        break;
                    case "deleteThis":
                        int deleteCarId = Integer.parseInt(req.getParameter("carId"));
                        System.out.println("You want to delete car " + deleteCarId);
                        deleteCar(deleteCarId);
                        resp.sendRedirect("/cars");
                        break;
                    case "updateThis":
                        /**
                         * Logic to update the record
                         */
                        System.out.println("Request to update this car");
                        int updatedCarId = 0;
                        int updatedOwnerId = 0;
                        String updateCarBrand = req.getParameter("brand");
                        String updateCarModel = req.getParameter("model");
                        try {
                            updatedCarId = Integer.parseInt(req.getParameter("carId"));
                            updatedOwnerId = Integer.parseInt(req.getParameter("ownerId"));
                        }catch (Exception e){
                            System.out.println("Exception parsing in car update page");
                            resp.sendRedirect("/owners?error=noUpdate");
                            break;
                        }


                        updateCar(updatedCarId,updatedOwnerId,updateCarBrand,updateCarModel);
                        resp.sendRedirect("/cars");
                        break;
                    default:
                        showMainContent(req, resp);
                }
            } else {
                showMainContent(req, resp);
            }
        }
    }

    private void updateCar(int updatedCarId,int updatedCarOwner, String brand, String model) {
        databaseUtil.updateCar(updatedCarId,updatedCarOwner,brand,model);
    }

    private void deleteCar(int deleteCarId) {
        databaseUtil.deleteCar(deleteCarId);
    }

    private Car retrieveCar(int carId) {
        return databaseUtil.getCar(carId);
    }

    private void saveNewCar(int ownerId,String brand,String model) {
        databaseUtil.saveNewCar(ownerId,brand,model);
    }

    private void showMainContent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameter("error") != null) {
            String message = req.getParameter("error") + " due to wrong data type";
            req.setAttribute("error", message);
        }
        List<Car> carsList;
        carsList = listCars(); //retrieve owners from database
        req.setAttribute("carslist", carsList);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/cars.jsp");
        dispatcher.forward(req, resp);

    }

    private boolean checkLog(HttpServletRequest req) {

        HttpSession session = req.getSession();
        Boolean loggedIn = (Boolean) session.getAttribute("login");
        databaseUtil = (DatabaseUtil) session.getAttribute("databaseutil");
        if (databaseUtil == null || loggedIn == null || !loggedIn) {
            return false;
        }
//        System.out.println("LoggedIn from session y en method checkLog: " + loggedIn);
        return true;
    }

    private List<Car> listCars() {
        return databaseUtil.getCars();
    }


}
