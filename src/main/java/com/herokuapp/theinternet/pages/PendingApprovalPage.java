package com.herokuapp.theinternet.pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class PendingApprovalPage extends BasePageObject{
	private By searchBar = By.xpath("//*[@id=\"root\"]/div/div[1]/div/div/section/div/div[4]/div[1]/div[1]/div/div/input");
	private By requestRow = By.xpath("//*[@id=\"root\"]/div/div[1]/div/div/section/div/div[6]/div/div[2]/div");
	private By seeDetailsButton = By.xpath(
			"//*[@id=\"root\"]/div/div[1]/div/div/section/div/div[6]/div/div[2]/div/div[10]/div/div/button[1]/span");
	private By approveRequestButton = By.id("approverequestButton");
	private By subCoMall = By.id("subcomall");
	
	public PendingApprovalPage(WebDriver driver, Logger log) {
		super(driver, log);
	}
	
	/** Wait for the request number to show on the page and return the actual path */ 
	public By actualRequestNumber(String requestNumber) {
		log.info("Searching for request number");
	//	By actualRequestNumber = By.linkText(requestNumber);
		By actualRequestNumber = By.xpath("//*[text()='" + requestNumber + "']");
		waitForVisibilityOf(actualRequestNumber, 10);
		
		return actualRequestNumber;
	}
	
	/** Search request number on SearchBar*/
	public void searchBar(String requestNumber) {
		log.info("Searching request number on search Bar");
		type(requestNumber + "\n", searchBar);
	}
	
	/** Hover mouse over request row*/
	public void hoverOver() {
		log.info("Hover mouse over request row");
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(requestRow)).perform();
	}
	
	public void seeDetails() {
		click(seeDetailsButton);
	}
	
	public void approveRequest() {
		click(approveRequestButton);
		click(subCoMall);
	}
}