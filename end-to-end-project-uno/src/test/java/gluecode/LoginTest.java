package gluecode;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.revature.page.EmployeePage;
import com.revature.page.FinanceManagerPage;
import com.revature.page.LoginPage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginTest {

	private WebDriver driver;
	private WebDriverWait webDriverWait;
	private FinanceManagerPage financeManagerPage;
	private EmployeePage employeePage;
	private LoginPage loginPage;

	@Given("that I am at the login page")
	public void that_i_am_at_the_login_page() {
		System.setProperty("webdriver.chrome.driver", "/Applications/chromedriver");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("-incoginito");
		this.driver = new ChromeDriver(options);

		this.driver.get("http://ec2-3-138-126-45.us-east-2.compute.amazonaws.com:8081/");
		this.loginPage = new LoginPage(driver);

	}

	@When("I type in the username of {string}")
	public void i_type_in_the_username_of(String string) {
		this.loginPage.getUsernameInput().sendKeys(string);
	}

	@When("I type in the passowrd of {string}")
	public void i_type_in_the_passowrd_of(String string) {
		this.loginPage.getPasswordInput().sendKeys(string);

	}

	@When("I click on the login button")
	public void i_click_on_the_login_button() {
		this.loginPage.getLoginButton().click();

	}

	@Then("I should see an error message of {string}")
	public void i_should_see_an_error_message_of(String string) {
		String actual = this.loginPage.getErrorMessage().getText();
		Assertions.assertEquals(string, actual);

		this.driver.close();
		this.driver.quit();
	}

	@When("I type in the password of {string}")
	public void i_type_in_the_password_of(String string) {
		this.loginPage.getPasswordInput().sendKeys(string);

	}
	

	@Then("I should click on the logout button")
	public void i_should_click_on_the_logout_button() {
		this.loginPage.getLogoutButton().click();
		
		this.driver.close();
		this.driver.quit();

	}

	@Then("I should be redirected to the employee homepage")
	public void i_should_be_redirected_to_the_employee_homepage() {
		this.employeePage = new EmployeePage(this.driver);
		String expectedHeadingOutcome = "Employee Reimbursement Dashboard";

		Assertions.assertEquals(expectedHeadingOutcome,
				this.employeePage.getNewReimbursementRequestHeading().getText());

	}
	
	@Then("I should be redirected to the finance manager homepage")
	public void i_should_be_redirected_to_the_finance_manager_homepage() {
	   
		this.financeManagerPage = new FinanceManagerPage(this.driver);
		String expectedHeadingOutcome = "All Reimbursement Dashboard";

		Assertions.assertEquals(expectedHeadingOutcome,
				this.financeManagerPage.getAllReimbursementHeading().getText());

	}

}
