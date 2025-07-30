CREATE DATABASE IF NOT EXISTS creditscore;
USE creditscore;

CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    age INT,
    income DOUBLE,
    credit_score INT
);
