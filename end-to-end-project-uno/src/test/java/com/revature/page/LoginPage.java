package com.revature.page;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

	private WebDriver driver;
	private WebDriverWait webDriverwait;

	@FindBy(xpath = "//input[@id='username']")
	private WebElement usernameInput;

	@FindBy(xpath = "//input[@id='password']")
	private WebElement userpasswordInput;

	@FindBy(xpath = "//input[@id='login']")
	private WebElement userloginButton;
	
	@FindBy(xpath = "//button[@id='logout']")
	private WebElement userLogoutButton;

	@FindBy(xpath = "//p[contains(text(),'Incorrect username or password')]")
	private WebElement errorMessage;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		this.webDriverwait = new WebDriverWait(driver, Duration.ofSeconds(10));

		PageFactory.initElements(driver, this);
	}

	public WebElement getUsernameInput() {
		return this.usernameInput;
	}

	public WebElement getPasswordInput() {
		return this.userpasswordInput;
	}

	public WebElement getLoginButton() {
		return this.userloginButton;
	}
	
	public WebElement getLogoutButton() {
		return this.userLogoutButton;
	}

	public WebElement getErrorMessage() {
		return this.webDriverwait.until(ExpectedConditions.visibilityOf(this.errorMessage));
	}
}
