Feature:  Four Day Weather Forecast
  As a user based in a specific location
  I want to send a request to the Weather API
  So that I can see a four day forecast

  Scenario Outline: Test the four day forecast for a location
    When I request the weather forecast for "<Location>" for "<Date>"
    Then I should get the "<Forecast>"

    Scenarios:
      | Location  | Date          | Forecast    |
      | London    | Today         | Heavy Cloud |
      | London    | Tomorrow      | Showers     |
      | London    | Tomorrow + 1  | Clear       |
      | London    | Tomorrow + 2  | Light Cloud |

  Scenario Outline: Test the location method of the API
    When I request the weather forecast for "<Location>"
    Then I should get a forecast for today with the expected "<WOEID>"

    Scenarios:
      | Location  | WOEID    |
      | London    | 44418    |
      | Oxford    | 31278    |

    # Additional manual test cases :-
    # Test API response with invalid location
    # , different location gives diff response etc

  @manual
  Scenario: Test API response with a date in the past
    When I request the weather for a date in the past
    Then I should get a forecast for that date with known forecast results

  @manual
  Scenario: Test all API response fields for a forecast
    When I request the weather
    Then I should get a forecast where all fields are valid

  @manual
  Scenario: Test API response with invalid location
    When I request the weather for an invalid location
    Then I should get a HTTP error response

  @manual
  Scenario: Test API response with invalid date
    When I request the weather for an invalid date
    Then I should get a HTTP error response

  @manual
  Scenario: Test location search api
    When I search for a location by short name, name, latitude, longitufe
    Then I should get a valid woeid