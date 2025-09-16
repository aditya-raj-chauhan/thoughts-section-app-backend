package com.devotion.thoughts.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/public/hello")
    public String publicHello() {
        return "This is a PUBLIC endpoint 🚀";
    }

    @GetMapping("/quotes/hello")
    public String userHello() {
        return "Hello, authenticated USER 👤";
    }

    @GetMapping("/admin/hello")
    public String adminHello() {
        return "Hello, mighty ADMIN 👑";
    }
}
