Feature: Test Orange HRM Application

  Scenario: Validate Login functionality
    Given user is on login page
    When user enter valid credentails
    Then user is on home page and validate home page title
    And user validate Home page URL

  Scenario: Validate PIM Page Functionality
    When user is on pim Page
    Then validate PIM page url

  Scenario Outline: Validate Add new Functionality
    When user click on add Employee
    And user enter valid "<firstname>","<middlename>", "<lastname>" and click on save button
    And user capture the employee id
    And user click on add employee list
    And user enter employee id and click on search button
    And user select searched employee and click on delete
    Then validate confirm delete window and click on yes deleted

    Examples: 
      | firstname | middlename | lastname |
      | abc1      | pqr1       | xyz1     |
      | abc2      | pqr2       | xyz2     |
      | abc3      | pqr3       | xyz3     |
      | abc4      | pqr4       | xyz4     |

  Scenario: Validate logout functionality
    When user click on profile image
    And user click on logout button
