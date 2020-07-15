package com.montojo.carmanag.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DatabaseUtil {

    private SessionFactory sessionFactory;
    private int counter;

    public DatabaseUtil() {

        counter += 1;
        System.out.println("This is Databaseutil number " + counter);
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Car.class)
                .addAnnotatedClass(Owner.class)
                .addAnnotatedClass(Services.class)
                .buildSessionFactory();
    }

    public List<Owner> getOwners() {
        List<Owner> ownersList = new ArrayList<>();

        Session session = sessionFactory.getCurrentSession();
//        System.out.println("Getting owners");

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
//        System.out.println("Getting services");
        try {
            session.beginTransaction();
            servicesList = session.createQuery("from Services").getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return servicesList;
    }

    public List<Car> getCars() {
        List<Car> carsList = new ArrayList<>();
        Session session = sessionFactory.getCurrentSession();
//        System.out.println("Getting cars");
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
//        System.out.println("Saving new owner");
        try {
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
//            e.printStackTrace();
        } finally {
            session.close();
        }
        return retrievedOwner;
    }

    public void updateOwner(int id, String fullName, int idCardNumber, int phone, String email) {

        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            Owner ownerToUpdate = session.get(Owner.class, id);
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

        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            Owner ownerToDelete = session.get(Owner.class, deleteOwnerId);
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


        try {
            session.beginTransaction();
            servicesList = (ArrayList<Services>) session.createQuery(query).getResultList();
            session.getTransaction().commit();
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

        String queryHib = "from Car c where c.owner.id= " + ownerId;
        try {
            session.beginTransaction();
            carsList = (ArrayList<Car>) session.createQuery(queryHib).getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return carsList;
    }

    public void saveNewCar(int ownerId, String brand, String model) {

        Session session = sessionFactory.getCurrentSession();

        try {
            session.getTransaction().begin();
            Owner ownerOfTheNewCar = session.get(Owner.class, ownerId);
            Car newCar = new Car(ownerOfTheNewCar, brand, model);
            session.save(newCar);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public Car getCar(int carId) {

        Car retrievedCar = null;
        Session session = sessionFactory.getCurrentSession();

        try {
            session.getTransaction().begin();
            retrievedCar = session.get(Car.class, carId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return retrievedCar;
    }

    public void deleteCar(int deleteCarId) {

        Session session = sessionFactory.getCurrentSession();

        try {
            session.getTransaction().begin();
            Car carToDelete = session.get(Car.class, deleteCarId);
            session.delete(carToDelete);
            session.getTransaction().commit();
            System.out.println("Deleting records on all two tables succeeded!!!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public Services getService(int serviceId) {

        Services retrievedService = null;

        Session session = sessionFactory.getCurrentSession();

        try {
            session.getTransaction().begin();
            retrievedService = session.get(Services.class, serviceId);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return retrievedService;
    }

    public void saveNewService(String name, int carId, String date, String notes, float price) {

        Session session = sessionFactory.getCurrentSession();

        try {
            session.getTransaction().begin();
            Car carOfService = session.get(Car.class, carId);
            Services newService = new Services(name, carOfService, date, notes, price);
            session.save(newService);
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void deleteService(int deleteServiceId) {

        Session session = sessionFactory.getCurrentSession();
        try {
            session.getTransaction().begin();
            Services serviceToDelete = session.get(Services.class,deleteServiceId);
            session.delete(serviceToDelete);
            session.getTransaction().commit();
            System.out.println("Deleting service succeeded!!!");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void updateCar(int idCarToUpdate, int OwnerId, String brand, String model) {

        Session session = sessionFactory.getCurrentSession();

        try {
            session.getTransaction().begin();

            Car carToUpdate = session.get(Car.class, idCarToUpdate);
            carToUpdate.setBrand(brand);
            carToUpdate.setModel(model);

            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
