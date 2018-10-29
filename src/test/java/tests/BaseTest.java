package tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import kdt.keywords.KeywordManager;
import log.restassured.filters.CustomRequestLoggingFilter;
import log.restassured.filters.CustomResponseLoggingFilter;
import log.restassured.filters.RestAssuredFilter;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import storage.Storage;
import storage.StorageContextHandler;
import utils.PropertiesController;

@Listeners(value = {StorageContextHandler.class})
public class BaseTest {

    protected static final Storage storage = Storage.getInstance();
    protected static final KeywordManager keywordManager = KeywordManager.getInstance();

    @BeforeSuite
    public void setUp() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .setBaseUri(PropertiesController.getProperty("baseUrl"))
                .setContentType("application/json")
                .addFilter(new CustomRequestLoggingFilter(LogDetail.ALL))
                .addFilter(new CustomResponseLoggingFilter(LogDetail.ALL))
                .addFilter(new RestAssuredFilter());
        RestAssured.requestSpecification = requestSpecBuilder.build();
    }
}