package com.example.db.demodb;

import com.example.db.entity.AuthorEntity;
import com.example.db.entity.AuthorRepository;
import com.example.db.entity.BookEntity;
import com.example.db.entity.BookRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class BookService {
    private AuthorRepository authorRepository;
    private BookRepository bookRepository;

    public BookService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public void createBooksTransactional(boolean isException) {
        BookEntity newBook1 = bookRepository.save(new BookEntity("transactional1", null));
        if (isException) {
            throw new RuntimeException("Transaction exception");
        }
        BookEntity newBook2 = bookRepository.save(new BookEntity("transactional2", null));
    }
}
