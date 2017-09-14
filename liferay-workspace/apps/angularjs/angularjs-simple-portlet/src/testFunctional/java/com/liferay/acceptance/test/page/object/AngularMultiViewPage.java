package com.liferay.acceptance.test.page.object;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.liferay.acceptance.test.setup.Run;

/**
 * Created by Haysa on 07/06/17.
 */
public class AngularMultiViewPage {

	// Locators

	static final String PORTLET_NAME = ".//*[contains(@id,'portlet_AngularJSSimplePortlet_INSTANCE_')]";

	static final By angularPageTitle = By.xpath(PORTLET_NAME + "/div/h2");

	static List<WebElement> sharedVarField = null;

	static List<WebElement> nonSharedField = null;

	// Actions

	public static void typeInSharedVarField1(String value) {
		sharedVarField = getWebDriver().findElements(By.xpath(".//input[@ng-model='sharedModel']"));
		sharedVarField.get(0).sendKeys(value);
	}

	public static void validateSharedField2EqualToSharedField1(String value) {
		sharedVarField = getWebDriver().findElements(By.xpath(".//input[@ng-model='sharedModel']"));
		sharedVarField.get(0).getAttribute("value");
		Assert.assertEquals(value, sharedVarField.get(1).getAttribute("value").toString());
	}

	public static void deleteValueInSharedField1() {
		sharedVarField = getWebDriver().findElements(By.xpath(".//input[@ng-model='sharedModel']"));
		sharedVarField.get(0).clear();
		Assert.assertEquals("", sharedVarField.get(1).getAttribute("value"));
	}

	public static void validateTitleInAngularViewPortlet() {
		String text = getWebDriver().findElement(angularPageTitle).getText();
		Assert.assertEquals("AngularJS Simple Portlet", text);
	}

	public static void typeInNonSharedField1(String value) {
		nonSharedField = getWebDriver().findElements(By.xpath(".//input[@ng-model='nonSharedModel']"));
		nonSharedField.get(0).clear();
		nonSharedField.get(0).sendKeys(value);
	}

	public static void typeInNonSharedField2(String value) {
		nonSharedField = getWebDriver().findElements(By.xpath(".//input[@ng-model='nonSharedModel']"));
		nonSharedField.get(1).clear();
		nonSharedField.get(1).sendKeys(value);
	}

	public static void validateNonSharedFieldsIsNotEqual() {
		nonSharedField = getWebDriver().findElements(By.xpath(".//input[@ng-model='nonSharedModel']"));
		String nonSharedField1 = nonSharedField.get(0).getAttribute("value").toString();
		String nonSharedField2 = nonSharedField.get(1).getAttribute("value").toString();

		if (nonSharedField1.equalsIgnoreCase(nonSharedField2)) {
			Assert.assertFalse(false);
		} else {
			Assert.assertTrue(true);
		}
	}

	private static WebDriver getWebDriver() {
		return Run.webDriver;
	}
}
