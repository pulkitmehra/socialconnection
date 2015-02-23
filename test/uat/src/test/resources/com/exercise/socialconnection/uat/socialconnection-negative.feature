#
#  Negative UAT Feature scenarion of Social connection.
#
Feature: Social Connection UAT negative cases

  Scenario: Get error response when an unknown person name is passed
    Given I have a REST Client and base url
    When I send a GET request to find path between two person "Foo" and "Unknown" to URL "/api/social/1.0/person?fromName={0}&toName={1}" with accept header "application/json"
    Then I should get error response JSON response with status 404
