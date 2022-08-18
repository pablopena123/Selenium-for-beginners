package com.herokuapp.theinternet.pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UserRequestSubmittedPage extends BasePageObject {

	private String expectedUrl = "https://ecm-test.accesshub.cloud.ibm.com/ECM/jbpmworkflowmanagement/createupdateuserrequestnextstep";
	private String expectedMessage = "Your Request has been submitted!";
	private By messagePath = By.xpath("/html/body/div[3]/div/div[2]/h4");
	private By requestNumberField = By.xpath("/html/body/div[3]/div/table/tbody/tr/td[2]");
	private By usernameRequest = By.xpath("/html/body/div[3]/div/table/tbody/tr/td[1]");

	private static String variableRequestNumber;

	private static String variableUsernameRequest;
	
	public static String getVariableRequestNumber() {
		return variableRequestNumber;
	}

	public static void setVariableRequestNumber(String variableRequestNumber) {
		UserRequestSubmittedPage.variableRequestNumber = variableRequestNumber;
	}

	public static String getVariableUsernameRequest() {
		return variableUsernameRequest;
	}

	public static void setVariableUsernameRequest(String variableUsernameRequest) {
		UserRequestSubmittedPage.variableUsernameRequest = variableUsernameRequest;
	}

	public UserRequestSubmittedPage(WebDriver driver, Logger log) {
		super(driver, log);
	}

	public String getPageUrl() {
		return expectedUrl;
	}

	public String successMessage() {
		return expectedMessage;
	}

	public String getMessage() {
		return text(messagePath);
	}

	public String getRequestNumber() {
		return text(requestNumberField);
	}

	public String getUsername() {
		return text(usernameRequest);
	}

	
}
