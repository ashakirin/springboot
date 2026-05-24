package com.example.democontroller;

import com.example.democontroller.repo.BookRepository;
import com.example.democontroller.repo.entity.BookEntity;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.StaleObjectStateException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class DemoController {

    private final BookRepository bookRepository;

    public DemoController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/demo-delete-id")
    public String demoDeleteId() {
        try {
            List<Long> ids = new ArrayList<>();
            ids.stream()
                    .forEach(id -> {
                        bookRepository.deleteById(id);
                    });

            if (!bookRepository.existsById(1L)) {
                throw new EmptyResultDataAccessException(1);
            }
            bookRepository.deleteById(100L);
        } catch (Exception e) {
            return "Error: "+ e.toString();
        }
        return "hello";
    }

    @GetMapping("/demo-delete")
    public String demoDelete() {
        BookEntity bookEntity = bookRepository.findById(1L).get();
        try {
            Thread.sleep(60000);
            bookRepository.delete(bookEntity);
        } catch (Exception e) {
            if (e.getCause() instanceof StaleObjectStateException)
            return "Error: "+ e.toString();
        }
        return "hello";
    }

    @GetMapping("/demo-create")
    public String demoCreate() {
        BookEntity bookEntity = new BookEntity();
        bookEntity.id = 1L;
//        bookEntity.setAuthor("testAuthor");
//        bookEntity.setTitle("testTitle");
        bookRepository.save(bookEntity);
        return "hello";
    }
}
