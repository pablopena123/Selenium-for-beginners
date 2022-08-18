package com.herokuapp.theinternet.pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class UserRequestPage extends BasePageObject {

	private By cnumElement = By.id("USEREDCNUM");
	private By firstNameElement = By.id("FIRSTNAME");
	private By lastNameElement = By.id("LASTNAME");
	private By workEmailElement = By.id("WORKEMAIL");
	private By citizen = By.id("s2id_selectSqlEnum18");
	private By otherElement = By.xpath("//*[@id=\"select2-drop\"]/ul/li[2]/div");
	private String citizenOption = "OTHER";
	private By userHasCloudID = By.id("s2id_selectSqlEnum111");
	private By managerSelected = By.xpath("//*[@id=\"select2-drop\"]/ul/li[1]");
	private By managerCnumElement = By.id("MANAGEREDCNUM");
	private By selectManager = By.id("s2id_selectSqlEnum105");
	private By managerBP = By.id("s2id_selectSqlEnum114");
	private By selectedMCNUM = By.xpath("//*[@id=\"select2-drop\"]/ul/li");
	private By submitButton = By.id("onpb");
	
	public UserRequestPage(WebDriver driver, Logger log) {
		super(driver, log);
	}
	
	public void completeForm(String cnum, String firstname, String lastName, String workEmail) {
		type(cnum, cnumElement);
		type(firstname, firstNameElement);
		type(lastName, lastNameElement);
		type(workEmail, workEmailElement);
		click(citizen);
		waitForTextToBePresentIn(otherElement, citizenOption);
	}

	/** This method is to select NO for Cloud ID */
	public void managerCloudIdNo(String managerCnum) {
		click(userHasCloudID);
		wait.until(ExpectedConditions
				.textToBePresentInElementLocated(By.xpath("//*[@id=\"select2-drop\"]/ul/li[1]/div"), "No"));
		click(managerSelected);
		waitForSpinnerInvisbility();
		type(managerCnum, managerCnumElement);
	}

	/**
	 * This method is to select a specific Manager from the list and click YES for
	 * Cloud ID
	 */
	public void managerCloudIdYes() {
		// Select Manager drop down
		click(selectManager);
		wait.until(ExpectedConditions.textToBePresentInElementLocated(
				By.xpath("//*[@id=\"select2-drop\"]/ul/li[1]/div"), "(6520017PJN) Aanum Shaikh"));
		click(managerSelected);
		waitForSpinnerInvisbility();
		// MBluepages CNUM drop down
		click(managerBP);
		wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//*[@id=\"select2-drop\"]/ul/li/div"),
				"1J1519897"));
		click(selectedMCNUM);
	}
	
	public UserRequestSubmittedPage clickSubmitButton() {
		
		click(submitButton);

		//wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[3]/div/div[2]/h4")));
		waitForVisibilityOf(By.xpath("/html/body/div[3]/div/div[2]/h4"), 10);
		
		return new UserRequestSubmittedPage(driver, log);
	}
}
