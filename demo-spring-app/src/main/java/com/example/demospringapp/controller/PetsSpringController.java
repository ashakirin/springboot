package com.example.demospringapp.controller;

import com.example.demospringapp.service.DemoSpringService;
import demo.openapi.api.versions.DemoApi;
import demo.openapi.api.versions.PetsApi;
import demo.openapi.versions.model.NewPet;
import demo.openapi.versions.model.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PetsSpringController implements PetsApi {
    private final DemoSpringService demoSpringService;

    @Autowired
    public PetsSpringController(DemoSpringService demoSpringService) {
        this.demoSpringService = demoSpringService;
    }

    @Override
    public ResponseEntity<Pet> addPet(NewPet newPet) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deletePet(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Pet> findPetById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<List<Pet>> findPets(List<String> tags, Integer limit) {
        return null;
    }
}
