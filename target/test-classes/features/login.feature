@UI @LoginTest
Feature: Login System

  Background:
    Given I open the e-commerce page

  # --- Negative Cases: Verification of error messages ---
  Scenario Outline: Login with wrong credentials
    When I login with "<email>" and "<password>"
    Then I should see an error message "Bad credentials! Please try again!"

    Examples:
      | email           | password  |
      | wrong@admin.com | admin123  |
      | admin@admin.com | wrongpass |

  # --- Positive Case: Successful login ---
  Scenario: Login with valid credentials
    When I login with "admin@admin.com" and "admin123"
    Then I should see the logout button