Feature: Insert products using POST API

  Scenario Outline: Validate post product api works correctly
    Given I hit the url of post products api endpoint
    When I pass the url in the request
    And I pass the request body of product title <ProductTitle>
    Then I receive the response code as 200
    Examples:
      | ProductTitle |
      | Shoes        |