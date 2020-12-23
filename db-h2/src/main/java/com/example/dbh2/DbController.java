package com.example.dbh2;

import com.example.dbh2.entity.Book;
import com.example.dbh2.entity.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Optional;

@RestController
@RequestMapping("/book")
public class DbController {
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/{id}")
    public Book getBook(@PathVariable("id") Integer id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.get();
    }

    @PostMapping
    public Integer createBook() {
        Book book = new Book("test");
        Book savedBook = bookRepository.save(book);
        return book.getId();
    }
}
