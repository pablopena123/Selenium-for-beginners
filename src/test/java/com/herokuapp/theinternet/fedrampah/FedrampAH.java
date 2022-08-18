package com.herokuapp.theinternet.fedrampah;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.herokuapp.theinternet.base.TestUtilities;
import com.herokuapp.theinternet.pages.AdminMenuPage;
import com.herokuapp.theinternet.pages.PendingApprovalPage;
import com.herokuapp.theinternet.pages.SecureAreaPage;
import com.herokuapp.theinternet.pages.UserRequestPage;
import com.herokuapp.theinternet.pages.UserRequestSubmittedPage;
import com.herokuapp.theinternet.pages.UsersPage;

public class FedrampAH extends TestUtilities {

	@Parameters({ "cnum", "firstName", "lastName", "workEmail", "managerCnum", "timesToApproveRequest" })
	@Test
	public void LoginPage(String cnum, String firstName, String lastName, String workEmail, String managerCnum,
			int timesToApproveRequest) {

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		SecureAreaPage secureAreaPage = new SecureAreaPage(driver, log);

		UserRequestPage userRequestPage = secureAreaPage.enterCreateUserRequest();

		// check spinning wheel is no longer showing
		userRequestPage.waitForSpinnerInvisbility();

		userRequestPage.completeForm(cnum, firstName, lastName, workEmail);

		userRequestPage.waitForSpinnerInvisbility();

		userRequestPage.managerCloudIdNo(managerCnum);

		// Submit Button
		userRequestPage.waitForSpinnerInvisbility();

		UserRequestSubmittedPage userRequestSubmittedPage = userRequestPage.clickSubmitButton();
		// verifications:
		String expectedUrl = userRequestSubmittedPage.getPageUrl();

		String actualUrl = userRequestSubmittedPage.getCurrentUrl();
		Assert.assertEquals(actualUrl, expectedUrl, "Actual page URL is not the same a expected ");

		//Assert.assertTrue(alertMessage.equals("I am a JS Alert"),	"Alert message is not expected. \nShould be 'I am a JS Alert', but it is '" + alertMessage + "'");
		
		// successful login management

		String expectedMessage = userRequestSubmittedPage.successMessage();

		String actualMessage = userRequestSubmittedPage.getMessage();

		Assert.assertTrue(actualMessage.contains(expectedMessage),
				"Actual message does not contain expected message. \nActual Message:  " + actualMessage
						+ "\n Expected message: " + expectedMessage);

		String requestNumber = userRequestSubmittedPage.getRequestNumber();

		String usernameRequest = userRequestSubmittedPage.getUsername();

		log.info(requestNumber);

		log.info(usernameRequest);

		sleep(3000);

		for (int i = 1; i <= timesToApproveRequest; i++) {

			secureAreaPage.goHome();

			Assert.assertTrue(driver.findElement(secureAreaPage.pendingApprovalModulePath()).isDisplayed(),
					"Create user button is not visible");

			PendingApprovalPage pendingApprovalPage = secureAreaPage.goToPendingApprovalModule();

			Assert.assertTrue(driver.findElement(pendingApprovalPage.actualRequestNumber(requestNumber)).isDisplayed(),
					"Request number is not visible");

			// log.info("THISSSSSSSS" +
			// pendingApprovalPage.actualRequestNumber(requestNumber));
			log.info(requestNumber);

			pendingApprovalPage.searchBar(requestNumber);

			pendingApprovalPage.hoverOver();

			pendingApprovalPage.seeDetails();

			pendingApprovalPage.approveRequest();
		}

		sleep(1000);

		log.info("Verify user exists after approving the request ");

		AdminMenuPage adminMenuPage = secureAreaPage.adminButton();

		adminMenuPage.openLeftBarModule();

		UsersPage usersPage = adminMenuPage.clickUsers();

		usersPage.waitForLoadingBoxInvisibility();

		/* verifications: */
		String expectedUsersUrl = UsersPage.getPageUrl();

		String actualUsersUrl = driver.getCurrentUrl();

		Assert.assertEquals(actualUsersUrl, expectedUsersUrl, "Actual page URL is not the same a expected ");

		usersPage.searchBar(usernameRequest);

		usersPage.waitForLoadingBoxInvisibility();

		Assert.assertTrue(driver.findElement(usersPage.searchRequestNumberPath(usernameRequest)).isDisplayed(),
				"Request number is not visible");

		sleep(3000);

	}

	@Parameters({ "appServerName" })
	@Test
	public void verifyCbtApp(String appServerName) {

		log.info("Starting test to verify values imported from CBT");

		SecureAreaPage secureAreaPage = new SecureAreaPage(driver, log);

		AdminMenuPage adminMenuPage = secureAreaPage.adminButton();

		adminMenuPage.useSearchBar(appServerName);

		Assert.assertTrue(driver.findElement(adminMenuPage.getActualAppServer(appServerName)).isDisplayed(), appServerName + " is not visible");
		
		adminMenuPage.clickAppServer(appServerName);
		
		sleep(2000);

		try {
			WebElement instanceButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("ui-id-2")));
			Assert.assertTrue(instanceButton.isDisplayed(), "instance button not visible");
			instanceButton.click();
		} catch (TimeoutException exception) {
			log.info("Exception catched " + exception.getMessage());
		}

		try {
			WebElement instance = wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"endPointTable\"]/tbody/tr/td[1]/a")));
			Assert.assertTrue(instance.isDisplayed(), "Create user button is not visible");
			instance.click();
		} catch (TimeoutException exception) {
			log.info("Exception catched " + exception.getMessage());
		}

		sleep(2000);

		try {
			WebElement otherAttributes = wait.until(ExpectedConditions.elementToBeClickable(By.id("ui-id-2")));
			Assert.assertTrue(otherAttributes.isDisplayed(), "other Attributes button is not visible");
			otherAttributes.click();
		} catch (TimeoutException exception) {
			log.info("Exception catched " + exception.getMessage());
		}

		sleep(2000);

		try {
			WebElement entitlementType = wait.until(ExpectedConditions.elementToBeClickable(By.id("ui-id-3")));
			Assert.assertTrue(entitlementType.isDisplayed(), "entitlement type button is not visible");
			entitlementType.click();
		} catch (TimeoutException exception) {
			log.info("Exception catched " + exception.getMessage());
		}

		sleep(2000);

		try {
			WebElement roleType = wait.until(ExpectedConditions.elementToBeClickable(By.id("ui-id-4")));
			Assert.assertTrue(roleType.isDisplayed(), "Role type button is not visible");
			roleType.click();
		} catch (TimeoutException exception) {
			log.info("Exception catched " + exception.getMessage());
		}

		sleep(2000);
	}
}
