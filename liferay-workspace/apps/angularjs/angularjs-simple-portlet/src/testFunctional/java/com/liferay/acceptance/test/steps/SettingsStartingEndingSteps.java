package com.liferay.acceptance.test.steps;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.liferay.acceptance.test.setup.Run;
import com.liferay.gs.testFramework.UtilsKeys;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class SettingsStartingEndingSteps {

	@Before
	public void beforeScenario() {
		getWebDriver().get(UtilsKeys.getUrlToHome());
		getWebDriver().manage().window().maximize();
		getWebDriver().manage().timeouts().implicitlyWait(UtilsKeys.getTimeOut(), TimeUnit.SECONDS);
	}


	@After
	public void afterScenario(Scenario scenario) {
		if (scenario.isFailed()) {
			byte[] screenshot = ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES);
			scenario.embed(screenshot, "image/png");
		}

		getWebDriver().navigate().to(UtilsKeys.getUrlToHome() + UtilsKeys.getLinkToLogOut());
	}

	private WebDriver getWebDriver() {
		return Run.webDriver;
	}

}