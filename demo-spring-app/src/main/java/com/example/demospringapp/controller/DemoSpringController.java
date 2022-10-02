package com.example.demospringapp.controller;

import com.example.demospringapp.service.DemoSpringService;
import demo.openapi.api.versions.DemoApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoSpringController implements DemoApi {
    private final DemoSpringService demoSpringService;

    @Autowired
    public DemoSpringController(DemoSpringService demoSpringService) {
        this.demoSpringService = demoSpringService;
    }

    public ResponseEntity<String> returnDemo(@PathVariable String param) {
        return ResponseEntity.ok(demoSpringService.demoOperation(param));
    }
}
