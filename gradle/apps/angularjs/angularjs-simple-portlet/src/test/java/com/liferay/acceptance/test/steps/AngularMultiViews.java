package com.liferay.acceptance.test.steps;

import com.liferay.gs.testFramework.UtilsKeys;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import com.liferay.acceptance.test.page.object.AngularMultiViewPage;

/**
 * Created by Haysa on 07/06/17.
 */
public class AngularMultiViews {

    @Given("^I reach the angular multiple views$")
    public void i_reach_the_angular_multiple_views() {

        AngularMultiViewPage.validateTitleInAngularView();
    }

    @Given("^I type in shared field \"([^\"]*)\"$")
    public void i_type_in_shared_field(String value) {
        AngularMultiViewPage.typeInSharedVarField1(value);
    }

    @Then("^the shared field will be with value \"([^\"]*)\" replicated equally$")
    public void the_shared_fields_will_be_with_values_replicated_equally(String value) {
        AngularMultiViewPage.validateSharedField2EqualToSharedField1(value);
    }

    @And("^When I erase the value, the second field will be erased too$")
    public void whenIEraseTheValueTheSecondFieldWillBeErasedToo() throws InterruptedException{
        AngularMultiViewPage.deleteValueInSharedField1();
    }

    // Before and after hooks

    @Before
    public void beforeScenarios(){

        UtilsKeys.DRIVER.get(UtilsKeys.getUrlToHome());
    }

    @After
    public void afterScenarios(Scenario scenario){

        if (scenario.isFailed()) {
            byte[] screenshot = ((TakesScreenshot) UtilsKeys.DRIVER).getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenshot, "image/png");
        }
    }

}
