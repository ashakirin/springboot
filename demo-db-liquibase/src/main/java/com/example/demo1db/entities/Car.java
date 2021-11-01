package com.example.demo1db.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "Car")
public class Car {
    @Id
    @GeneratedValue
    public long id;
    public String type;
    public boolean ready;

    public Car() {
    }

    public Car(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return id == car.id && Objects.equals(type, car.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
