package com.herokuapp.theinternet.fedrampah;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
//import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import com.herokuapp.theinternet.base.TestUtilities;

public class LoginTests extends TestUtilities {


	@Test(priority = 1, enabled = true, groups = { "positiveTest", "smokeTest" })
	public void positiveLoginTest() {

		log.info("Starting loginTest");

//		open test page 	
		String url = "https://the-internet.herokuapp.com/login";
		driver.get(url);
		log.info("Page is open");

//		enter username
		WebElement username = driver.findElement(By.id("username"));
		username.sendKeys("tomsmith");

//		enter password 
		WebElement password = driver.findElement(By.name("password"));
		password.sendKeys("SuperSecretPassword!");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

//		click login button
		WebElement logInButton = driver.findElement(By.tagName("button"));
		wait.until(ExpectedConditions.elementToBeClickable(logInButton));
		logInButton.click();

		/*
		 * try { Thread.sleep(5000); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
//		verifications:
//		new url
		String expectedUrl = "https://the-internet.herokuapp.com/secure";
		String actualUrl = driver.getCurrentUrl();

		Assert.assertEquals(actualUrl, expectedUrl, "Actual page URL is not the same a expected ");

//		logout button is visible
		WebElement logOutButton = driver.findElement(By.xpath("//a[@class='button secondary radius']"));
		Assert.assertTrue(logOutButton.isDisplayed(), "Log out button is not visible");

//		successful login management 
		WebElement successMessage = driver.findElement(By.cssSelector("div#flash"));

		String expectedMessage = "You logged into a secure area!";
		String actualMessage = successMessage.getText();
		// Assert.assertEquals(actualMessage, expectedMessage, "Actual message is not
		// the same a expected ");
		Assert.assertTrue(actualMessage.contains(expectedMessage),
				"Actual message does not contain expected message. \nActual Message:  " + actualMessage
						+ "\n Expected message: " + expectedMessage);

	}

	@Parameters({ "username", "password", "expectedMessage" })
	@Test(priority = 2, enabled = true, groups = { "negativeTest", "smokeTest" })
	public void NegativeLoginTest(String username, String password, String expectedMessage) {

		log.info("Starting NegativeLoginTest" + username + " " + password);
// 		create driver

		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
		WebDriver driver = new ChromeDriver();

//		open test page 	
		String url = "https://the-internet.herokuapp.com/login";
		driver.get(url);
		log.info("Page is open");

// 		Maximize browser window 
		driver.manage().window().maximize();

//		enter username
		WebElement usernameElement = driver.findElement(By.id("username"));
		usernameElement.sendKeys(username);

//		enter password 
		WebElement passwordElement = driver.findElement(By.name("password"));
		passwordElement.sendKeys(password);

//		click login button
		WebElement logInButton = driver.findElement(By.tagName("button"));
		logInButton.click();

//		verifications:
		String expectedUrl = "https://the-internet.herokuapp.com/login";
		String actualUrl = driver.getCurrentUrl();

		Assert.assertEquals(actualUrl, expectedUrl, "Actual page URL is not the same a expected ");

//		successful login management 
		WebElement successMessage = driver.findElement(By.cssSelector("div#flash"));

		String actualMessage = successMessage.getText();
		Assert.assertTrue(actualMessage.contains(expectedMessage),
				"Actual message does not contain expected message. \nActual Message:  " + actualMessage
						+ "\n Expected message: " + expectedMessage);

	}

}
