package com.herokuapp.theinternet.commercialtests;

import static org.testng.Assert.assertTrue;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.herokuapp.theinternet.base.TestUtilities;

public class CommercialTests extends TestUtilities {

	@Test
	public void commercialTests() throws InterruptedException {

		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
		WebDriver driver = new ChromeDriver();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		driver.get(
				"https://web-chat.global.assistant.watson.appdomain.cloud/preview.html?region=us-east&integrationID=1892efae-8579-4027-be13-b184ae129070&serviceInstanceID=fe1c4506-a077-4713-b5f0-f8a0713586e6");

		driver.manage().window().maximize();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("WACLauncher__Button")));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("WACLauncher__Button")));

		driver.findElement(By.id("WACLauncher__Button")).click();

		Thread.sleep(1000);

		wait.until(ExpectedConditions.elementToBeClickable(By.id("WAC__inputContainer-TextArea--homeScreenModern")));
		driver.findElement(By.id("WAC__inputContainer-TextArea--homeScreenModern")).click();
		driver.findElement(By.id("WAC__inputContainer-TextArea--homeScreenModern")).sendKeys("Accesshub" + "\n");

		Thread.sleep(3000);

		String text = driver.findElement(By.xpath("//*[@id=\"WAC__message-1\"]/div[2]/div/div[3]")).getText();

		text = text.replace("\n", "");
		
		System.out.println("Text from chatbot is: " + text);

		String testCaseText = "AccessHubis an access management tool , which provides simplified and standardized access\n"
				+ "\n" + "management for all IBM digital assets and all IBMers.\n" + "\n"
				+ "AccessHub is providing the applications with all the necessary access management functionalities for managing users access, reporting, periodic access re-validation, reconciliation, out of band access deletion, QEV/leavers deletions and much more.\n"
				+ "\n" + "Find more information about AccessHub onAccessHub Community";

		testCaseText = testCaseText.replace("\n", "");

		System.out.println("Test case text is:    " + testCaseText);

		try {
			assertTrue(text.contains(testCaseText));
			System.out.println("\n Text is correct \n");
			
		} catch (AssertionError e) {
			System.out.println("\n Text is NOT correct \n");
			throw e;
		}
		Thread.sleep(3000);
		// Select Environment option for button
		wait.until(ExpectedConditions.elementToBeClickable(By.id("downshift-0-toggle-button")));
		driver.findElement(By.id("downshift-0-toggle-button")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("downshift-0-item-0")));
		driver.findElement(By.id("downshift-0-item-0")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"WAC__message-4\"]/div[2]/div/div[3]/div/p[1]")));
		text = driver.findElement(By.xpath("//*[@id=\"WAC__message-4\"]/div[2]/div/div[3]/div/p[1]")).getText();
		text = text.replace("\n", "");
		System.out.println("Text from chatbot is: " + text);
		
		String testCaseButtonText = "\n"
				+ "Information about AccessHub environments and availability can be found on AccessHub community,Environments & Availabilitypage";

		testCaseButtonText = testCaseButtonText.replace("\n", "");
		System.out.println("Test case text is:    " + testCaseButtonText);

		try {
			assertTrue(text.contains(testCaseButtonText));
			System.out.println("\n Text is correct \n");
			
		} catch (AssertionError e) {
			System.out.println("\n Text is NOT correct \n");
			throw e;
		}
		
		//Send phrase "What is accesshub"
		wait.until(ExpectedConditions.elementToBeClickable(By.id("WAC__inputContainer-TextArea")));
		driver.findElement(By.id("WAC__inputContainer-TextArea")).click();
		driver.findElement(By.id("WAC__inputContainer-TextArea")).sendKeys("What is AccessHub" + "\n");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"WAC__message-6\"]/div[2]/div/div[3]/div")));
		
		text = driver.findElement(By.xpath("//*[@id=\"WAC__message-6\"]/div[2]/div/div[3]/div")).getText();

		text = text.replace("\n", "");
		
		System.out.println("Text from chatbot is: " + text);
		System.out.println("Test case text is:    " + testCaseText);
		
		try {
			assertTrue(text.contains(testCaseText));
			System.out.println("\n Text is correct \n");
			
		} catch (AssertionError e) {
			System.out.println("\n Text is NOT correct \n");
			throw e;
		}

		Thread.sleep(5000);

		driver.close();
		driver.quit();

	}

}
