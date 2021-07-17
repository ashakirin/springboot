package com.example.resourceserver;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequestMapping(value = "/api/foos")
public class FooController {

    @CrossOrigin(origins = "http://localhost:8089")
    @GetMapping(value = "/{id}")
    public FooDto findOne(@PathVariable Long id) {
        return new FooDto(1L, "testFoo");
    }

    @GetMapping
    public Collection<FooDto> findAll() {
        List<FooDto> fooDtos = new ArrayList<>();
        fooDtos.add(new FooDto(1L, "testFoo"));
        return fooDtos;
    }
}
