package com.montojo.carmanag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class ConnDriver {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, InterruptedException, IOException {
        //jdbc:mysql://localhost:3306/?user=root
        Class.forName("com.mysql.cj.jdbc.Driver");

        //using Statement (.createStatement() )
        System.out.println("Now, using Statement");
        Thread.sleep(1000);

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/car_manag", "root", "root");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from car");
            System.out.println("Success retrieving cars!!");
            while (rs.next()) {
                System.out.println("Car id is: " + rs.getInt("id"));
                Thread.sleep(100);
                System.out.println("Car owner is: " + rs.getString("owner_id"));
                Thread.sleep(100);
                System.out.println("Car model is: " + rs.getString("model"));
            }

            rs = stmt.executeQuery("select * from owner");
            System.out.println("Success retrieving owners!!");
            while (rs.next()) {
                System.out.println("Owner with id: " + rs.getInt("id") + ", is: " + rs.getString("full_name"));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }


        //using PreparedStatement

        System.out.println("Now, using Prepared Statement");
        Thread.sleep(1000);

        int selection;
        String firstTableToQuery;
        String secondTableToQuery;
        // example from http://tutorials.jenkov.com/jdbc/preparedstatement.html
        // String sql = "update people set firstname=? , lastname=? where id=?";

        String sqlCar = "select * from car where id=?";

        System.out.println("Please, introduce which car id you want to retrieve");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        selection = Integer.parseInt(reader.readLine());
        reader.close();

        try {


            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/car_manag", "root", "root");

            PreparedStatement preparedStatement = con.prepareStatement(sqlCar);

            preparedStatement.setString(1, String.valueOf(selection));

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                System.out.println("You queried id: " + selection);
                System.out.println("Results are: ");
                System.out.println("Car id: " + rs.getInt("id"));
                Thread.sleep(100);
                System.out.println("Car owner: " + rs.getInt("owner_id"));
                Thread.sleep(100);
                System.out.println("Car brand: " + rs.getString("brand"));
                Thread.sleep(100);
                System.out.println("Car model: " + rs.getString("model"));
            }
            con.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
