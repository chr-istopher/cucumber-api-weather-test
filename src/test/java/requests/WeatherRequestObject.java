package requests;

/**
 * This maps the JSON returned from metaweather.com to a Java object
 */
public class WeatherRequestObject {

    private String id;
    private String applicable_date;
    private String weather_state_name;
    private String weather_state_abbr;
    private String wind_speed;
    private String wind_direction;
    private String wind_direction_compass;
    private String min_temp;
    private String max_temp;
    private String the_temp;
    private String air_pressure;
    private String humidity;
    private String visibility;
    private String predictability;

    private String woeid;

    public String toString() {
        return "The id is " + this.id + " the weather is " + weather_state_name + "";
    }

    public String getWeatherStateName() {

        return this.weather_state_name;

    }

    public String getWOEID() {

        return this.woeid;

    }

}
