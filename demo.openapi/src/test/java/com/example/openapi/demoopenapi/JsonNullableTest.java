package com.example.openapi.demoopenapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.openapitools.jackson.nullable.JsonNullable;
import org.openapitools.jackson.nullable.JsonNullableModule;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JsonNullableTest {
    public static class TestJsonNullable {
        public String normalProperty;
        public JsonNullable<String> nullableProperty;
    }

    @Test
    public void testNullableJsonProperty() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.registerModule(new JsonNullableModule());

        TestJsonNullable testJsonNullable1 = new TestJsonNullable();
        testJsonNullable1.normalProperty = "normal";
        testJsonNullable1.nullableProperty = JsonNullable.of(null); //JsonNullable.undefined();

        String serialized = objectMapper.writeValueAsString(testJsonNullable1);
        System.out.println(serialized);

        TestJsonNullable testJsonNullable = objectMapper.readValue(serialized.getBytes(StandardCharsets.UTF_8), TestJsonNullable.class);
        System.out.println(testJsonNullable.nullableProperty);
    }
}
