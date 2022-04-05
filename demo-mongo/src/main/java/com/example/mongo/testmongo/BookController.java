package com.example.mongo.testmongo;

import com.example.mongo.testmongo.entity.BookEntity;
import com.example.mongo.testmongo.entity.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
public class BookController {
    private final BookRepository bookRepository;

    @Autowired
    public BookController(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/books/{name}")
    List<BookEntity> getBook(@PathVariable("name") String name) {
        return bookRepository.findByName(name);
    }

    @GetMapping("/books/concurrent1")
    List<BookEntity> getBook1() {
        List<BookEntity> test1 = bookRepository.findByName("test1");
        test1.get(0).setName(test1.get(0).getName() + " modified1");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bookRepository.save(test1.get(0));

        return test1;
    }

    @GetMapping("/books/concurrent2")
    List<BookEntity> getBook2() {
        List<BookEntity> test1 = bookRepository.findByName("test1");
        test1.get(0).setName(test1.get(0).getName() + " modified2");
        bookRepository.save(test1.get(0));

        return test1;
    }

    @GetMapping("/books/isbn/{isbn}")
    List<BookEntity> getBookByISBN(@PathVariable("isbn") String isbn) {
        return bookRepository.findByISBN(isbn);
    }

    @PostMapping("/books")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity createBook(@RequestBody BookEntity bookEntity) {
        bookEntity.setId(UUID.randomUUID().toString());
        bookRepository.save(bookEntity);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(
                MvcUriComponentsBuilder
                        .fromMethodName(BookController.class, "createBook", bookEntity)
                        .pathSegment(bookEntity.getId())
                        .buildAndExpand()
                        .toUri());

        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
    }
}
