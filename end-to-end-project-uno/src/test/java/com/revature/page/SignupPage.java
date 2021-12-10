package com.revature.page;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignupPage {

	private WebDriver driver;
	private WebDriverWait webDriverwait;

	@FindBy(xpath = "//input[@id='firstname']")
	private WebElement firstnameInput;

	@FindBy(xpath = "//input[@id='lastname']")
	private WebElement lastnameInput;

	@FindBy(xpath = "//input[@id='email']")
	private WebElement emailInput;

	@FindBy(xpath = "//input[@id='userName']")
	private WebElement usernameInput;

	@FindBy(xpath = "//input[@id='passWord']")
	private WebElement passwordInput;

	@FindBy(xpath = "//input[@id='role']")
	private WebElement roleInput;

	@FindBy(xpath = "//input[@id='signup']")
	private WebElement signupButton;

	@FindBy(xpath = "//p[contains(text(),'{\"message\":\"You entered an invalid first name\"}')]")
	private WebElement errorMessage;

	@FindBy(xpath = "//p[contains(text(),'{\"User successfully sign up as\"}')]")
	private WebElement successMessage;

	public SignupPage(WebDriver driver) {
		this.driver = driver;
		this.webDriverwait = new WebDriverWait(driver, Duration.ofSeconds(10));

		PageFactory.initElements(driver, this);
	}

	public WebElement getFirstnameInput() {
		return this.firstnameInput;
	}

	public WebElement getLastnameInput() {
		return this.lastnameInput;
	}

	public WebElement getEmailInput() {
		return this.emailInput;
	}

	public WebElement getUsernameInput() {
		return this.usernameInput;
	}

	public WebElement getPasswordInput() {
		return this.passwordInput;
	}

	public WebElement getRoleInput() {
		return this.roleInput;
	}
	
	public WebElement getSignupButton() {
		return this.signupButton;
	}

//	public WebElement getSuccessMessage() {
//		return this.webDriverwait.until(ExpectedConditions.visibilityOf(this.successMessage));
//	}

	public WebElement getErrorMessage() {
		return this.webDriverwait.until(ExpectedConditions.visibilityOf(this.errorMessage));
	}

}
