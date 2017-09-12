Feature: Angular example multiple web portlet views.

#Background:
  #Given I sing up with credentials "test@liferay.com" "test"


  Scenario: shared var field replicate when user types in
    Given I reach the angular multiple views
    When I type in shared field "Liferay angular test"
    Then the shared field will be with value "Liferay angular test" replicated equally
    And When I erase the value, the second field will be erased too