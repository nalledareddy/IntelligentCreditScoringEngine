package com.credit.controller;

import com.credit.model.User;
import com.credit.repository.UserRepository;
import com.credit.service.CreditScoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired private UserRepository userRepository;
    @Autowired private CreditScoringService scoringService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        user.setCreditScore(scoringService.calculateCreditScore(user));
        return userRepository.save(user);
    }

    @GetMapping
    public List<User> allUsers() {
        return userRepository.findAll();
    }
}
