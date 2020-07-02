package com.montojo.carmanag.model;

import javax.persistence.*;

@Entity
@Table(name = "service")
public class Services {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "date")
    private String date;


    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "car_id")
    private Car car;

    @Column(name = "notes")
    private String notes;
    @Column(name = "price")
    private Float price;

    public Services() {
    }

    public Services(int id, String name, Car car, String date, String notes, Float price) {
        this.id = id;
        this.name = name;
        this.car = car;
        this.date = date;
        this.notes = notes;
        this.price = price;
    }

    public Services(String name, Car car, String date, String notes, Float price) {
        this.name = name;
        this.car = car;
        this.date = date;
        this.notes = notes;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Float getPrice() {
        return price;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car carId) {
        this.car = carId;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Service id: " + id + " | car id: " + car + " | date: " + date + " | price: " + price + " | notes: " + notes;
    }
}
