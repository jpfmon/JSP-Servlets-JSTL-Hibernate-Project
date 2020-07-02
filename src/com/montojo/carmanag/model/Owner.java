package com.montojo.carmanag.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "owner")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "id_card")
    private int idCardNumber;
    @Column(name = "phone")
    private int phone;
    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Car> cars;


    public Owner() {
    }

    public Owner(String fullName,int idCardNumber, int phone, String email){
        this.fullName = fullName;
        this.idCardNumber = idCardNumber;
        this.phone = phone;
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setIdCardNumber(int idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public int getIdCardNumber() {
        return idCardNumber;
    }

    public int getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public List<Car> getCars() {
        return cars;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", idCardNumber=" + idCardNumber +
                ", phone=" + phone +
                ", email='" + email + '\'' +
                ", cars=" + cars +
                '}';
    }
}
