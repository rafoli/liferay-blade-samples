package com.liferay.acceptance.test.steps;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.liferay.acceptance.test.page.object.LoginPage;
import com.liferay.acceptance.test.utils.CommonMethods;
import com.liferay.gs.testFramework.WaitUtils;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class CommonSteps {

	LoginPage loginPage = new LoginPage();
	CommonMethods commonMethods = new CommonMethods();

	private static final String email = "test@liferay.com";
	private static final String password = "test";
	private static final String usernameAcronym = "TT";

	private void login(String email, String password, String usernameAcronym) {
		loginPage.clickOnSignIn();
		loginPage.fillEmailAddressField(email);
		loginPage.fillPasswordField(password);
		loginPage.clickOnSignInOfTheModal();

		By usernameAcronymLocator = By
				.xpath(".//*[contains(@id, 'p_p_id_com_liferay_product_navigation_user_personal_bar_web_portlet_ProductNavigationUserPersonalBarPortlet_')]//*[contains(text(), '"
						+ usernameAcronym + "')]");

		WaitUtils.getWaitDriver().until(ExpectedConditions.visibilityOfElementLocated(usernameAcronymLocator));
	}

	@Given("^I will login with the default user$")
	public void i_will_login_with_the_default_user() {
		login(email, password, usernameAcronym);
	}

	@Then("^Insert \"([^\"]*)\" on \"([^\"]*)\" (\\d+) times$")
	public void insert_porlet_on_column_x_times(String portletName, String column, int times) {
		commonMethods.addPortletOnScreen(portletName, column, times);
	}

}
