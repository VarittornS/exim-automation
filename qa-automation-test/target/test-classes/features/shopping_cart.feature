@UI @ShoppingCartTest
Feature: End-to-End Shopping Flow

  Scenario: Pick products once and validate multiple shipping scenarios
    Given I open the e-commerce page
    And I login with "admin@admin.com" and "admin123"
    
    # Step 2: Add products to cart and calculate total (Log will appear only once)
    When I search and add "Huawei Matebook X Pro" to cart by clicking Next
    And I adjust quantity of "Huawei Matebook X Pro" to 2
    And I search and add "iPhone 13 Pro" to cart by clicking Next
    And I adjust quantity of "iPhone 13 Pro" to 3
    And I validate the Total cost of items in cart
    And I proceed to checkout

    # Step 3 & 4: Validate shipping form constraints and final address format
    Then I validate the following shipping scenarios:
      | phone      | street           | city    | country  | result  |
      |            | 911 Siam Streets | Bangkok | Thailand | FAIL    |
      | 0812345678 |                  | Bangkok | Thailand | FAIL    |
      | 0812345678 | 911 Siam Streets |         | Thailand | FAIL    |
      | 0812345678 | 911 Siam Streets | Bangkok |          | FAIL    |
      | 0812345678 | 911 Siam Streets | Bangkok | Thailand | SUCCESS |