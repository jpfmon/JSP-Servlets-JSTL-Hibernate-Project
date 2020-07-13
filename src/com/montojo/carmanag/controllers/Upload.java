package com.montojo.carmanag.controllers;

import com.montojo.carmanag.model.Car;
import com.montojo.carmanag.model.DatabaseUtil;
import com.montojo.carmanag.model.Owner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/upload")
@MultipartConfig
public class Upload extends HttpServlet {

    DatabaseUtil databaseUtil;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Se vino por aqui.....");
        req.setAttribute("error", "Please, log in to get access");
        RequestDispatcher dispatcher = req.getRequestDispatcher("/index.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String selection = request.getParameter("import");
        System.out.println("This is the selection of radio buttons: " + selection);
        System.out.println("Estamos aqui");
        if (checkLog(request) && selection !=null) {
            String description = request.getParameter("description"); // Retrieves <input type="text" name="description">
            Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
            InputStream fileContent = filePart.getInputStream();

            System.out.println(filePart.toString());
            System.out.println(fileName);

            InputStreamReader inputStreamReader = new InputStreamReader(fileContent);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            List<String> content = new ArrayList();

            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine();
                content.add(line);
            }

            switch (selection) {
                case "owners":
                    importOwners(content);
                    break;
                case "cars":
                    importCars(content);
                    break;
                case "services":
                    importServices(content);
                    break;
                default:
            }

            response.sendRedirect("/dashboard");
        } else {
            request.setAttribute("error", "Please, log in to get access");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void importOwners(List<String> content) {

        for (int i = 1; i < content.size(); i++) {
            String fullName;
            String idCardNumber;
            String phone;
            String email;
            System.out.println("Importing owner from this line: " + content.get(i));

            String[] lineArray = content.get(i).split(";");
            int lineArrayLength = lineArray.length;

            System.out.println("Length: " + lineArrayLength);
            switch (lineArrayLength) {
                case 1:
                    fullName = lineArray[0];
                    break;
                case 2:
                    fullName = lineArray[0];
                    idCardNumber = lineArray[1];
                    break;
                case 3:
                    fullName = lineArray[0];
                    idCardNumber = lineArray[1];
                    phone = lineArray[2];
                    break;
                case 4:
                    fullName = lineArray[0];
                    idCardNumber = lineArray[1];
                    phone = lineArray[2];
                    email = lineArray[3];

                    System.out.println("Data readed from file: " + fullName + " | " + idCardNumber + " | " + phone + " | " + email);
                    Owner newOwner;
                    try {
                        newOwner = new Owner(fullName, Integer.parseInt(idCardNumber), Integer.parseInt(phone), email);
                        if (!checkExistingOwner(newOwner)) {
                            System.out.println("Saving this new owner: " + newOwner.toString());
                            databaseUtil.saveNewOwner(newOwner);
                        } else {
                            System.out.println("Owner already exists!!!");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
            }
        }
    }

    private boolean checkExistingOwner(Owner newOwner) {
        System.out.println("Checking if owner with this ID exists: " + newOwner.getIdCardNumber());
        List<Owner> existingOwners = databaseUtil.getOwners();
        boolean exists = false;
        for (Owner o : existingOwners) {
            if (o.getIdCardNumber() == newOwner.getIdCardNumber()) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    private void importCars(List<String> content) {

        for (int i = 1; i < content.size(); i++) {

            String ownerId;
            String brand;
            String model;
            System.out.println("Importing car from this line: " + content.get(i));

            String[] lineArray = content.get(i).split(";");
            int lineArrayLength = lineArray.length;

            System.out.println("Length: " + lineArrayLength);
            switch (lineArrayLength) {
                case 3:
                    ownerId = lineArray[0];
                    brand = lineArray[1];
                    model = lineArray[2];

                    System.out.println("Data read from file: " + ownerId + " | " + brand + " | " + model);
                    Car newCar;
                    Owner owner = null;
                    try {
                        owner = databaseUtil.getOwner(Integer.parseInt(ownerId));
                    } catch (Exception e) {
                        System.out.println("Owner doesn't exist!!! (first warning)");
//                        e.printStackTrace();
                    }
                    if (owner == null) {
                        System.out.println("Owner doesn't exist!!! (second warning)");
                        break;
                    } else {
                        newCar = new Car(owner, brand, model);

                        if (!checkExistingCar(newCar)) {
                            System.out.println("Saving new Car");
                            databaseUtil.saveNewCar(Integer.parseInt(ownerId), brand, model);
                        } else {
                            System.out.println("Car already exists!!!");
                        }
                    }
                    break;
                default:
            }
        }
    }

    private boolean checkExistingCar(Car newCar) {
        List<Car> cars = databaseUtil.getCars();
        boolean carExists = false;
        for (Car c : cars) {
            if (c.getModel().toLowerCase().equals(newCar.getModel().toLowerCase()) && c.getBrand().toLowerCase().equals(newCar.getBrand().toLowerCase())) {
                carExists = true;
            }
        }
        return carExists;
    }


    private void importServices(List<String> content) {
    }

    private boolean checkLog(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Boolean loggedIn = (Boolean) session.getAttribute("login");
        databaseUtil = (DatabaseUtil) session.getAttribute("databaseutil");
        if (databaseUtil == null || loggedIn == null || !loggedIn) {
            return false;
        }
        System.out.println("LoggedIn from session y en method checkLog: " + loggedIn);
        return true;
    }
}
