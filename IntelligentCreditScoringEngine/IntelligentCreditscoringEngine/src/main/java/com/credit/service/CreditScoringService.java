package com.credit.service;

import com.credit.model.User;
import org.springframework.stereotype.Service;

@Service
public class CreditScoringService {
    public int calculateCreditScore(User user) {
        int score = 300;
        if (user.getIncome() > 500000) score += 200;
        if (user.getAge() > 25 && user.getAge() < 45) score += 100;
        return Math.min(score, 850);
    }
}
