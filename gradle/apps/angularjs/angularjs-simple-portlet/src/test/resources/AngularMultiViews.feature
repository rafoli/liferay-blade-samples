Feature: Angular example multiple web portlet views.

Scenario: Create AngularJS Simple Portlet Configuration
	Given I will login with the default user
	Then Insert "AngularJS Simple Portlet" on "column-1" 2 times
	And I reach the angular multiple views

Scenario Outline: shared var field replicate when user types in
	When I type in shared field <textOnSharedField>
	Then the shared field will have the value <textOnSharedField> replicated equally

	Examples:
		|textOnSharedField|
		|Liferay angular test|

Scenario Outline: shared var field replicate when user erase in
	When I type in shared field <textOnSharedField>
	And I erase the value of the shared field
	Then the shared field will have the blank value replicated equally

	Examples:
		|textOnSharedField|
		|Liferay angular test that will be erased|

Scenario Outline: non shared var field is not replicate when user types in
	When I type in non shared field1 <textOnNonSharedField>
	Then the non shared field1 will have the value different from non shared field2

	Examples:
		|textOnNonSharedField|
		|Liferay angular non shared field 1 test1|

Scenario Outline: non shared var field is not replicate when user types in
	When I type in non shared field2 <textOnNonSharedField>
	Then the non shared field1 will have the value different from non shared field2

	Examples:
		|textOnNonSharedField|
		|Liferay angular non shared field 2 test2|