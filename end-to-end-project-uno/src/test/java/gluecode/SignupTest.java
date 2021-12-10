package gluecode;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.revature.page.LoginPage;
import com.revature.page.SignupPage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SignupTest {

	private WebDriver driver;
	private WebDriverWait webDriverWait;
	private SignupPage signupPage;

	@Given("that I am at the login signup area page")
	public void that_i_am_at_the_login_signup_area_page() {
		System.setProperty("webdriver.chrome.driver", "/Applications/chromedriver");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("-incoginito");
		this.driver = new ChromeDriver(options);

		this.driver.get("http://ec2-3-138-126-45.us-east-2.compute.amazonaws.com:8081/");
		this.signupPage = new SignupPage(driver);
	}

	@When("I type in the firstname of {string}")
	public void i_type_in_the_firstname_of(String string) {
		this.signupPage.getFirstnameInput();
	}

	@When("I type in the lastname of {string}")
	public void i_type_in_the_lastname_of(String string) {
		this.signupPage.getLastnameInput();
	}

	@When("I type in the email of {string}")
	public void i_type_in_the_email_of(String string) {
		this.signupPage.getEmailInput();
	}

	@When("I type in the userName of {string}")
	public void i_type_in_the_user_name_of(String string) {
		this.signupPage.getUsernameInput();
	}
	
	@When("I type in the passWord of {string}")
	public void i_type_in_the_pass_word_of(String string) {
	   this.signupPage.getPasswordInput();
	}


	@When("I type in the role of <role>")
	public void i_type_in_the_role_of_role() {
		this.signupPage.getRoleInput();
	}

	@Then("I click on the signup button")
	public void i_click_on_the_signup_button() {
		this.signupPage.getSignupButton();
	}

//	@Then("I should see a success message of {string} {string}")
//	public void i_should_see_a_success_message_of(String string, String string2) {
//		this.signupPage.getSuccessMessage();
//
//		this.driver.close();
//		this.driver.quit();
//	}

}
