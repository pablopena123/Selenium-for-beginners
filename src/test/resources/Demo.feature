@Demo
Feature: test

    Scenario Outline: Create a new user request 
    
        Given Enter the url
        When Login to the ACCESSHUB application with username and password 
				Then User clicks on create user request module
				Then Enter the CNUM "<Cnum>" first name "<FirstName>" the last name "<LastName>" work email "<WorkEmail>" and manager CNUM "<ManagerCnum>" and submit the request
				Then Approve the request 1 times
				Then Verify user has been created correctly
				And Logout the application
	
	Examples:
	| Cnum | FirstName | LastName | WorkEmail | ManagerCnum |
	| 003025781 | Alfonso | Guzman Zavala | Alfonso.Guzman@ibm.com | 070630781 |
	
