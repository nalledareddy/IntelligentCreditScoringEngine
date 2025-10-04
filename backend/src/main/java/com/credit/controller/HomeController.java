package com.credit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "✅ Intelligent Credit Scoring Engine (backend) is running!";
    }
}
