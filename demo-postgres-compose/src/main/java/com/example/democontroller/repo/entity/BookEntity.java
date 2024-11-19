package com.example.democontroller.repo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "books")
    @AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
}
