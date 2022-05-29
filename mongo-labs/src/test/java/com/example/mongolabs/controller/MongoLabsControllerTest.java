package com.example.mongolabs.controller;

import com.example.mongolabs.entity.Payment;
import com.example.mongolabs.service.MongoLabsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebMvcTest
class MongoLabsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MongoLabsService mongoLabsService;

    @Test
    public void shouldReturnPayment() throws Exception {

        when(mongoLabsService.getPayment(anyString())).thenReturn(new Payment("test", BigDecimal.TEN));

        mockMvc.perform(MockMvcRequestBuilders.get("/labs/payments/123")).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("amount").value(10));
    }

}