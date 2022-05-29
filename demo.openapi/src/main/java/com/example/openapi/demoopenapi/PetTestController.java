package com.example.openapi.demoopenapi;

import demo.openapi.api.versions.PetsApi;
import demo.openapi.versions.model.Pet;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/test")
public class PetTestController implements PetsApi {
    @Override
    public ResponseEntity<Pet> addPet(Pet pet) {
        try {
            return ResponseEntity.created(new URI("/124")).build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e.getMessage());
        }
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
