package com.liferay.acceptance.test.setup;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import cucumber.api.CucumberOptions;

/**
 * Created by Haysa on 07/06/17.
 */
@RunAsClient
@RunWith(Arquillian.class)
@CucumberOptions(
        features = {"src/test/resources"},
        format = {"pretty", "html:reports/acceptanceTestReport"},
        glue = {"com/liferay/acceptance/test/steps"})
public class Run {

	@Drone
	public static WebDriver webDriver;
}
