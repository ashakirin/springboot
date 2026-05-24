package com.example.democontroller.repo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
//import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class BookEntity {
    @Id
    public Long id;
}
