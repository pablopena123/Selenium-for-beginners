package com.accesshub.qa.stepdefinitions;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.herokuapp.theinternet.base.BaseTest;
import com.herokuapp.theinternet.base.BrowserDriverFactory;
import com.herokuapp.theinternet.pages.AdminMenuPage;
import com.herokuapp.theinternet.pages.LoginPage;
import com.herokuapp.theinternet.pages.PendingApprovalPage;
import com.herokuapp.theinternet.pages.SecureAreaPage;
import com.herokuapp.theinternet.pages.UserRequestPage;
import com.herokuapp.theinternet.pages.UserRequestSubmittedPage;
import com.herokuapp.theinternet.pages.UsersPage;
import com.herokuapp.theinternet.pages.WelcomePage;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Stepdefinition extends BaseTest {

	Logger log = (Logger) LogManager.getLogger(Stepdefinition.class);
	BrowserDriverFactory factory = new BrowserDriverFactory("chrome", log);

	protected WebDriver driver = factory.createDriver();

	protected WebDriverWait wait;

	WelcomePage welcomePage = new WelcomePage(driver, log);
	SecureAreaPage secureAreaPage = new SecureAreaPage(driver, log);
	UserRequestSubmittedPage userRequestSubmittedPage = new UserRequestSubmittedPage(driver, log);
	UserRequestPage userRequestPage = new UserRequestPage(driver, log);
	PendingApprovalPage pendingApprovalPage = new PendingApprovalPage(driver, log);

	@Before
	public void before() {
	}

	@After
	public void after(Scenario scenario) {
// 		Close browser
		driver.quit();
	}

	@Given("^Enter the url$")
	public void someStep() throws Throwable {
		System.out.println("execute someStep");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// open test page

		welcomePage.openPage();
		welcomePage.maximizePage();

	}

	@When("Login to the ACCESSHUB application with username and password")
	public void login_to_the_accesshub_application_with_username_and_password() throws Throwable {
		System.out.println("execute thisIsExpected");

		LoginPage loginPage = welcomePage.clickFormAuthenticationLink();
		loginPage.logIn("pablo.pena@ibm.com", "Mustang$helbyGT500");

		/** Verifications */

		Assert.assertEquals(secureAreaPage.getCurrentUrl(), secureAreaPage.getPageUrl());

	}

	@Then("User clicks on create user request module")
	public void user_clicks_on_identity_repository() {

		secureAreaPage.enterCreateUserRequest();
		userRequestPage.waitForSpinnerInvisbility();

	}

	@Then("Enter the CNUM {string} first name {string} the last name {string} work email {string} and manager CNUM {string} and submit the request")
	public void enter_the_cnum_first_name_the_last_name_and_work_email_and_submit_the_request(String cnum,
			String firstName, String lastName, String workEmail, String managerCnum) throws Throwable {

		userRequestPage.completeForm(cnum, firstName, lastName, workEmail);

		userRequestPage.waitForSpinnerInvisbility();

		userRequestPage.managerCloudIdNo(managerCnum);

		// Submit Button
		userRequestPage.waitForSpinnerInvisbility();

		userRequestPage.clickSubmitButton();
		// verifications:
		String expectedUrl = userRequestSubmittedPage.getPageUrl();

		String actualUrl = userRequestSubmittedPage.getCurrentUrl();
		Assert.assertEquals(actualUrl, expectedUrl, "Actual page URL is not the same a expected ");

		// successful login management

		String expectedMessage = userRequestSubmittedPage.successMessage();

		String actualMessage = userRequestSubmittedPage.getMessage();

		Assert.assertTrue(actualMessage.contains(expectedMessage),
				"Actual message does not contain expected message. \nActual Message:  " + actualMessage
						+ "\n Expected message: " + expectedMessage);

		UserRequestSubmittedPage.setVariableRequestNumber(userRequestSubmittedPage.getRequestNumber());
		UserRequestSubmittedPage.setVariableUsernameRequest(userRequestSubmittedPage.getRequestNumber());

		log.info("Request number set is " + UserRequestSubmittedPage.getVariableRequestNumber());
		log.info("Username set is " + UserRequestSubmittedPage.getVariableUsernameRequest());

	}

	@Then("Approve the request {int} times")
	public void approve_the_request_times(int timesToApproveRequest) throws Throwable {

		for (int i = 1; i <= timesToApproveRequest; i++) {

			secureAreaPage.goHome();

			Assert.assertTrue(driver.findElement(secureAreaPage.pendingApprovalModulePath()).isDisplayed(),
					"Create user button is not visible");

			secureAreaPage.goToPendingApprovalModule();

			Assert.assertTrue(
					driver.findElement(pendingApprovalPage
							.actualRequestNumber(UserRequestSubmittedPage.getVariableRequestNumber())).isDisplayed(),
					"Request number is not visible");

			log.info(UserRequestSubmittedPage.getVariableRequestNumber());

			pendingApprovalPage.searchBar(UserRequestSubmittedPage.getVariableRequestNumber());

			pendingApprovalPage.hoverOver();

			pendingApprovalPage.seeDetails();

			pendingApprovalPage.approveRequest();
		}

		Thread.sleep(1000);
	}

	@Then("Verify user has been created correctly")
	public void verify_user_has_been_created_correctly() throws Throwable {

		log.info("Verify user exists after approving the request ");

		AdminMenuPage adminMenuPage = secureAreaPage.adminButton();

		adminMenuPage.openLeftBarModule();

		UsersPage usersPage = adminMenuPage.clickUsers();

		usersPage.waitForLoadingBoxInvisibility();

		/* verifications: */
		String expectedUsersUrl = UsersPage.getPageUrl();

		String actualUsersUrl = driver.getCurrentUrl();

		Assert.assertEquals(actualUsersUrl, expectedUsersUrl, "Actual page URL is not the same a expected ");

		usersPage.searchBar(UserRequestSubmittedPage.getVariableRequestNumber());

		usersPage.waitForLoadingBoxInvisibility();

		Assert.assertTrue(driver.findElement(usersPage.searchRequestNumberPath(UserRequestSubmittedPage.getVariableRequestNumber())).isDisplayed(),
				"Request number is not visible");

		Thread.sleep(3000);
	}

	@Then("^Logout the application$")
	public void logout_the_application() throws InterruptedException {

	}

}
