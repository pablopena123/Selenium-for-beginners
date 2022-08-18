package com.herokuapp.theinternet.pages;

import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePageObject {

	protected WebDriver driver;
	protected Logger log;
	protected WebDriverWait wait;
	
	
	public void sleep(long m) {
		try {
			Thread.sleep(m);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public BasePageObject(WebDriver driver, Logger log) {

		this.driver = driver;
		this.log = log;

		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	/** Get the text of a locator */
	public String text(By locator) {
		return find(locator).getText();

	}

	/** Open page with given URL */
	protected void openUrl(String url) {
		driver.get(url);

	}

	/** Find element using locator */
	protected WebElement find(By locator) {
		return driver.findElement(locator);
	}

	/** Click on element with given locator when its visible */
	protected void click(By locator) {
		waitForVisibilityOf(locator, 5);
		wait.until(ExpectedConditions.elementToBeClickable(locator));
		find(locator).click();
	}

	/** Type given text into element with given locator */
	protected void type(String text, By locator) {
		waitForVisibilityOf(locator, 5);
		wait.until(ExpectedConditions.elementToBeClickable(locator));
		find(locator).sendKeys(text);
	}

	/** Get URL of current page from browser */
	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	/** Get title of current page */
	public String getCurrentPageTitle() {
		return driver.getTitle();
	}
	
	/** check spinning wheel is no longer showing */
	public void waitForSpinnerInvisbility() {
		WebElement spinner = driver.findElement(By.id("resultLoading"));
		wait.until(ExpectedConditions.invisibilityOf(spinner));
	}
	
	/**  check processing loading box is no longer showing */
	public void waitForLoadingBoxInvisibility() {
		WebElement processing = driver.findElement(By.id("usersList_processing"));
		wait.until(ExpectedConditions.invisibilityOf(processing));	
	}
	
	/**
	 * Wait for specific ExpectedCondition for the given amount of time in seconds
	 */
	private void waitFor(ExpectedCondition<WebElement> condition, Integer timeOutInSeconds) {
		timeOutInSeconds = timeOutInSeconds != null ? timeOutInSeconds : 30;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(condition);
	}

	/**
	 * Wait for given number of seconds for element with given locator to be visible
	 * on the page
	 */
	protected void waitForVisibilityOf(By locator, Integer... timeOutInSeconds) {
		int attempts = 0;
		while (attempts < 2) {
			try {
				waitFor(ExpectedConditions.visibilityOfElementLocated(locator),
						(timeOutInSeconds.length > 0 ? timeOutInSeconds[0] : null));
				break;
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
		}
	}

	protected void waitForTextToBePresentIn(By locator, String text) {
		try {
			wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
			click(locator);
		} catch (TimeoutException exception) {
			log.info("Exception catched " + exception.getMessage());
			sleep(3000);
		}
	}
	
	/** Wait for alert present and then switch to it */
	protected Alert switchToAlert() {
		//WebDriverWait wait = new WebDriverWait(driver, 5);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.alertIsPresent());
		return driver.switchTo().alert();
	}
	
	public void switchToWindowWithTitle(String expectedTitle) {
		// Switching to new window
		String firstWindow = driver.getWindowHandle();

		Set<String> allWindows = driver.getWindowHandles();
		Iterator<String> windowsIterator = allWindows.iterator();

		while (windowsIterator.hasNext()) {
			String windowHandle = windowsIterator.next().toString();
			if (!windowHandle.equals(firstWindow)) {
				driver.switchTo().window(windowHandle);
				if (getCurrentPageTitle().equals(expectedTitle)) {
					break;
				}
			}
		}
	}
	
	/** Switch to iFrame using it's locator */
	protected void switchToFrame(By frameLocator) {
		driver.switchTo().frame(find(frameLocator));
	}
	
	/** Press Key on locator */
	protected void pressKey(By locator, Keys key) {
		find(locator).sendKeys(key);
	}

	/** Press Key using Actions class */
	public void pressKeyWithActions(Keys key) {
		log.info("Pressing " + key.name() + " using Actions class");
		Actions action = new Actions(driver);
		action.sendKeys(key).build().perform();
	}
	
	/** Perform scroll to the bottom */
public void scrollToBottom() {
	log.info("Scrolling to the bottom of the page");
	JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
	jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
}
	
	
}
