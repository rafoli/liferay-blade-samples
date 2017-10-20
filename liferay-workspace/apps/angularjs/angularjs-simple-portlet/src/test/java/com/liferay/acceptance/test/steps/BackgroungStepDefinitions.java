package com.liferay.acceptance.test.steps;

import com.liferay.acceptance.test.page.object.AngularMultiViewPage;
import com.liferay.acceptance.test.utils.CommonMethods;

import cucumber.api.java.en.Given;

public class BackgroungStepDefinitions {

	CommonMethods commonMethods = new CommonMethods();

	@Given("^I reach the angular multiple views$")
	public void i_reach_the_angular_multiple_views() {
		AngularMultiViewPage.validateTitleInAngularViewPortlet();
	}

}
