package step_definitions;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.testng.Assert;
import tests.WeatherForecastTest;
import util.Constants;

public class WeatherStepDefs {

    private WeatherForecastTest test;

    /**
     *
     * Sets the location to @param testLocation and the date to TODAY
     *
     * @param testLocation The location to run the test against
     *
     * @throws Throwable
     */
    @When("^I request the weather forecast for \"([^\"]*)\"$")
    public void iRequestTheWeatherForecastForLocation(String testLocation) throws Throwable {
        // Create a test object
        test = new WeatherForecastTest();
        // Set the date to today in the format "YYYY/MM/DD"
        test.setTestDate(Constants.TODAY);
        // Set the location
        test.setTestLocation(testLocation);
    }

    /**
     *
     * Sets the location to @param testLocation and the date to @param testDate
     *
     * @param testLocation  The location to run the test against
     * @param testDate  The date to run the test against
     *
     * @throws Throwable
     */
    @When("^I request the weather forecast for \"([^\"]*)\" for \"([^\"]*)\"$")
    public void iRequestTheWeatherForecastForLocationAndDate(String testLocation, String testDate) throws Throwable {
        // Create a test object
        test = new WeatherForecastTest();
        // Set the date to today in the format "YYYY/MM/DD"
        test.setTestDate(testDate);
        // Set the location
        test.setTestLocation(testLocation);
    }

    /**
     *
     * Tests an expected forecast for a location and date against the actual forecast
     *
     * @param expectedForecast The expected weather forecast for that location and date
     * @throws Throwable
     */
    @Then("^I should get the \"([^\"]*)\"$")
    public void iShouldGetTheForecast(String expectedForecast) throws Throwable {
        // Validate response data
        String actualForecast = test.testForecastByLocationAndDate();
        Assert.assertEquals(actualForecast, expectedForecast);
    }

    /**
     *
     * Tests the forecast returned is for the correct location
     *
     * @param expectedWOEID The expected WOEID of the forecast
     * @throws Throwable
     */
    @Then("^I should get a forecast for today with the expected \"([^\"]*)\"$")
    public void iShouldGetAForecastForTodayWithTheExpectedWOEID(String expectedWOEID) throws Throwable {
        Assert.assertEquals(expectedWOEID, test.testLocation());
    }

}
