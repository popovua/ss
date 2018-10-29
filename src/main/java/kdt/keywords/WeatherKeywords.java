package kdt.keywords;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.GetWeatherResponse;

import java.net.HttpURLConnection;

import static io.restassured.RestAssured.given;
import static storage.Storage.rememberThe;

public class WeatherKeywords extends BaseKeyword {

    private static final String WEATHER_PATH = "/weather";
    private static final String FORECAST_PATH = "/forecast";

    public static final String WEATHER_RESPONSE = "currentWeatherResponse";

    public WeatherKeywords(KeywordManager keywordManager) {
        super(keywordManager);
    }

    @Step
    public KeywordManager getCurrentWeather() {
        Response response = given()
                .when()
                .get(WEATHER_PATH);
        rememberThe(REST_ASSURED_RESPONSE, response);
        response.then().statusCode(HttpURLConnection.HTTP_OK);
        rememberThe(WEATHER_RESPONSE, response.as(GetWeatherResponse.class));
        return keywordManager;
    }

    @Step
    public KeywordManager getWeatherForecast() {
        Response response = given()
                .when()
                .get(FORECAST_PATH);
        rememberThe(REST_ASSURED_RESPONSE, response);
        response.then().statusCode(HttpURLConnection.HTTP_OK);
        rememberThe(WEATHER_RESPONSE, response.as(GetWeatherResponse.class));
        return keywordManager;
    }

}
