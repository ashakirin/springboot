package com.example.democontroller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(DemoController.class)
public class DemoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldGetSuccessResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/demo"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("my demo"));
    }

    @Test
    void shouldUseOwnAssertions() {
        CustomAssertions.assertThat("This string contains my name Andrei")
                .containsMyName();
    }
}
