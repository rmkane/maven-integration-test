package org.example.web.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hello")
public class HelloController {

    @GetMapping
    public Map<String, String> sayHello() {
        Map<String, String> body = new HashMap<>();
        body.put("message", "Hello from Spring Boot!");
        return body;
    }
}




