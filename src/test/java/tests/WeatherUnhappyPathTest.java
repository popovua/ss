package tests;

import com.google.common.collect.ImmutableMap;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import kdt.keywords.BaseKeyword;
import kdt.keywords.KeywordManager;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;
import java.util.function.Supplier;

import static org.hamcrest.Matchers.equalTo;
import static storage.Storage.whatIsTheObject;

public class WeatherUnhappyPathTest extends BaseTest {

    @DataProvider
    public Object[][] dataForTestingEndpoints() {
        return new Object[][] {
                {getCurrentWeatherEndpoint()},
                {getWeatherForecastEndpoint()}
        };
    }

    @Test(dataProvider = "dataForTestingEndpoints")
    public void testCityNotFoundError(Supplier<KeywordManager> supplier) {
        keywordManager
                .runWithError(HttpURLConnection.HTTP_NOT_FOUND,
                        () -> keywordManager
                                .runWithQueryParameters(ImmutableMap.of("q", "Error cityName"), supplier));
        verifyCityNotFoundError();
    }

    @Test(dataProvider = "dataForTestingEndpoints")
    public void testUnauthorizedError(Supplier<KeywordManager> supplier) {
        keywordManager
                .runWithError(HttpURLConnection.HTTP_UNAUTHORIZED, supplier);
        verifyUnauthorizedError();
    }

    @Test(dataProvider = "dataForTestingEndpoints")
    public void testNothingToGeocodeError(Supplier<KeywordManager> supplier) {
        keywordManager
                .runWithError(HttpURLConnection.HTTP_BAD_REQUEST,
                        () -> keywordManager
                                .runWithQueryParameters(ImmutableMap.of(), supplier));
        verifyNothingToGeocodeError();
    }

    @Step
    public void verifyCityNotFoundError() {
        Response response = whatIsTheObject(BaseKeyword.REST_ASSURED_RESPONSE, Response.class);
        response.then()
                .body("cod", equalTo(String.valueOf(HttpURLConnection.HTTP_NOT_FOUND))).and()
                .body("message", equalTo("city not found"));
    }

    @Step
    public void verifyUnauthorizedError() {
        Response response = whatIsTheObject(BaseKeyword.REST_ASSURED_RESPONSE, Response.class);
        response.then()
                .body("cod", equalTo(String.valueOf(HttpURLConnection.HTTP_UNAUTHORIZED))).and()
                .body("message", equalTo("Invalid API key. Please see http://openweathermap.org/faq#error401 for more info."));
    }

    @Step
    public void verifyNothingToGeocodeError() {
        Response response = whatIsTheObject(BaseKeyword.REST_ASSURED_RESPONSE, Response.class);
        response.then()
                .body("cod", equalTo(String.valueOf(HttpURLConnection.HTTP_BAD_REQUEST))).and()
                .body("message", equalTo("Nothing to geocode"));
    }

    private Supplier<KeywordManager> getCurrentWeatherEndpoint() {
        return () -> keywordManager
                .weather().getCurrentWeather();
    }

    private Supplier<KeywordManager> getWeatherForecastEndpoint() {
        return () -> keywordManager
                .weather().getWeatherForecast();
    }
}
