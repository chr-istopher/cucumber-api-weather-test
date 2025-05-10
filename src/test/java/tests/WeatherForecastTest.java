package tests;

import requests.WeatherRequest;
import util.Constants;
import util.DateFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This class carries out the logic required to test the API — it separates the test logic from the interface.
 * The interface with the API is implemented in @WeatherRequest
 */
public class WeatherForecastTest {

    private WeatherRequest weatherRequest;
    private String testDate;
    private String testLocation;

    public WeatherForecastTest() throws Throwable {

        weatherRequest = new WeatherRequest();

    }

    /**
     *
     * Tests the API by supplying a location and date and returning the response
     *
     * @throws Throwable
     */
    public String testForecastByLocationAndDate () throws Throwable {

        return weatherRequest.requestWeatherByDateAndLocation(this.testDate, this.testLocation);

    }

    public String testLocation () throws Throwable {

        return weatherRequest.requestWeatherByLocation(this.testLocation);

    }

    /**
     *
     * Tests the API by supplying a Map of locations and dates and returning the responses as a Map
     *
     * @throws Throwable
     */
    public Map testForecastByLocationAndDates (Map <String, String> testData) throws Throwable {

        Map <String, String> testResults = new HashMap<String, String>();
        for (String testDate : testData.keySet()) {
            setTestDate(testDate);
            testResults.put(testDate, weatherRequest.requestWeatherByDateAndLocation(this.testDate, this.testLocation));
        }
        return testResults;

    }

    /**
     *
     * Sets the date for use in the test in the format "YYYY/MM/DD"
     *
     * @param testDate A String representing a date e.g. "Today", "Tomorrow", "Tomorrow + 1", "20190101"
     */
    public void setTestDate (String testDate) {

        int dayOffSet = 0;
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(new Date());

        if (testDate.equals(Constants.TODAY)) {

            this.testDate = DateFormatter.getAPIFormattedDate(currentDate.getTime());

        } else if (testDate.startsWith(Constants.TOMORROW)) {

            dayOffSet = getDayOffset(testDate);
            currentDate.add(Calendar.DATE, dayOffSet);
            this.testDate = DateFormatter.getAPIFormattedDate(currentDate.getTime());

        } else {

            this.testDate = testDate; // Date must be an absolute value

        }

    }

    /**
     *
     * Sets the test location as a WOEID from a city or town name
     *
     * @param testLocation The name of the town or city to test
     */
    public void setTestLocation (String testLocation) {

        this.testLocation = getWOEID(testLocation);

    }

    /**
     *
     * A simple lookup to convert a location String to a WOEID
     *
     * @param location The city or town name to look up
     * @return
     */
    private String getWOEID(String location) {

        String woeid = "";

        switch (location) {

            case "London": {

                woeid = Constants.LONDON;
                break;

            }

            case "Oxford": {

                woeid = Constants.OXFORD;
                break;

            }


            default: {

                woeid = Constants.LONDON;

            }

        }

        return woeid;

    }

    /**
     *
     * Calculates the number of additional days in strings such as "Tomorrow + 1"
     *
     * @param timePeriod A String such as "Tomorrow + 1", "Tomorrow + 2" etc.
     * @return
     */
    private int getDayOffset (String timePeriod) {

        int dayOffset = 0; // Today

        if (timePeriod.startsWith(Constants.TOMORROW)) {

            if (timePeriod.contains("+")) {

                String dateNoSpace = timePeriod.replaceAll("\\s","");
                int start = dateNoSpace.indexOf("+");
                String numberOfDays = dateNoSpace.substring(start + 1);
                dayOffset = Integer.parseInt(numberOfDays) + 1; // Number of days after tomorrow

            } else {

                dayOffset = 1; // Tomorrow

            }
        }

        return dayOffset;

    }

}
