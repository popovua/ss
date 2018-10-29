package kdt.keywords;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.response.Response;
import utils.PropertiesController;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static storage.Storage.whatIsTheObject;

public class KeywordManager {

    private static KeywordManager instance = null;
    private WeatherKeywords weatherKeywords = new WeatherKeywords(this);

    private KeywordManager() {}

    public static KeywordManager getInstance() {
        if (instance == null) {
            instance = new KeywordManager();
        }
        return instance;
    }

    public WeatherKeywords weather() {
        return weatherKeywords;
    }

    @Step
    public KeywordManager runWithQueryParameters(Map<String, String> parameters, Supplier<KeywordManager> supplier) {
        final Map<String, String> params = new HashMap<>();
        params.put("appid", PropertiesController.getProperty("appid"));
        params.putAll(parameters);
        RestAssured.requestSpecification.queryParams(params);
        try {
            supplier.get();
        } finally {
            params.keySet().forEach(key -> ((RequestSpecificationImpl) RestAssured.requestSpecification).removeQueryParam(key));
        }
        return this;
    }

    @Step
    public KeywordManager runWithError(int statusCode, Supplier<KeywordManager> supplier) {
        try {
            supplier.get();
        }
        catch (AssertionError e) { }
        finally {
            Response response = whatIsTheObject(BaseKeyword.REST_ASSURED_RESPONSE, Response.class);
            response.then().statusCode(statusCode);
        }
        return this;
    }
}
