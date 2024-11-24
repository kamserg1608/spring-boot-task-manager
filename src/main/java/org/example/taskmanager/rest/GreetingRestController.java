package org.example.taskmanager.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingRestController {

    @GetMapping("/")
    public String sayHello() {
        return "Hello World!";
    }

}