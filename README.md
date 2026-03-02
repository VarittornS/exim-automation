# QA Automation Project: E-Commerce UI & Employee API

This repository contains a comprehensive automation testing suite covering both **Frontend (UI)** and **Backend (API)** for the QA Practice platform.

##  Tech Stack
* **Language:** Java 17
* **Testing Framework:** Cucumber BDD with JUnit
* **UI Automation:** Selenium WebDriver & WebDriverManager
* **API Automation:** Rest-Assured & JSON 
* **Environment:** Docker Desktop (API Server)
* **CI/CD:** GitHub Actions

---

## Project Structure
The project follows the standard Maven and Cucumber structure for scalability:
* **`src/test/resources/features`**: Gherkin feature files for UI and API.
* **`src/test/java/stepdefinitions`**: Implementation of test steps using Selenium and Rest-Assured.
* **`src/test/java/testrunner`**: Configuration to filter and execute tests via tags.

---

## Test Coverage

### 1. UI Automation (Selenium)
Focuses on the end-to-end e-commerce flow:
* **Login System:** Positive and Negative authentication scenarios.
* **Cart Calculation:** Verifying that the calculated total matches the UI display.
* **Address Validation:** Ensuring the shipping address is correctly concatenated as `Street, City - Country`.
* **Form Validation:** Handling mandatory field tooltips and validation error cases.

### 2. API Automation (Rest-Assured)
Tests the Employee Management system hosted on Docker:
* **POST /api/v1/employees**:
    * **Positive**: Successfully creating an employee (Status 201).
    * **Negative**: Validating `defaultMessage` for invalid email formats (Status 400).
* **GET /api/v1/employees/{id}**:
    * **Positive**: Retrieving existing employee data (Status 200).
    * **Negative**: Validating error message `Employee not found with ID {id}` for non-existing IDs (Status 404).

---

## How to Run Locally

### 1. Start the API Server
Execute the following command to run the backend container via Docker:
```bash
docker run -d --rm --name qa-practice-api -p 8887:8081 rvancea/qa-practice-api:latest
```

### 2. Execute Tests
```bash
mvn clean test
```

Note: You can filter tests using tags like @UI or @API in the TestRunner.java class.

## Test Results
Upon completion, a comprehensive HTML report is generated at: target/cucumber-reports.html
