package com.example.db.demodb;

import com.example.db.entity.AuthorRepository;
import com.example.db.entity.BookEntity;
import com.example.db.entity.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BooksRestControllerTest {
    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BooksRestController booksRestController;

    private BookEntity bookEntity = new BookEntity("name", null);

    @Test
    public void testFindBookSuccess() {
        // Arrange
        when(bookRepository.findByName("name")).thenReturn(Optional.of(bookEntity));

        // Act
        Book book = booksRestController.getBook("name");

        // Assert
        assertThat(book.getName()).isEqualTo("name");
    }

}