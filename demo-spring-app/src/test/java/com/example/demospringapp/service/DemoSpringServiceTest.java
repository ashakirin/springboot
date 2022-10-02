package com.example.demospringapp.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DemoSpringServiceTest {
    private DemoSpringService demoSpringService = new DemoSpringService();

    @Test
    void shouldReturnSpecifiedString() {
        String result = demoSpringService.demoOperation("test");
        assertThat(result).isEqualTo("Hello world: test");
    }

}