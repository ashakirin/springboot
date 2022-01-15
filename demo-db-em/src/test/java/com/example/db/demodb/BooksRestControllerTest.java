package com.example.db.demodb;

import com.example.db.BooksRestController;
import com.example.db.entity.AuthorRepository;
import com.example.db.entity.BookEntity;
import com.example.db.entity.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BooksRestController.class)
class BooksRestControllerTest {
    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    BookService bookService;

    @Autowired
    MockMvc mockMvc;

    @InjectMocks
    private BooksRestController booksRestController;

    private BookEntity bookEntity = new BookEntity("testName", null);

    @Test
    public void shouldFindBookSuccess() throws Exception {
        // Arrange
        when(bookRepository.findByName("testName")).thenReturn(Optional.of(bookEntity));

        mockMvc.perform(MockMvcRequestBuilders.get("/book/testName").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("testName"));
    }
}