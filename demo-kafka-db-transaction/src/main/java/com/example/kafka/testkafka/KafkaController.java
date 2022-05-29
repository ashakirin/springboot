package com.example.kafka.testkafka;

import com.example.kafka.testkafka.entity.Book;
import com.example.kafka.testkafka.entity.BookRepository;
import com.example.kafka.testkafka.entity.TransactionalSaver;
import com.example.kafka.testkafka.messaging.Sender;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class KafkaController {
    private final Sender sender;
    private final BookRepository bookRepository;
    private final TransactionalSaver transactionalSaver;


    public KafkaController(Sender sender, BookRepository bookRepository, TransactionalSaver transactionalSaver) {
        this.sender = sender;
        this.bookRepository = bookRepository;
        this.transactionalSaver = transactionalSaver;
    }

    @GetMapping("/send")
    public void sendIntoTopic() {
        sender.send("test");
    }

    @GetMapping("/sendobject")
    public void sendObjectIntoTopic() {
        sender.sendObject("test");
    }

    @PostMapping("/entities")
    public void createEntity() {
        Book book = new Book("test");
        bookRepository.save(book);
    }

    @PostMapping("/transactional")
    public void transactionalCreateEntity() {
        sender.sendInTransaction("test");
    }

    @GetMapping("/entities/{name}")
    public Book getEntity(@PathVariable String name) {
        List<Book> books = bookRepository.findByName(name);
        if (!books.isEmpty()) {
            return books.get(0);
        } else {
            return null;
        }
    }
}
