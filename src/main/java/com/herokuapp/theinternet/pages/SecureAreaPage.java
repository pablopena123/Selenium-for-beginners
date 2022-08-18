package com.herokuapp.theinternet.pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

public class SecureAreaPage extends BasePageObject {

	private String pageUrl = "https://ecm-test.accesshub.cloud.ibm.com/ECMv6/request/requestHome";
	private By logOutButton = By.xpath("//a[@class='button secondary radius']");
	private By message = By.id("flash-messages");
	private By createUserButton = By.xpath(
			"/html//div[@id='root']/div[@class='main-container']/div[1]//a[@href='https://ecm-test.accesshub.cloud.ibm.com/ECM/workflowmanagement/createupdateuserrequestfirststep']//div[@role='button']/h5[@class='feature-title']");
	private By applications = By.xpath("//*[@id=\"root\"]/div/header/div/div/div[4]/div[2]/button");
	private By admin = By.xpath("//*[@id=\"root\"]/div/header/div/div/div[4]/div[2]/div/div/div[6]/a");
	private By pendingApprovalModule = By.xpath("//*[@id=\"root\"]/div/div[1]/div/div/div[1]/div[2]/div/a[3]/div");

	public SecureAreaPage(WebDriver driver, Logger log) {
		super(driver, log);
	}

	/** Go to Home Page */
	public void goHome() {
		log.info("Going to Home Page");
		openUrl(pageUrl);
	}

	/** Get URL variable from PageObject */
	public String getPageUrl() {
		return pageUrl;
	}

	/** Verification if logOutButton is visible on the page */
	public boolean isLogOutButtonVisible() {
		return find(logOutButton).isDisplayed();
	}

	/** Return text from success message */
	public String getSuccessMessageText() {
		return find(message).getText();

	}

	/** Go to Create User Request module */
	public UserRequestPage enterCreateUserRequest() {
		log.info("Entering the create user request module");
		click(createUserButton);
		return new UserRequestPage(driver, log);
	}

	public By pendingApprovalModulePath() {
		return pendingApprovalModule;
	}

	/** Go to Pending Approval module */
	public PendingApprovalPage goToPendingApprovalModule() {
		log.info("Starting FedRamp Verify User Request");
		try {
			click(pendingApprovalModule);
		} catch (TimeoutException exception) {
			log.info("Exception catched " + exception.getMessage());
			sleep(3000);
		}
		return new PendingApprovalPage(driver, log);
	}

	/** Open admin page */
	public AdminMenuPage adminButton() {
		log.info("Opening the admin page");
		click(applications);
		click(admin);

		return new AdminMenuPage(driver, log);
	}
}
