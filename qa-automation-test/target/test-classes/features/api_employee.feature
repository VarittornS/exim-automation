@API
Feature: Employee API Management

  Background:
    # Docker Port 8887 mapping
    Given I set the API base URL to "http://localhost:8887"

  @POST @Positive
  Scenario: Create a new employee successfully
    When I send a POST request to "/api/v1/employees" with name "John Doe" and email "john.doe@test.com"
    Then the response status code should be 201

  @POST @Negative
  Scenario: Failed to create employee with invalid email format
    When I send a POST request to "/api/v1/employees" with name "Jane" and email "invalid-email"
    Then the response status code should be 400
    And the response body should contain "defaultMessage"

  @GET @Positive
  Scenario: Get existing employee information
    When I send a GET request to "/api/v1/employees/1"
    Then the response status code should be 200

  @GET @Negative
  Scenario: Get error when employee ID does not exist
    When I send a GET request to "/api/v1/employees/999"
    Then the response status code should be 404
    And the response body should contain "Employee not found with ID 999"