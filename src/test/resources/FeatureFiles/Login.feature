# missing:
#gitignore
#upload to github
#REPORTING!!!
#create bat file
# delete login.feature (this file)

@login
Feature: Login to Link


  @loginValid
  Scenario: Login with valid credentials
    Given User is in the login screen
    And User has valid credentials
    When enter valid UserID
    And enter valid password
    And click on Hamburger Menu
    Then User is navigated to the dashboard
    
    
  @loginInvalid
  Scenario Outline: To verify error message with invalid credentials
    Given User is in the login screen
    When enter invalid User ID "<invalid_UserID>"
    And enter invalid Password "<invalid_password>"
    And click on Hamburger Menu
    Then system displays error message "<error_message>"

    Examples: 
      | invalid_UserID           | invalid_password | error_message                                           |
      | nono@invalid.test        | notvalid123      | The email address or password you entered is incorrect. |
      | deactivated@invalid.test | invalid!abc      | The email address or password you entered is incorrect. |