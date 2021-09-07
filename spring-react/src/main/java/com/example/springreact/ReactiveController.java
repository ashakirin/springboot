package com.example.springreact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ReactiveController {
    EmployeeRepository employeeRepository;

    @Autowired
    public ReactiveController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/react")
    public Mono<Employee> getMono() {
        return employeeRepository.findById(1);
    }

    @GetMapping("/flux")
    public Flux<Employee> getFlux() {
        return employeeRepository.findAll();
    }

    @PostMapping("/create")
    public void postEmployee() {
        Employee employee = new Employee();
        employee.id = 2;
        employee.name = "Test";
        employee.salary = 100;

        Mono<Employee> employeeAsync = employeeRepository.save(employee);
        Employee newEmployee = employeeAsync.block();
    }
}
