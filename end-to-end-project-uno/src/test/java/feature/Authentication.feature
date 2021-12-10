Feature: LoginPage

Scenario: Invalid password, valid username (negative test)
	Given that I am at the login page
	When I type in the username of "cRonaldo"
	And I type in the passowrd of "isPass"
	And I click on the login button 
	Then I should see an error message of "Incorrect username or password"

Scenario: Invalid username, valid password (negative test)
	Given that I am at the login page
	When I type in the username of "as.23"
	And I type in the password of "disIsMyPassword13"
	And I click on the login button 
	Then I should see an error message of "Incorrect username or password"

Scenario: Invalid username, invalid password (negative test)
	Given that I am at the login page
	When I type in the username of "as23"
	And I type in the password of "isPass"
	And I click on the login button
	Then I should see an error message of "Incorrect username or password"

Scenario Outline: Successful Employee login (positive test)
	Given that I am at the login page
	When I type in the username of <username>
	And I type in the password of <password>
	And I click on the login button
	Then I should be redirected to the employee homepage
	Then I should click on the logout button 

	Examples: 
		| username | password |
		| "bwhite" | "disIsMyPassword13" |
		
		
Scenario Outline: Successful Finance Manager login (positive test)
	Given that I am at the login page
	When I type in the username of <username>
	And I type in the password of <password>
	And I click on the login button
	Then I should be redirected to the finance manager homepage
	Then I should click on the logout button 

	Examples:
		| username | password |
		| "aWenger" | "disIsMyPassword13" |
		
		
Scenario Outline: Successsful Employee signup (positive test)
	 Given that I am at the login signup area page
	 When I type in the firstname of <firstname>
	 And I type in the lastname of <lastname>
	 And I type in the email of <email>
	 And I type in the userName of <userName>
	 And I type in the passWord of <passWord>
	 And I type in the role of <role>
	 Then I click on the signup button 
	 
	 Examples:
	 	|firstname | lastname | email | userName | passWord | role
	 	|"Frank" | "Lampard" | "fLampaard@cfc.com" | "fLampard" | "Employee" |
	

