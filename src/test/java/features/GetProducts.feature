Feature: Get all products from API

  Scenario: Verify the get api for products
    Given I hit the url of get products api endpoint
    When I pass the url in the request
    Then I receive the response code as 200


  Scenario Outline: Verify the rate of the first product is correct
    Given I hit the url of get products api endpoint
    When I pass the url in the request
    Then I verify that the rate of first product is <FirstProductRate>
    Examples:
      | FirstProductRate |
      | 3.9              |