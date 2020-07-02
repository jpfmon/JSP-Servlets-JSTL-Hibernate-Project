package com.montojo.carmanag.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DatabaseUtil {

    private SessionFactory sessionFactory;
    private int counter;

    public DatabaseUtil(SessionFactory sessionFactory) {
        counter += 1;
        System.out.println("This is Databaseutil number " + counter);
        this.sessionFactory = sessionFactory;
    }

    public List<Owner> getOwners() {
        List<Owner> ownersList = new ArrayList<>();

        Session session = sessionFactory.getCurrentSession();
        System.out.println("Getting owners");

        try {
            session.beginTransaction();

            ownersList = session.createQuery("from Owner").getResultList();

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return ownersList;
    }

    public List<Services> getServices() {
        List<Services> servicesList = new ArrayList<>();
        Session session = sessionFactory.getCurrentSession();
        System.out.println("Getting services");
        try {
            session.beginTransaction();

            servicesList = session.createQuery("from Services").getResultList();

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            sessionFactory.close();
        }
        return servicesList;
    }

    public List<Car> getCars() {
        List<Car> carsList = new ArrayList<>();
        Session session = sessionFactory.getCurrentSession();
        System.out.println("Getting cars");
        try {
            session.beginTransaction();

            carsList = session.createQuery("from Car").getResultList();

            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return carsList;
    }


    public void saveNewOwner(Owner newOwner) {
        Session session = sessionFactory.getCurrentSession();
        System.out.println("Saving new owner");
        try {
//            String sql = "insert into owner (full_name,id_card,phone,email) values (?,?,?,?)";
            session.beginTransaction();

            session.save(newOwner);

            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public Owner getOwner(int ownerId) {

        Owner retrievedOwner = null;
        Session session = sessionFactory.getCurrentSession();
        String queryHib = "from Owner o where o.id= " + ownerId;
        try {
            session.beginTransaction();

            retrievedOwner = (Owner) session.createQuery(queryHib).getResultList().get(0);
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return retrievedOwner;
    }

    public void updateOwner(int id,String fullName,int idCardNumber, int phone, String email) {

        Session session = sessionFactory.getCurrentSession();

//        String sql = "update  owner set full_name = ?, id_card = ?, phone = ?, email = ? where id = ?";

        try {

        session.beginTransaction();

        Owner ownerToUpdate = session.get(Owner.class,id);

        ownerToUpdate.setFullName(fullName);
        ownerToUpdate.setIdCardNumber(idCardNumber);
        ownerToUpdate.setPhone(phone);
        ownerToUpdate.setEmail(email);

        session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void deleteOwner(int deleteOwnerId) {

        String sqlServices = "delete from service where car_id in (select id from car where owner_id = ?)";
        String sqlCars = "delete from car where owner_id = ?";
        String sqlOwners = "delete from owner where id = ?";

        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            Owner ownerToDelete = session.get(Owner.class,deleteOwnerId);
            session.delete(ownerToDelete);
            session.getTransaction().commit();
            System.out.println("Deleting records on all three tables succeeded!!!");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public ArrayList<Services> retrieveOwnerServices(int ownerId) {

        ArrayList<Services> servicesList = new ArrayList<>();

        Session session = sessionFactory.getCurrentSession();
        String query = "from Services s where s.car in (from Car c where c.owner.id = " + ownerId + ")";
//        String sql = "select * from service where car_id in (select id from car where owner_id = ?);";

        try {
            session.beginTransaction();

            servicesList = (ArrayList<Services>) session.createQuery(query).getResultList();

            session.getTransaction().commit();

            for (Services s:servicesList){
                System.out.println(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return servicesList;
    }

    public ArrayList<Car> retrieveOwnerCars(int ownerId) {

        ArrayList<Car> carsList = new ArrayList<>();
        Session session = sessionFactory.getCurrentSession();

//        String sql = "select * from car where owner_id = ?";
        String queryHib = "from Car c where c.owner.id= " + ownerId;
        try {
            session.beginTransaction();
            carsList = (ArrayList<Car>) session.createQuery(queryHib).getResultList();
            session.getTransaction().commit();

            for(Car c:carsList){
                System.out.println(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return carsList;
    }

    public void saveNewCar(Car carTemp) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
//        try {
//            connection = dataSource.getConnection();
//            String sql = "insert into car (owner_id,brand,model) values (?,?,?)";
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1, carTemp.getOwner_id());
//            preparedStatement.setString(2, carTemp.getBrand());
//            preparedStatement.setString(3, carTemp.getModel());
//            preparedStatement.execute();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            close(connection, preparedStatement, null);
//        }
    }

    public Car getCar(int carId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Car retrievedCar = null;
//        try {
//            connection = dataSource.getConnection();
//            String sql = "select * from car where id=?";
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1, carId);
//
//            resultSet = preparedStatement.executeQuery();
//
//            if (resultSet.next()) {
//                int id = resultSet.getInt("id");
//                int ownerId = resultSet.getInt("owner_id");
//                String brand = resultSet.getString("brand");
//                String model = resultSet.getString("model");
//                System.out.println("Retrieved Car. Id: " + id + " Owner id: " + ownerId + " Brand: " + brand + " Model: " + model);
//                retrievedCar = new Car(id, ownerId, brand, model);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            close(connection, preparedStatement, resultSet);
//        }
        return retrievedCar;
    }

    public void deleteCar(int deleteCarId) {
        Connection connection = null;
        PreparedStatement preparedStatementServices = null;
        PreparedStatement preparedStatementCars = null;

        String sqlServices = "delete from service where car_id = ?";
        String sqlCars = "delete from car where id = ?";

//        try {
//            connection = dataSource.getConnection();
//            preparedStatementServices = connection.prepareStatement(sqlServices);
//            preparedStatementCars = connection.prepareStatement(sqlCars);
//
//            preparedStatementServices.setInt(1, deleteCarId);
//            preparedStatementCars.setInt(1, deleteCarId);
//
//            preparedStatementServices.execute();
//            preparedStatementCars.execute();
//
//            System.out.println("Deleting records on all two tables succeeded!!!");
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                connection.close();
//                preparedStatementServices.close();
//                preparedStatementCars.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

    public Services getService(int serviceId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Services retrievedService = null;
//        try {
//            connection = dataSource.getConnection();
//            String sql = "select * from service where id=?";
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1, serviceId);
//
//            resultSet = preparedStatement.executeQuery();
//
//            if (resultSet.next()) {
//                int id = resultSet.getInt("id");
//                String name = resultSet.getString("name");
//                String date = resultSet.getString("date");
//                int carId = resultSet.getInt("car_id");
//                String notes = resultSet.getString("notes");
//                Float price = resultSet.getFloat("price");
//                retrievedService = new Services(id, name, carId, date, notes, price);
//                System.out.println(retrievedService.toString());
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            close(connection, preparedStatement, resultSet);
//        }
        return retrievedService;
    }

    public void saveNewService(Services newService) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
//        try {
//            connection = dataSource.getConnection();
//            String sql = "insert into service (name,date,car_id,notes,price) values (?,?,?,?,?)";
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, newService.getName());
//            preparedStatement.setString(2, newService.getDate());
//            preparedStatement.setInt(3, newService.getCarId());
//            preparedStatement.setString(4, newService.getNotes());
//            preparedStatement.setFloat(5, newService.getPrice());
//            preparedStatement.execute();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            close(connection, preparedStatement, null);
//        }
    }

    public void deleteService(int deleteServiceId) {
        Connection connection = null;
        PreparedStatement preparedStatementServices = null;

        String sqlServices = "delete from service where id = ?";

//        try {
//            connection = dataSource.getConnection();
//            preparedStatementServices = connection.prepareStatement(sqlServices);
//
//            preparedStatementServices.setInt(1, deleteServiceId);
//            preparedStatementServices.execute();
//            System.out.println("Deleting service succeeded!!!");
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                connection.close();
//                preparedStatementServices.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

    public void updateCar(Car updatedCar) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

//        try {
//            connection = dataSource.getConnection();
//            String sql = "update  car set owner_id = ?, brand = ?, model = ? where id = ?";
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1, updatedCar.getOwner_id());
//            preparedStatement.setString(2, updatedCar.getBrand());
//            preparedStatement.setString(3, updatedCar.getModel());
//            preparedStatement.setInt(4, updatedCar.getId());
//
//            preparedStatement.execute();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            close(connection, preparedStatement, null);
//        }
    }
}
