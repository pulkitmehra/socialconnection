#
# Positive UAT Feature scenarion of Social connection.
#
Feature: Social Connection UAT positive cases

  Scenario: Fetching list of persons from social connection
    Given I have a REST Client and base url
    When I send a GET all person request to URL "/api/social/1.0/person" with accept header "application/json"
    Then I should get list of Persons as JSON response with status 200
    And The List size should be 7

  Scenario: Get path between two given person
    Given I have a REST Client and base url
    When I send a GET request to find path between two person "Foo" and "Tim" to URL "/api/social/1.0/person?fromName={0}&toName={1}" with accept header "application/json"
    Then I should get list of Persons path as JSON response with status 200
    And The path size should be 3

  Scenario: Get path count between two given person
    Given I have a REST Client and base url
    When I send a GET request to find count of person between two person "Foo" and "Tim" request to URL "/api/social/1.0/person/count?fromName={0}&toName={1}" with accept header "application/json"
    Then The path count should be 1
    
