package com.example.config.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ConfigPropertiesDemoApplicationTests {
    @Autowired
    ConfigProperties configProperties;

    @Test
    void contextLoads() {
        assertThat(configProperties.getHostName()).isEqualTo("host@mail.com");
        assertThat(configProperties.getCredentials()).isNotNull();
        assertThat(configProperties.getCredentials().getUsername()).isEqualTo("john");
        assertThat(configProperties.getTestBean()).isNotNull();
        assertThat(configProperties.getTestBean().getField1()).isEqualTo("test");
    }

}
