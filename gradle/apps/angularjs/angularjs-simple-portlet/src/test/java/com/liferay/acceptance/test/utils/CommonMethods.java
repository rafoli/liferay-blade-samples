package com.liferay.acceptance.test.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.liferay.gs.testFramework.UtilsKeys;
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
		UtilsKeys.DRIVER.findElement(locator).clear();
		UtilsKeys.DRIVER.findElement(locator).sendKeys(text);
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
		WebElement element = UtilsKeys.DRIVER.findElement(searchApplicationResultLocator);
		WebElement target = UtilsKeys.DRIVER.findElement(columnLocator);
		(new Actions(UtilsKeys.DRIVER)).dragAndDrop(element, target).perform();
		WaitUtils.waitMediumTime();
	}

	private void searchForPortletByName(String portletName) {
		WaitUtils.getWaitDriver().until(ExpectedConditions.visibilityOfElementLocated(searchApplicationLocator));
		WaitUtils.getWaitDriver().until(ExpectedConditions.elementToBeClickable(searchApplicationLocator));
		UtilsKeys.DRIVER.findElement(searchApplicationLocator).clear();
		UtilsKeys.DRIVER.findElement(searchApplicationLocator).sendKeys(portletName);
	}

	private void clickOnAddButton() {
		WaitUtils.getWaitDriver().until(ExpectedConditions.visibilityOfElementLocated(addButtonLocator));
		WaitUtils.getWaitDriver().until(ExpectedConditions.elementToBeClickable(addButtonLocator));
		UtilsKeys.DRIVER.findElement(addButtonLocator).click();
	}

	private void clickOnApplicationCategory() {
		WaitUtils.getWaitDriver().until(ExpectedConditions.visibilityOfElementLocated(applicationHeadingLocator));
		WaitUtils.getWaitDriver().until(ExpectedConditions.elementToBeClickable(applicationHeadingLocator));

		By portletContentCategories = By.cssSelector(".add-content-menu .lfr-content-category");
		boolean isApplicationCategoriesDisplayed = UtilsKeys.DRIVER.findElement(portletContentCategories).isDisplayed();

		if (!isApplicationCategoriesDisplayed) {
			WaitUtils.getWaitDriver().until(ExpectedConditions.visibilityOfElementLocated(applicationHeadingLocator));
			WaitUtils.getWaitDriver().until(ExpectedConditions.elementToBeClickable(applicationHeadingLocator));
			UtilsKeys.DRIVER.findElement(applicationHeadingLocator).click();
		}
	}
}
