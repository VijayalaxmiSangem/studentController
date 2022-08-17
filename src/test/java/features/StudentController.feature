Feature: Student Controller Feature
  Background:
    Given a student

  Scenario: Verify that student details are created successfully
    When Creating a student
    Then Student Controller is created

  Scenario: Verify that student details are getting successfully
    When Creating a student
    Then Getting a student

  Scenario: Verify that getting a single student by using id.
    When Creating a student
    Then Getting a single student

  Scenario: Verify that student details are updated successfully.
    When Creating a student
    And Updating the student details
    Then Student details must be updated

  Scenario: Verify the student details are deleted.
    When Creating a student
    Then deleting the student details

  Scenario: Verify the error is thrown when the name field is not given.
    When Creating a student without name
    Then Name is required error thrown

  Scenario: Verify the error is thrown when the age field is not given.
    When Creating a student without age
    Then Age is required error thrown

  Scenario: Verify the error is thrown when the email field is not given.
    When Creating a student without email
    Then Email is required error thrown

  Scenario: Verify the error is thrown when the id is not given.
    When Creating a student without id
    Then Internal server error is thrown

  Scenario: Verify the path param is required for student updating.
    When Creating a student
    And Updating without path param
    Then Method not allowed error is thrown

  Scenario: Verify the path param is required for student deletion.
    When Creating a student
    And Deleting without path param
    Then Method not allowed error is thrown