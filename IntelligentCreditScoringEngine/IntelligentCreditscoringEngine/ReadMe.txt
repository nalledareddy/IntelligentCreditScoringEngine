Intelligent Credit Scoring Engine Project Summary
Prepared by: Charan Reddy

Overview:
The Intelligent Credit Scoring Engine is an enterprise grade backend system developed to evaluate and score users creditworthiness using financial data. The system mimics real world fintech scoring engines like those used in banks and credit bureaus.

Tech Stack:
- Java 17
- Spring Boot (Spring Data JPA, Spring Web, Spring Validation)
- MySQL
- Apache Kafka (for scalable messaging between services)
- RESTful API
- JUnit and Mockito (for unit and integration testing)

Core Features:
1. User and Credit Profile Management: Secure APIs to onboard users and manage profiles.
2. Credit Score Computation**: Backend service that calculates a score using logic built around credit history, 	income, loans, etc.
3. Kafka Integration: Kafka is used for asynchronous messaging between services, allowing scalability.
4. Audit & Logs: Every credit evaluation is logged for auditability.
5. Validation & Exception Handling**: Complete request validations and exception responses.
6. Secure Design: Best practices in API and data layer security.

Folder Structure:
- /src
  - /main/java/com/charan/intelligentscore/
    - controller/
    - service/
    - model/
    - repository/
    - config/
    - exception/
  - /resources/
    - application.properties
- /test/
- /sql/
  - schema.sql
  - data.sql

Database Schema:
- users (id, name, dob, income, credit_history)
- credit_score (user_id, score, status, evaluated_at)

How to Run:
1. Clone the project.
2. Start MySQL and import the schema from `sql/schema.sql`.
3. Navigate to the root directory and run the Spring Boot app: `mvn spring-boot:run`
4. API is available at `http://localhost:8080/api/credit/score`
5. Kafka must be running on `localhost:9092` with topic `credit-score-events`.

Hosting Details:
1. Use services like Railway.app, Render, or deploy manually to AWS EC2.
2. Ensure the MySQL DB is publicly accessible or deployed with the app.
3. Use tools like Postman to demonstrate REST endpoints.


Prepared by: Charan Reddy
