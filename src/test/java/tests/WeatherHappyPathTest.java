package tests;

import io.qameta.allure.Step;
import kdt.keywords.WeatherKeywords;
import models.GetWeatherResponse;
import org.hamcrest.core.AnyOf;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static storage.Storage.whatIsTheObject;

public class WeatherHappyPathTest extends BaseTest {

    private static final String CITY_NAME = "Kiev";
    private static final String COUNTRY = "UA";
    private static final String ID = "703448";
    private static final String LON = "30.52";
    private static final String LAT = "50.43";

    @DataProvider
    public Object[][] dataForTestGetCurrentWeather() {
        return new Object[][] {
                {byCityName()},
                {byCityNameAndCountry()},
                {byId()},
                {byCoords()},
                {byZipCode()},
        };
    }

    @Test(dataProvider = "dataForTestGetCurrentWeather")
    public void testGetCurrentWeather(Map<String, String> query) {
        keywordManager
                .runWithQueryParameters(query,
                        () -> keywordManager
                                    .weather().getCurrentWeather());
        currentWeatherCityNameShouldBe(anyOf(equalTo(CITY_NAME), equalTo("Misto Kyyiv"), equalTo("Kyiv")));
        currentWeatherCountryShouldBe(COUNTRY);
        currentWeatherIdShouldBe(anyOf(equalTo(Long.valueOf(ID)), equalTo(Long.valueOf("400013233")), equalTo(Long.valueOf("703447"))));
        currentWeatherCoordsShouldBe(byCoords());
    }

    @Test(dataProvider = "dataForTestGetCurrentWeather")
    public void testGetWeatherForecast(Map<String, String> query) {
        keywordManager
                .runWithQueryParameters(query,
                        () -> keywordManager
                                .weather().getWeatherForecast());

        forecastWeatherCityNameShouldBe(anyOf(equalTo(CITY_NAME), equalTo("Misto Kyyiv"), equalTo("Kyiv")));
        forecastWeatherCountryShouldBe(whatIsTheObject(WeatherKeywords.WEATHER_RESPONSE, GetWeatherResponse.class).getCity().getCountry());
        forecastWeatherIdShouldBe(anyOf(equalTo(Long.valueOf(ID)), equalTo(Long.valueOf("400013233")), equalTo(Long.valueOf("703447"))));
        forecastWeatherCoordsShouldBe(byCoords());
    }

    @Step
    public void currentWeatherCityNameShouldBe(AnyOf expected) {
        String actual = whatIsTheObject(WeatherKeywords.WEATHER_RESPONSE, GetWeatherResponse.class).getName();
        assertThat(actual, expected);
    }

    @Step
    public void currentWeatherCountryShouldBe(String expected) {
        String actual = whatIsTheObject(WeatherKeywords.WEATHER_RESPONSE, GetWeatherResponse.class).getSys().getCountry();
        assertThat(actual, equalTo(expected));
    }

    @Step
    public void forecastWeatherCityNameShouldBe(AnyOf expected) {
        String actual = whatIsTheObject(WeatherKeywords.WEATHER_RESPONSE, GetWeatherResponse.class).getCity().getName();
        assertThat(actual, expected);
    }

    @Step
    public void forecastWeatherCountryShouldBe(String expected) {
        String actual = whatIsTheObject(WeatherKeywords.WEATHER_RESPONSE, GetWeatherResponse.class).getCity().getCountry();
        assertThat(actual, equalTo(expected));
    }

    @Step
    public void currentWeatherIdShouldBe(AnyOf expected) {
        Long actual = whatIsTheObject(WeatherKeywords.WEATHER_RESPONSE, GetWeatherResponse.class).getId();
        assertThat(actual, expected);
    }

    @Step
    public void forecastWeatherIdShouldBe(AnyOf expected) {
        Long actual = whatIsTheObject(WeatherKeywords.WEATHER_RESPONSE, GetWeatherResponse.class).getCity().getId();
        assertThat(actual, expected);
    }

    @Step
    public void currentWeatherCoordsShouldBe(Map<String, String> expected) {
        GetWeatherResponse currentWeatherResponse = whatIsTheObject(WeatherKeywords.WEATHER_RESPONSE,
                GetWeatherResponse.class);
        assertThat(currentWeatherResponse.getCoord().getLat(), closeTo(Double.valueOf(expected.get("lat")), 0.2));
        assertThat(currentWeatherResponse.getCoord().getLon(), equalTo(Double.valueOf(expected.get("lon"))));
    }

    @Step
    public void forecastWeatherCoordsShouldBe(Map<String, String> expected) {
        GetWeatherResponse currentWeatherResponse = whatIsTheObject(WeatherKeywords.WEATHER_RESPONSE,
                GetWeatherResponse.class);
        assertThat(currentWeatherResponse.getCity().getCoord().getLat(), closeTo(Double.valueOf(expected.get("lat")), 0.2));
        assertThat(currentWeatherResponse.getCity().getCoord().getLon(), closeTo(Double.valueOf(expected.get("lon")), 0.2));
    }

    private Map<String, String> byCityName() {
        final Map<String, String> query = new HashMap<>();
        query.put("q", CITY_NAME);
        return query;
    }

    private Map<String, String> byCityNameAndCountry() {
        final Map<String, String> query = new HashMap<>();
        query.put("q", CITY_NAME + "," + COUNTRY);
        return query;
    }

    private Map<String, String> byId() {
        final Map<String, String> query = new HashMap<>();
        query.put("id", ID);
        return query;
    }

    private Map<String, String> byCoords() {
        final Map<String, String> query = new HashMap<>();
        query.put("lon", LON);
        query.put("lat", LAT);
        return query;
    }

    private Map<String, String> byZipCode() {
        final Map<String, String> query = new HashMap<>();
        query.put("zip", "02068" + "," + COUNTRY);
        return query;
    }
}