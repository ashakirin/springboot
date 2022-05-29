package com.example.openapi.demoopenapi;

import demo.openapi.versions.model.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    PetClient petClient;

    @Autowired
    public TestController(PetClient petClient) {
        this.petClient = petClient;
    }

    @GetMapping("/invoke")
    public String invoke() {

        ResponseEntity<Pet> petById = petClient.findPetById(1L);
        Pet pet = petById.getBody();
        System.out.println(pet);
        return "test";
    }
}
