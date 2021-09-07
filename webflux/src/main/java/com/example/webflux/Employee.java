package com.example.webflux;

public class Employee {

    int id;
    String name;
    long salary;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getSalary() {
        return salary;
    }

    //Getters and setters

    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", salary=" + salary + "]";
    }
}
