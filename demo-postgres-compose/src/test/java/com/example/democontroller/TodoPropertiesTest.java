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
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ByteArrayResource;

import java.io.IOException;
import java.io.StringReader;

import static com.example.democontroller.CustomAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

        @Test
        void isValid() {
            assertThat(todoProperties.getMyProperty()).isEqualTo("test");
        }
    }

    @Test
    void testHacks() throws IOException {
        var props = """
                my-property: test
                """;

        YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
        var ps = loader.load("inline", new ByteArrayResource(props.getBytes()));
        var env = new StandardEnvironment();
        env.getPropertySources().addFirst(ps.get(0));

        var appBuilder = new SpringApplicationBuilder(TodoProperties.class)
                .web(WebApplicationType.NONE)
                .environment(env);

        appBuilder.run();
    }
}
