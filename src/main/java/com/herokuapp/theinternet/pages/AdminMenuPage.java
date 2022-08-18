package com.herokuapp.theinternet.pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminMenuPage extends BasePageObject{
	private By menu = By.xpath("//*[@id=\"header\"]/div/header/div/div/div[1]/button");
	private By users = By.xpath("/html/body/div[6]/div[3]/div/ul/li[1]/div/p/ul/a[2]/div");
	private By appSearch = By.id("dtsearch_securitysystemsList");
	
	
	public AdminMenuPage(WebDriver driver, Logger log) {
		super(driver, log);
	}

	
	public void openLeftBarModule() {
		click(menu);
	}
	
	public UsersPage clickUsers() {
		click(users);
		
		return new UsersPage(driver, log);
	}
	
	public void useSearchBar(String appServerName) {
		click(appSearch);
		type(appServerName + "\n", appSearch);
		
	}
	
	public By getActualAppServer(String appServerName) {
		By actualAppServer = By.xpath("//*[contains(text(), '" + appServerName + "')]");
		waitForVisibilityOf(actualAppServer, 10);
		
		return actualAppServer;
	}
	
	public void clickAppServer(String appServerName) {
		
		click(getActualAppServer(appServerName));
	}
}
