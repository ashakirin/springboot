package com.example.openapi.demoopenapi;

import demo.openapi.api.versions.PetsApi;
import demo.openapi.versions.model.Pet;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "test", url = "http://localhost:8080")
public interface PetClient extends PetsApi {
}
