package com.example.mongo.testmongo;

import com.example.mongo.testmongo.entity.BookEntity;
import com.example.mongo.testmongo.entity.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
    private final BookRepository bookRepository;

    @Autowired
    public BookController(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/book")
    BookEntity getBook() {
        BookEntity book = new BookEntity();
        book.setId(1);
        book.setName("test");
        bookRepository.save(book);

        return bookRepository.findByName("test").get();
    }
}
