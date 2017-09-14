package com.liferay.acceptance.test.steps;

import com.liferay.acceptance.test.page.object.AngularMultiViewPage;
import com.liferay.acceptance.test.utils.CommonMethods;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Created by Haysa on 07/06/17.
 */
public class AngularMultiViews {

	CommonMethods commonMethods = new CommonMethods();

	@Given("^I type in shared field (-?[^\"]*)$")
	public void i_type_in_shared_field(String value) {
		AngularMultiViewPage.typeInSharedVarField1(value);
	}

	@When("^I type in non shared field1 (-?[^\"]*)$")
	public void i_type_in_non_shared_field1(String value) {
		AngularMultiViewPage.typeInNonSharedField1(value);
	}

	@When("^I type in non shared field2 (-?[^\"]*)$")
	public void i_type_in_non_shared_field2(String value) {
		AngularMultiViewPage.typeInNonSharedField2(value);
	}

	@Then("^the shared field will have the value (-?[^\"]*) replicated equally$")
	public void the_shared_fields_will_have_the_values_replicated_equally(String value) {
		AngularMultiViewPage.validateSharedField2EqualToSharedField1(value);
	}

	@Then("^the shared field will have the blank value replicated equally$")
	public void the_shared_fields_will_have_the_blank_value_replicated_equally() {
		AngularMultiViewPage.validateSharedField2EqualToSharedField1("");
	}

	@Then("^the non shared field1 will have the value different from non shared field2$")
	public void the_non_shared_field1_will_have_the_value_different_from_non_shared_field2() {
		AngularMultiViewPage.validateNonSharedFieldsIsNotEqual();
	}

	@And("^I erase the value of the shared field$")
	public void iEraseTheValueTheSharedField() {
		AngularMultiViewPage.deleteValueInSharedField1();
	}
}
