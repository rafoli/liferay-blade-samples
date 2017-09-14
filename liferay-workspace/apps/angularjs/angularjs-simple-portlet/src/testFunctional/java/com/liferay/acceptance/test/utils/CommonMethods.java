package com.liferay.acceptance.test.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.liferay.acceptance.test.setup.Run;
import com.liferay.gs.testFramework.WaitUtils;

public class CommonMethods {

	private final By addButtonLocator = By.xpath(
			".//*[@id='_com_liferay_product_navigation_control_menu_web_portlet_ProductNavigationControlMenuPortlet_addToggleId']");
	private final By applicationHeadingLocator = By.xpath(
			".//*[@id='_com_liferay_product_navigation_control_menu_web_portlet_ProductNavigationControlMenuPortlet_addApplicationHeading']");
	private final By searchApplicationLocator = By.xpath(
			".//*[@id='_com_liferay_product_navigation_control_menu_web_portlet_ProductNavigationControlMenuPortlet_searchApplication']");

	public void waitElement(By locator) {
		WaitUtils.getWaitDriver().until(ExpectedConditions.visibilityOfElementLocated(locator));
		WaitUtils.getWaitDriver().until(ExpectedConditions.elementToBeClickable(locator));
	}

	public void input(By locator, String text) {
		waitElement(locator);
		getWebDriver().findElement(locator).clear();
		getWebDriver().findElement(locator).sendKeys(text);
	}

	public void addPortletOnScreen(String portletName, String column, int times) {
		clickOnAddButton();
		clickOnApplicationCategory();
		for (int i = 0; i < times; i++) {
			searchForPortletByName(portletName);
			dragAndDropPortletToColumn(portletName, column);
		}
	}

	private void dragAndDropPortletToColumn(String portletName, String column) {
		WaitUtils.waitMediumTime();

		By searchApplicationResultLocator = By
				.xpath(".//*[contains(@id, '_com_liferay_product_navigation_control_menu_web_portlet_ProductNavigationControlMenuPortlet_portletCategory')]//*[contains (text(), '"
						+ portletName + "')]");

		By columnLocator = By.xpath(".//*[@id='" + column + "']");

		WaitUtils.getWaitDriver().until(ExpectedConditions.visibilityOfElementLocated(searchApplicationResultLocator));
		WaitUtils.getWaitDriver().until(ExpectedConditions.elementToBeClickable(searchApplicationResultLocator));
		WebElement element = getWebDriver().findElement(searchApplicationResultLocator);
		WebElement target = getWebDriver().findElement(columnLocator);
		(new Actions(getWebDriver())).dragAndDrop(element, target).perform();
		WaitUtils.waitMediumTime();
	}


	private void searchForPortletByName(String portletName) {
		WaitUtils.getWaitDriver().until(ExpectedConditions.visibilityOfElementLocated(searchApplicationLocator));
		WaitUtils.getWaitDriver().until(ExpectedConditions.elementToBeClickable(searchApplicationLocator));
		getWebDriver().findElement(searchApplicationLocator).clear();
		getWebDriver().findElement(searchApplicationLocator).sendKeys(portletName);
	}

	private void clickOnAddButton() {
		WaitUtils.getWaitDriver().until(ExpectedConditions.visibilityOfElementLocated(addButtonLocator));
		WaitUtils.getWaitDriver().until(ExpectedConditions.elementToBeClickable(addButtonLocator));
		getWebDriver().findElement(addButtonLocator).click();
	}

	private void clickOnApplicationCategory() {
		WaitUtils.getWaitDriver().until(ExpectedConditions.visibilityOfElementLocated(applicationHeadingLocator));
		WaitUtils.getWaitDriver().until(ExpectedConditions.elementToBeClickable(applicationHeadingLocator));

		By portletContentCategories = By.cssSelector(".add-content-menu .lfr-content-category");
		boolean isApplicationCategoriesDisplayed = getWebDriver().findElement(portletContentCategories).isDisplayed();

		if (!isApplicationCategoriesDisplayed) {
			WaitUtils.getWaitDriver().until(ExpectedConditions.visibilityOfElementLocated(applicationHeadingLocator));
			WaitUtils.getWaitDriver().until(ExpectedConditions.elementToBeClickable(applicationHeadingLocator));
			getWebDriver().findElement(applicationHeadingLocator).click();
		}
	}

	private WebDriver getWebDriver() {
		return Run.webDriver;
	}
}
