package com.example.db.demodb;

import java.awt.print.Book;

import com.example.db.data.BookEntity;
import com.example.db.data.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookRestController {
    @Autowired
    BookRepository bookRepository;

    @GetMapping("/book")
    public BookEntity getBook() {
        return bookRepository.findByName("Test1").get();
    }
}
