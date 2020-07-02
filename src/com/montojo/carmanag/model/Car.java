package com.montojo.carmanag.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

//    @Column(name = "owner_id")
//    private int owner_id;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @Column(name = "brand")
    private String brand;
    @Column(name = "model")
    private String model;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<Services> services;

    public Car() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Services> getServices() {
        return services;
    }

    public void setServices(List<Services> services) {
        this.services = services;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", owner=" + owner +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", services=" + services +
                '}';
    }
}
