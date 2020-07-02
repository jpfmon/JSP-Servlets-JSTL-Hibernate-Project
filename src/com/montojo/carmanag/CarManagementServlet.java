package com.montojo.carmanag;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

@WebServlet("/holahola")
public class CarManagementServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Aqui andamos");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //using Statement (.createStatement() )
        System.out.println("Now, using Statement");
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        out.println("<h1>DESDE LA BASE DE DATOS</h1><br><hr>");
        ArrayList list = new ArrayList();
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/car_manag", "root", "root");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from car");
            System.out.println("Success retrieving cars!!");
            while (rs.next()) {
                System.out.println("Car id is: " + rs.getInt("id"));
                System.out.println("Car owner is: " + rs.getString("owner_id"));
                System.out.println("Car model is: " + rs.getString("model"));
                out.println("<br>Car id is: " + rs.getInt("id"));
                out.println("<br>Car owner is: " + rs.getString("owner_id"));
                out.println("<br>Car model is: " + rs.getString("model"));
                list.add(rs.getInt("id"));
                list.add(rs.getString("owner_id"));
                list.add(rs.getString("model"));

                req.setAttribute("list",list);

            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        out.println("</body></html>");

        RequestDispatcher dispatcher = req.getRequestDispatcher("/show_results.jsp");
        dispatcher.forward(req,resp);
    }
}
