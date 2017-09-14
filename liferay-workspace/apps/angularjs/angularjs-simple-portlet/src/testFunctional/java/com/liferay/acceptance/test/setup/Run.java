package com.liferay.acceptance.test.setup;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import com.liferay.gs.testFramework.UtilsKeys;

/**
 * Created by Haysa on 07/06/17.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/testFunctional/resources",
        glue = {"com/liferay/acceptance/test/steps"},
        plugin = {"pretty", "html:reports/cucumber"},
        tags = {})
public class Run {

        public static WebDriver webDriver = UtilsKeys.DRIVER;
}
