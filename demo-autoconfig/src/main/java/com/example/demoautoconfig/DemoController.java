package com.example.demoautoconfig;

import custom.autoconfig.MyTestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Autowired
    MyTestBean myTestBean;

    @GetMapping
    public String test() {
        System.out.println(myTestBean.getA());
        return "hello world: " + myTestBean.getA();
    }
}
