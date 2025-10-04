package com.credit.service;

import com.credit.model.User;
import org.springframework.stereotype.Service;

/**
 * Simple deterministic scoring fallback used if ML service fails.
 * This is not the ML model — ML predictions are requested from ML service.
 */
@Service
public class CreditScoringService {

    public int calculateCreditScore(User user) {
        int score = 300;
        if (user == null) return score;

        Double income = user.getIncome();
        Integer age = user.getAge();
        Double loan = user.getLoanAmount();
        Integer empYears = user.getEmploymentYears();
        Double debt = user.getDebt();

        if (income != null && income > 500000) score += 200;
        else if (income != null && income > 250000) score += 120;
        else if (income != null && income > 100000) score += 60;

        if (age != null && age >= 26 && age <= 44) score += 80;

        if (empYears != null && empYears >= 5) score += 40;

        if (loan != null && loan > 300000) score -= 50;
        if (debt != null && debt > 200000) score -= 60;

        if (score > 850) score = 850;
        if (score < 300) score = 300;
        return score;
    }
}
