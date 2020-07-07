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
import java.util.List;


@WebServlet("/upload")
@MultipartConfig
public class Upload extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String description = request.getParameter("description"); // Retrieves <input type="text" name="description">
        Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
        InputStream fileContent = filePart.getInputStream();
        // ... (do your job here)
        System.out.println(filePart.toString());
        System.out.println(fileName);
        response.sendRedirect("/dashboard");

        InputStreamReader inputStreamReader = new InputStreamReader(fileContent);

        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        while (bufferedReader.ready()){
            System.out.println(bufferedReader.readLine());
        }
    }
}
