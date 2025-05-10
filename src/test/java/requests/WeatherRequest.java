package requests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import util.Constants;
import javax.net.ssl.*;

/**
 * Implements the interface with the API for weather-related requests
 */
public class WeatherRequest {

    private final int LATEST_WEATHER_OBJECT = 0;

    public WeatherRequest() throws Throwable {

    }

    /**
     * Performs an API call to get the weather for the supplied date and location
     * @param location The location to get the weather for
     * @param date The date to get the weather for in the string format "YYYY/MM/DD"
     * @return Returns a string with the latest weather state
     * @throws Throwable
     */
    public String requestWeatherByDateAndLocation (String date, String location) throws Throwable {

        String weatherRequestURL = Constants.WEATHER_REQUEST_URL + Constants.LOCATION_SEARCH_URL + location + "/" + date + "/";
        System.out.println("requestURL = " + weatherRequestURL);

        OkHttpClient client = trustAllSslClient(new OkHttpClient());
        Request request = new Request.Builder().url(weatherRequestURL).build();
        ResponseBody responseBody = client.newCall(request).execute().body();

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        WeatherRequestObject[] daysWeatherArray = gson.fromJson(responseBody.string(), WeatherRequestObject[].class);

        return daysWeatherArray[LATEST_WEATHER_OBJECT].getWeatherStateName();

    }

    /**
     * Performs an API call to get the WOEID from the response for the supplied location (assumes today's date)
     * @param location The location to get the weather for
     * @return Returns a string with the WOEID of the town or city
     * @throws Throwable
     */
    public String requestWeatherByLocation (String location) throws Throwable {

        String weatherRequestURL = Constants.WEATHER_REQUEST_URL + Constants.LOCATION_SEARCH_URL + location + "/";
        System.out.println("requestURL = " + weatherRequestURL);

        OkHttpClient client = trustAllSslClient(new OkHttpClient());
        Request request = new Request.Builder().url(weatherRequestURL).build();
        ResponseBody responseBody = client.newCall(request).execute().body();

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        WeatherRequestObject daysWeather = gson.fromJson(responseBody.string(), WeatherRequestObject.class);

        return daysWeather.getWOEID();

    }

    /**
     * The code below resolves an issue with the SSL certificate of metaweather.com:
     * I was getting a certificate error and was unable to add the certificate to cacerts.
     * This code is a workaround to trust all certificates.
     */
    private static final TrustManager[] trustAllCerts = new TrustManager[] {
            new X509TrustManager() {
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)  {
                }

                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)  {
                }

                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            }
    };

    private static final SSLContext trustAllSslContext;

    static {
        try {
            trustAllSslContext = SSLContext.getInstance("SSL");
            trustAllSslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static final SSLSocketFactory trustAllSslSocketFactory = trustAllSslContext.getSocketFactory();

    public static OkHttpClient trustAllSslClient(OkHttpClient client) {
        OkHttpClient.Builder builder = client.newBuilder();
        builder.sslSocketFactory(trustAllSslSocketFactory, (X509TrustManager)trustAllCerts[0]);
        builder.hostnameVerifier(new HostnameVerifier() {

            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        return builder.build();
    }

}
