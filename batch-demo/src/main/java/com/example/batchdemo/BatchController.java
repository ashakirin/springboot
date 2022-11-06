package com.example.batchdemo;

import com.example.batchdemo.entity.AuthorRepository;
import com.example.batchdemo.entity.BookEntity;
import com.example.batchdemo.entity.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Book;
import java.util.Optional;

@RestController
public class BatchController {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BatchController(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @GetMapping("/start")
    public String startJob() {

//        BookEntity bookEntity = new BookEntity();
//        bookEntity.setId(1L);
//        bookEntity.setName("test");
//        bookRepository.save(bookEntity);

        Iterable<BookEntity> allBooks = bookRepository.findAll();
        Optional<BookEntity> book = bookRepository.findById(10001L);

        return "OK: " + allBooks.iterator().hasNext() + " - " + book.isPresent();
    }
}
