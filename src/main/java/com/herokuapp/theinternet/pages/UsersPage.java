package com.herokuapp.theinternet.pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UsersPage extends BasePageObject {

	private static String pageUrl = "https://ecm-test.accesshub.cloud.ibm.com/ECM/users/list";
	private By userSearch = By.xpath("//*[@id=\"dtsearch_usersList\"]");
	
	public UsersPage(WebDriver driver, Logger log) {
		super(driver, log);
	}

	public static String getPageUrl() {
		return pageUrl;
	}

	public static void setPageUrl(String pageUrl) {
		UsersPage.pageUrl = pageUrl;
	}

	/** Search an application/Server using the searchbar */
	public void searchBar(String usernameRequest) {
		type(usernameRequest + "\n", userSearch);
	}
	
	public By searchRequestNumberPath(String usernameRequest) {		
		By actualUsernameRequest = By.xpath("//*[contains(text(), '" + usernameRequest + "')]");
		log.info("XPath of actual username request is " + actualUsernameRequest);
		
		return actualUsernameRequest;
	}

	 
	
	
	
	
	
	
}
