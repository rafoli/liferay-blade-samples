package com.liferay.acceptance.test.page.object;

import com.liferay.gs.testFramework.UtilsKeys;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by Haysa on 07/06/17.
 */
public class AngularMultiViewPage {

    // Locators

    static final String PORTLET_NAME = ".//*[contains(@id,'portlet_AngularJSSimplePortlet_INSTANCE_')]";

    static final By angularPageTitle = By.xpath(PORTLET_NAME+"/div/h2");

    static final By sharedVarField1 = By.xpath(PORTLET_NAME+"/div/div/div/ui-view/div/input[1]");
    static final By sharedVarField2 = By.xpath(PORTLET_NAME+"/div/div/div/ui-view/div/input[1]");

    static final By nonSharedField1 = By.xpath(PORTLET_NAME+"/div/div/div/ui-view/div/input[2]");
    static final By nonSharedField2 = By.xpath("");


    // Actions

    public static void typeInSharedVarField1(String value){

        UtilsKeys.DRIVER.findElement(sharedVarField1).sendKeys(value);
    }

    public static void validateSharedField2EqualToSharedField1(String value){

        WebElement element = UtilsKeys.DRIVER.findElement(sharedVarField2);
        String i = element.getAttribute("value");
        Assert.assertEquals(value, i);

    }

    public static void deleteValueInSharedField1() {

        UtilsKeys.DRIVER.findElement(sharedVarField1).clear();

        WebElement element = UtilsKeys.DRIVER.findElement(sharedVarField2);
        String i = element.getAttribute("value");
        Assert.assertEquals("", i);

    }

    public static void typeInNonSharedField1(String value){
        UtilsKeys.DRIVER.findElement(nonSharedField1).sendKeys(value);
    }

    public static void validateTitleInAngularView(){
        String text = UtilsKeys.DRIVER.findElement(angularPageTitle).getText();
        Assert.assertEquals("AngularJS Simple Portlet", text);
    }
}
