@Search
Feature: Search
  Search in stackoverflow


  Background: 
    Given user navigates to home page
    And user clicks on the hamburger menu


@SearchJobs
  Scenario: Job search
    When user clicks on the Jobs link
    Then user is navigated to the jobs screen
    When user enters <searchCriteria>
    And user clicks on Search button
    Then system displays jobs related to the search criteria


@SearchQuestions
  Scenario: Question search
    When user clicks on the Questions link
    Then user is navigated to the questions screen
    When user clicks on filter button
    And user selects Filter by as No answers
    And user selects Sorted by as Recent activity
    And user selects Tagged with The following tags:
    And user enters <tags>
    And user clicks on Apply filter button
    Then system displays questions based on the selection
