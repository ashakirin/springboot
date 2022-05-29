package com.example.openapi.demoopenapi;

import demo.openapi.api.versions.PetsApi;
import demo.openapi.versions.model.Cat;
import demo.openapi.versions.model.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

@RestController
public class PetController implements PetsApi {
    @Autowired
    HttpServletRequest request;

    @Autowired
    TestFeinClient testFeinImplementation;

    @Value("${encrypted.myproperty}")
    private String password;

    @Override
    public ResponseEntity<Pet> addPet(Pet pet) {
        System.out.println(pet.getClass());
        return null;
    }

    @Override
    public ResponseEntity<Void> deletePet(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Pet> findPetById(Long id) {
        Cat cat = new Cat();
        cat.petType("Cat");
        cat.setName("my cat");
        cat.setHuntingSkill(Cat.HuntingSkillEnum.ADVENTUROUS);
//        testFeinImplementation.testGet();
//
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            System.out.println(headerName +  ": " + request.getHeader(headerNames.nextElement()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(cat);
//        Pet pet = new Pet();
//        pet.setName("test pet");
//        return ResponseEntity.status(HttpStatus.OK).body(pet);
    }

    @Override
    public ResponseEntity<List<Pet>> findPets(List<String> tags, Integer limit) {
        return null;
    }
}
