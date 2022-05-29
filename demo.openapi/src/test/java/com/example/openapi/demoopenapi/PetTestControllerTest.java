package com.example.openapi.demoopenapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.openapi.versions.model.Cat;
import demo.openapi.versions.model.Pet;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.regex.Matcher;

import static org.hamcrest.Matchers.containsString;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PetTestController.class)
public class PetTestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldSuccessfullyCreatePayment() throws Exception {

        Cat cat = new Cat();
        cat.setName("my cat");
        ResultActions payments = mockMvc.perform(MockMvcRequestBuilders.post("/pets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(marshalObject(cat)));

        payments.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location", containsString("124")));
    }

    private String marshalObject(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}
