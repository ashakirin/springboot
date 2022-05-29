package com.example.openapi.demoopenapi;

import demo.openapi.versions.model.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.*;

@RestController
@RequestMapping("/rest-template")
public class TestRestTemplateController {
    RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/invoke")
    public String invoke() {
        try {
            ResponseEntity<Pet> petById = restTemplate.getForEntity("http://localhost:8081/pets/1", Pet.class);
            Pet pet = petById.getBody();
            System.out.println(pet);
            return "test";
        } catch (HttpClientErrorException e) {
            System.out.println("HttpClientErrorException: " + e.getMessage() + "; " + e.getStatusText());
            e.printStackTrace();
            return "error";
        } catch (HttpServerErrorException e) {
            System.out.println("HttpServerErrorException: " + e.getMessage() + "; " + e.getStatusText());
            e.printStackTrace();
            return "error";
        } catch (ResourceAccessException e) {
            System.out.println("ResourceAccessException: " + e.getClass() + "; " + e.getMessage());
            return "error";
        } catch (RestClientException e) {
            System.out.println("RestClientException: " + e.getClass() + "; " + e.getMessage());
            return "error";
        }
    }
}
