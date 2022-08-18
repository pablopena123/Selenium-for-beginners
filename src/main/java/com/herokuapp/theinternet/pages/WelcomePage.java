package com.herokuapp.theinternet.pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class WelcomePage extends BasePageObject {

	private String pageUrl = "https://ecm-test.accesshub.cloud.ibm.com/ECMv6/request/requestHome";
		
	//private By formAuthenticationLinkLocator = By.linkText("Form Authentication");

	public WelcomePage(WebDriver driver, Logger log) {
		super(driver, log);
	}

	/** Open WelcomePage with it's URL */
	public void openPage() {
		log.info("Opening page: " + pageUrl);
		openUrl(pageUrl);
		log.info("Page opened!");
		
	}
	/** Maximize browser window */
	public void maximizePage() {
		driver.manage().window().maximize();
		log.info("Page Maximized");
	}

	/** Open LoginPage by clicking on Form Authentication Link */
	public LoginPage clickFormAuthenticationLink() {
		log.info("Clicking Form Authentication link on Welcome Page");
		//click(formAuthenticationLinkLocator);
		return new LoginPage(driver, log);
	}

}