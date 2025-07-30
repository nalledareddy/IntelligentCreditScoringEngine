package com.credit;

import com.credit.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserControllerTest {

    @Test
    void testScoreCalculation() {
        User user = new User();
        user.setAge(30);
        user.setIncome(600000);
        int score = new com.credit.service.CreditScoringService().calculateCreditScore(user);
        assertTrue(score >= 600 && score <= 850);
    }
}
