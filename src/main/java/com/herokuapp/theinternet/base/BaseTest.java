package com.herokuapp.theinternet.base;

import java.time.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import com.herokuapp.theinternet.pages.LoginPage;
import com.herokuapp.theinternet.pages.SecureAreaPage;
import com.herokuapp.theinternet.pages.WelcomePage;

public class BaseTest {

	protected WebDriver driver;
	protected WebDriverWait wait;
	protected Logger log;

	@Parameters({ "browser" })
//	@BeforeMethod(alwaysRun = true)
	public void setUp(@Optional("chrome") String browser, ITestContext ctx) {
		String testName = ctx.getCurrentXmlTest().getName();
		log = LogManager.getLogger(testName);

		BrowserDriverFactory factory = new BrowserDriverFactory(browser, log);
		driver = factory.createDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		//open test page 	
		WelcomePage welcomePage = new WelcomePage(driver, log);
		welcomePage.openPage();
		welcomePage.maximizePage();

		LoginPage loginPage = welcomePage.clickFormAuthenticationLink();
		/*
		 * LoginPage loginPage = new LoginPage(driver, log);
		 * loginPage.logIn("pablo.pena@ibm.com", "Mustang$helbyGT350");
		 */

		SecureAreaPage secureAreaPage = loginPage.logIn("pablo.pena@ibm.com", "Mustang$helbyGT500");

		/** Verifications */

		// New page url is expected
		Assert.assertEquals(secureAreaPage.getCurrentUrl(), secureAreaPage.getPageUrl());

		/*
		 * String url =
		 * "https://ecm-test.accesshub.cloud.ibm.com/ECMv6/request/requestHome";
		 * driver.get(url); log.info("Page is open");
		 */

		/*
		 * 
		 * 
		 * // enter username WebElement usernameElement =
		 * wait.until(ExpectedConditions.elementToBeClickable(By.id("user-name-input")))
		 * ; usernameElement.sendKeys("pablo.pena@ibm.com");
		 * 
		 * // enter password WebElement passwordElement =
		 * wait.until(ExpectedConditions.elementToBeClickable(By.id("password-input")));
		 * passwordElement.sendKeys("Mustang$helbyGT350");
		 * 
		 * // click login button WebElement logInButton =
		 * driver.findElement(By.id("login-button")); logInButton.click();
		 * 
		 * // verifications:
		 * 
		 * String expectedUrl =
		 * "https://ecm-test.accesshub.cloud.ibm.com/ECMv6/request/requestHome"; String
		 * actualUrl = driver.getCurrentUrl();
		 * 
		 * wait.until(ExpectedConditions.urlToBe(expectedUrl));
		 * Assert.assertEquals(actualUrl, expectedUrl,
		 * "Actual page URL is not the same a expected ");
		 */

		log.info("correct URL is open");

	}

//	@AfterMethod(alwaysRun = true)
	public void tearDown() {

// 		Close browser
		log.info("Close driver");
		driver.quit();

	}
}
