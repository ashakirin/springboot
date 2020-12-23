package com.example.dbmongo;

import com.example.dbmongo.entity.Book;
import com.example.dbmongo.entity.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    BookRepository bookRepository;

    @GetMapping("{id}")
    public Book getBook(@PathVariable("id") int id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.get();
    }

    @PostMapping()
    public int createBook() {
        Book book = new Book("test");
        Book savedBook = bookRepository.save(book);
        return savedBook.getId();
    }
}
