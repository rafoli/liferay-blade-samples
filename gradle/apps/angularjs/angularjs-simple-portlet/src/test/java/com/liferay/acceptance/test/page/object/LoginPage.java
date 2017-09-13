package com.liferay.acceptance.test.page.object;

import org.openqa.selenium.By;

import com.liferay.acceptance.test.utils.CommonMethods;
import com.liferay.gs.testFramework.UtilsKeys;

public class LoginPage {

	CommonMethods commonMethods = new CommonMethods();

	private final By signInLocator = By.xpath(
			".//*[@id='p_p_id_com_liferay_product_navigation_user_personal_bar_web_portlet_ProductNavigationUserPersonalBarPortlet_']");
	private final By emailAddressLocator = By.xpath(".//*[@id='_com_liferay_login_web_portlet_LoginPortlet_login']");
	private final By passwordLocator = By.xpath(".//*[@id='_com_liferay_login_web_portlet_LoginPortlet_password']");
	private final By signInButtonOnModalLocator = By
			.xpath(".//*[contains(@id, '_com_liferay_login_web_portlet_LoginPortlet') and contains(@type, 'submit')]");

	public void fillEmailAddressField(String userLogin) {
		commonMethods.input(emailAddressLocator, userLogin);
	}

	public void fillPasswordField(String userPassword) {
		commonMethods.input(passwordLocator, userPassword);
	}

	public void clickOnSignIn() {
		commonMethods.waitElement(signInLocator);
		UtilsKeys.DRIVER.findElement(signInLocator).click();
	}

	public void clickOnSignInOfTheModal() {
		commonMethods.waitElement(signInButtonOnModalLocator);
		UtilsKeys.DRIVER.findElement(signInButtonOnModalLocator).click();
	}

}