package com.revature.page;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EmployeePage {

	private WebDriver driver;
	private WebDriverWait webDriverWait;
	
	@FindBy(xpath="//h3[contains(text(),'Employee Reimbursement Dashboard')]")
	private WebElement newReimbursementRequestHeading;
	
	public EmployeePage(WebDriver driver) {
		this.driver = driver;
		this.webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		PageFactory.initElements(driver, this);
	}
	
	public WebElement getNewReimbursementRequestHeading() {
		return this.webDriverWait.until(ExpectedConditions.visibilityOf(newReimbursementRequestHeading));
	}
	
}
