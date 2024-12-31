package com.example.democontroller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

@Validated
@EnableConfigurationProperties(TodoProperties.class)
@ConfigurationProperties(prefix = "todo")
public class TodoProperties {
    @NotBlank
    private String myProperty;

    @ConstructorBinding
    @JsonCreator
    public TodoProperties(@JsonProperty("myProperty") String myProperty) {
        this.myProperty = myProperty;
    }

    public TodoProperties() {
        myProperty = null;
    }

    public @NotBlank String getMyProperty() {
        return myProperty;
    }

    public void setMyProperty(@NotBlank String myProperty) {
        this.myProperty = myProperty;
    }
}
