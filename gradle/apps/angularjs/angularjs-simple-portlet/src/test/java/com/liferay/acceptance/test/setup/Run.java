package com.liferay.acceptance.test.setup;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by Haysa on 07/06/17.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources"},
        format = {"pretty", "html:reports/acceptanceTestReport"},
        glue = {"com/liferay/acceptance/test/steps"})
public class Run {
}
