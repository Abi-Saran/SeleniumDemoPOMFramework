package com.test.MailAccess.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage_NOPCOM {
	public WebDriver driver;
	
	public LoginPage_NOPCOM(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id="Email")
	public WebElement txtFieldEmail;
	
	@FindBy(className="password")
	public WebElement txtFieldPassword;
	
	@FindBy(css=".button-1.login-button")
	public WebElement btnLogin;
	
	@FindBy(xpath="//*[contains(text(),'Logout')]")
	public WebElement btnSignout;
}
