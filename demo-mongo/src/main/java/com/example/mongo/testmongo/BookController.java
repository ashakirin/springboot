package com.example.mongo.testmongo;

import com.example.mongo.testmongo.entity.BookAggregate;
import com.example.mongo.testmongo.entity.BookEntity;
import com.example.mongo.testmongo.entity.BookRepository;
import com.example.mongo.testmongo.entity.LibraryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
public class BookController {
    private final BookRepository bookRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public BookController(final BookRepository bookRepository, MongoTemplate mongoTemplate) {
        this.bookRepository = bookRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @GetMapping("/books/{name}")
    List<BookEntity> getBook(@PathVariable("name") String name) {

        List<BookEntity> book = bookRepository.findByName(name);
        if (!book.isEmpty()) {
            System.out.println(book.get(0).getIssued());
        }
        return book;
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

    @GetMapping("/books/aggregate")
    List<BookAggregate> getAggregate() {
        LibraryEntity lib = new LibraryEntity();
        lib.setName("test library1");
        LibraryEntity savedLibraryEntity = mongoTemplate.save(lib);

        BookEntity bookEntity = new BookEntity();
        bookEntity.setName("test book");
        bookEntity.setLibraryId(savedLibraryEntity.getId());
        mongoTemplate.save(bookEntity);

        LookupOperation lookupOperation = LookupOperation.newLookup().
                from("libraryEntity").
                localField("id").
                foreignField("libraryId").
                as("library");

        AggregationOperation match = Aggregation.match(Criteria.where("name").is("test book"));

        Aggregation aggregation = Aggregation.newAggregation(lookupOperation, match);

        List<BookAggregate> results = mongoTemplate.aggregate(aggregation, "bookEntity", BookAggregate.class).getMappedResults();
        return results;
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
        Instant instantNow = Instant.now();
        Date date = Date.from(instantNow);
        bookEntity.setIssued(date);
        System.out.println(date);
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
