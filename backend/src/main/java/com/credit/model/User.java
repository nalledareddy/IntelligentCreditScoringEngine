package com.credit.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // wrapper types to allow null
    private Integer age;
    private Double income;
    private Double loanAmount;
    private Integer employmentYears;
    private Double debt;

    private Integer creditScore;

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public Double getIncome() { return income; }
    public void setIncome(Double income) { this.income = income; }

    public Double getLoanAmount() { return loanAmount; }
    public void setLoanAmount(Double loanAmount) { this.loanAmount = loanAmount; }

    public Integer getEmploymentYears() { return employmentYears; }
    public void setEmploymentYears(Integer employmentYears) { this.employmentYears = employmentYears; }

    public Double getDebt() { return debt; }
    public void setDebt(Double debt) { this.debt = debt; }

    public Integer getCreditScore() { return creditScore; }
    public void setCreditScore(Integer creditScore) { this.creditScore = creditScore; }
}
