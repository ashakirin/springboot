package com.example.democontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;

import static com.example.democontroller.CustomAssertions.assertThat;

public class TodoPropertiesTest {

    @Test
    void validateProperties() {
        TodoProperties testProp = new TodoProperties("testProp");
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        validator.validate(testProp);
        assertThat(validator.validate(testProp)).isEmpty();
    }

    @Test
    void validateNullProperties() {
        TodoProperties testProp = new TodoProperties(null);
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        assertThat(validator.validate(testProp)).isNotEmpty();
    }

    @Test
    void yaml() throws JsonProcessingException {
        YAMLMapper mapper = YAMLMapper.builder()
                .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
                .addModule(new ParameterNamesModule())
                .build();

        TodoProperties todoProperties = mapper.readValue("""
                "myProperty": "test"
                """, TodoProperties.class);
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        assertThat(validator.validate(todoProperties)).isEmpty();
    }

    @Nested
    @SpringBootTest(classes = {TodoProperties.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE,
            properties = {
            "todo.my-property=test"
            })
    @ConfigurationPropertiesScan
    class PropertyIntegrationTest {
        @Autowired
        TodoProperties todoProperties;

        @TestConfiguration
        @EnableConfigurationProperties(TodoProperties.class)
        static class TestConfig {}

        @Test
        void isValid() {
            assertThat(todoProperties.getMyProperty()).isEqualTo("test");
        }
    }
}
