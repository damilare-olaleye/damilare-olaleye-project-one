# Project-1: Employee Reimbursement System (ERS)

## Summary

* The Expense Reimbursement System (ERS) will manage the process of reimbursing employees for expenses incurred while on company time.
* All employees in the company can login and submit requests for reimbursement and view their past tickets and pending requests.
* Finance managers can log in and view all reimbursement requests and past history for all employees in the company.
* Finance managers are authorized to approve and deny requests for expense reimbursement.

## Employee User Stories
* An Employee can login
* An Employee can submit a reimbursement request
* An Employee can upload an image of his/her receipt as part of the reimbursement request
* An Employee can view their pending reimbursement requests
* An Employee can view their resolved reimbursement requests
* An Employee can logout
* An Employee can create a new account (sign up)
* An Employee can search for other employees or finance manager 

## Finance Manager User Stories
* A Finance Manager can login
* A Finance Manager can view the Financial Manager Homepage
* A Finance Manager can approve/deny pending reimbursement requests
* A Finance Manager can view all pending requests from all employees
* A Finance Manager can logout
* A Finance Manager can create a new account (sign up)

## Technologies Used
* Java 8
* Junit (unit test)
* Selenium and Cucumber (e2e test)
* JavaScript, HTML, and CSS (front-end)
* Postgres (database)
* AWS (RDS and EC2) and Jenkins for Deployment
* SonarCloud for code analysis

## To-do list
* Host on AWS S3 bucket
* Authentication email for new users
* Redesign profile page
* Change front end from JavaScript to Angular (in progress)
* Inform employees via email whenever finance manager accept or decline reimbursement requests

## How to run the project:
http://ec2-3-138-126-45.us-east-2.compute.amazonaws.com:8081/
* Default username for Finance Manger: aWenger
* Default password for Finance Manger: disIsMyPassword13
* Default username for Employee: bWhite
* Default password for Employee: disIsMyPassword13
* You can also create a new account
