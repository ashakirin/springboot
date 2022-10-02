package com.example.demospringapp.controller;

import com.example.demospringapp.service.DemoSpringService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebMvcTest(DemoSpringController.class)
public class DemoSpringControllerTest {
    @MockBean
    DemoSpringService demoSpringService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void testController() throws Exception {
        when(demoSpringService.demoOperation("myparam")).thenReturn("test");
        mockMvc.perform(MockMvcRequestBuilders.get("/demo/myparam"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("test"));
    }


}
