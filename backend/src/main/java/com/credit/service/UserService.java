package com.credit.service;

import com.credit.model.User;
import com.credit.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repo;
    private final CreditScoringService fallbackScorer;

    public UserService(UserRepository repo, CreditScoringService fallbackScorer) {
        this.repo = repo;
        this.fallbackScorer = fallbackScorer;
    }

    public User saveUser(User u) {
        return repo.save(u);
    }

    public List<User> getAllUsers() {
        return repo.findAll();
    }

    public User getUser(Long id) {
        return repo.findById(id).orElse(null);
    }
}
