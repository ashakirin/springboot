package com.example.db;

import com.example.db.entity.AuthorEntity;
import com.example.db.entity.AuthorRepository;
import com.example.db.entity.BookEntity;
import com.example.db.entity.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RestControler {
    private static Logger logger = LoggerFactory.getLogger(RestControler.class);
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public RestControler(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @PostMapping
    public String createAuthor() {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setName("testName");
        authorRepository.save(authorEntity);
        logger.info("Author id: " + authorEntity.getId());
        return "test";
    }

    @GetMapping("authors/{id}")
    public AuthorEntity getAuthor(@PathVariable("id") Integer id) {
        return authorRepository.findById(id).get();
    }

    @PostMapping("/books")
    public void createBooks() {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setName("testBook");
        bookRepository.save(bookEntity);
        logger.info("Book id: " + bookEntity.getId());
    }

    @GetMapping("/books")
    public List<BookEntity> getBooks() {
        List<BookEntity> result = new ArrayList<>();
        Iterable<BookEntity> books = bookRepository.findAll();
        books.forEach(e -> result.add(e));
        return result;
    }

}
