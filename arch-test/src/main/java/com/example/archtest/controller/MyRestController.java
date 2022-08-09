package com.example.archtest.controller;

import com.example.archtest.service.MyDomainObject;
import com.example.archtest.service.MyService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/objects")
public class MyRestController {
    private final MyService myService;

    public MyRestController(MyService myService) {
        this.myService = myService;
    }

    @GetMapping("/{id}")
    public MyDomainObject read(@PathVariable("id") String id) {
        return myService.readAndProcess(id);
    }

    @PostMapping
    public String save(@RequestBody MyDomainObject myDomainObject) {
        return myService.save(myDomainObject);
    }
}
