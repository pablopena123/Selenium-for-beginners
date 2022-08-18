package com.herokuapp.theinternet.pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePageObject {

	private By usernameLocator = By.id("user-name-input");
	private By passwordLocator = By.id("password-input");
	private By logInButtonLocator = By.id("login-button");

	public LoginPage(WebDriver driver, Logger log) {
		super(driver, log);
	}

	/** Execute log in */
	public SecureAreaPage logIn(String username, String password) {
		log.info("Executing LogIn with username [" + username + "] and password [" + password + "]");
		type(username, usernameLocator);
		type(password, passwordLocator);
		click(logInButtonLocator);
		return new SecureAreaPage(driver, log);
	}

}