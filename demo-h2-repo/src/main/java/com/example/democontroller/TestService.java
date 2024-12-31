package com.example.democontroller;

import com.example.democontroller.repo.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    private final BookRepository bookRepository;

    public TestService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void myTest() {
        bookRepository.deleteById(1L);
    }
}
