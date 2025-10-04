package com.credit.controller;

import com.credit.model.User;
import com.credit.repository.UserRepository;
import com.credit.service.CreditScoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final CreditScoringService fallbackScorer;

    @Autowired
    public UserController(UserRepository userRepository, CreditScoringService fallbackScorer) {
        this.userRepository = userRepository;
        this.fallbackScorer = fallbackScorer;
        this.restTemplate = new RestTemplate();
    }

    /**
     * Registration endpoint. Expects JSON body with:
     * { name, age, income, loanAmount, employmentYears, debt }
     *
     * This will call the ML service (http://localhost:5000/predict by default)
     * with those 5 features and set creditScore from ML response. If ML fails,
     * fallback deterministic scoring will be applied.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (user == null) return ResponseEntity.badRequest().body("no user");

        String mlUrl = System.getenv().getOrDefault("ML_SERVICE_URL", "http://localhost:5000/predict");

        Map<String, Object> payload = new HashMap<>();
        // Ensure keys the ML service expects:
        payload.put("age", user.getAge() != null ? user.getAge() : 0);
        payload.put("income", user.getIncome() != null ? user.getIncome() : 0.0);
        payload.put("loanAmount", user.getLoanAmount() != null ? user.getLoanAmount() : 0.0);
        payload.put("employmentYears", user.getEmploymentYears() != null ? user.getEmploymentYears() : 0);
        payload.put("debt", user.getDebt() != null ? user.getDebt() : 0.0);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String,Object>> request = new HttpEntity<>(payload, headers);

        try {
            Map resp = restTemplate.postForObject(mlUrl, request, Map.class);
            if (resp != null && resp.get("creditScore") != null) {
                Integer score = ((Number) resp.get("creditScore")).intValue();
                user.setCreditScore(score);
            } else {
                user.setCreditScore(fallbackScorer.calculateCreditScore(user));
            }
        } catch (Exception e) {
            // fallback if ML unreachable or returns error
            user.setCreditScore(fallbackScorer.calculateCreditScore(user));
        }

        User saved = userRepository.save(user);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<User>> allUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return userRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
