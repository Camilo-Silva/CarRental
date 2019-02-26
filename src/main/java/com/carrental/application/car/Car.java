package com.carrental.application.car;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String brand;
    private String type;

    protected Car() {  }

    public Car(String brand, String type) {
        this.brand = brand;
        this.type = type;
    }

    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    public String getBrand() {
        return this.brand;
    }
    
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
}