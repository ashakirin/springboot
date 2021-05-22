package com.example.demo1db;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DBController.class)
class DBControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DBService dbService;

    @Test
    public void testGet() throws Exception {
        when(dbService.findCar("test")).thenReturn(Optional.of(new CarDTO("test", true)));
        mockMvc.perform(MockMvcRequestBuilders.get("/cars/test"))
                .andExpect(MockMvcResultMatchers.jsonPath("type").value("test"));
    }
}