package com.example.db.demodb;

import com.example.db.entity.AuthorEntity;
import com.example.db.entity.AuthorRepository;
import com.example.db.entity.BookEntity;
import com.example.db.entity.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@RestController
public class BooksRestController {
    Logger LOGGER = LoggerFactory.getLogger(BooksRestController.class);

    private BookRepository bookRepository;

    private AuthorRepository authorRepository;

    private final BookService bookService;

    @Autowired
    public BooksRestController(BookRepository bookRepository, AuthorRepository authorRepository, BookService bookService) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public List<Book> getBooks() {
        Iterable<BookEntity> books = bookRepository.findAll();
        List<Book> result = new ArrayList<>();
        books.forEach(e -> result.add(new Book(e.getId(), e.getName(), (e.getAuthorEntity() != null) ? e.getAuthorEntity().getName() : null)));
        return result;
    }

    @GetMapping("/book/{name}")
    public Book getBook(@PathVariable("name") final String name) {
        Optional<BookEntity> bookEntity = bookRepository.findByName(name);
        return bookEntity
                .map(b -> new Book(b.getId(), b.getName(), (b.getAuthorEntity() != null) ? b.getAuthorEntity().getName() : null))
                .orElse(null);
    }

    @PostMapping("/authors")
    public Integer createAuthor(@RequestBody final String name) {
        final AuthorEntity authorEntity = new AuthorEntity(name);
        final AuthorEntity createdAuthor = authorRepository.save(authorEntity);
        return createdAuthor.getId();
    }

    public static class BookDTO {
        public String name;
        public Integer authorId;

        public BookDTO(String name, Integer authorId) {
            this.name = name;
            this.authorId = authorId;
        }
    }

    @PostMapping("/books")
    private Integer createBook(@RequestBody BookDTO bookDTO) {
        Optional<AuthorEntity> author = authorRepository.findById(bookDTO.authorId);
        BookEntity book = author
                .map(a -> new BookEntity(bookDTO.name, a))
                .orElse(new BookEntity(bookDTO.name, null));
        BookEntity newBook = bookRepository.save(book);
        return newBook.getId();
    }

    @PostMapping("/transactional")
    @ResponseStatus(HttpStatus.CREATED)
    private void createBooksTransactional() {
        bookService.createBooksTransactional(true);
    }

    @GetMapping("/async")
    List<Book> async() {
        LOGGER.info("Executing controller: " + Thread.currentThread().getName());
        try {
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                try {
                    LOGGER.info("Executing async: " + Thread.currentThread().getName());
                    Thread.sleep(1000);
                    return "test";
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return null;
                }
            });
            LOGGER.info("Async result: " + future.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Arrays.asList(new Book(1, "myBook", "myAuthor"));
    }
}
