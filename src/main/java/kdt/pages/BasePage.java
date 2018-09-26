package kdt.pages;

import annotations.DynamicPage;
import annotations.URI;
import com.codeborne.selenide.*;
import io.qameta.allure.Step;
import kdt.pages.elements.Header;
import org.openqa.selenium.support.ui.LoadableComponent;
import utils.DriverFactory;
import utils.PropertiesController;

@DynamicPage
public class BasePage extends LoadableComponent<BasePage> {

    protected final PageManager pageManager;
    private static final String BASE_URL = PropertiesController.getProperty("env.url");
    private static String lng = PropertiesController.getProperty("lng");

    static {
        loadLanguageProperties(lng);
    }

    public BasePage() {
        this.pageManager = PageManager.getInstance();
        get();
    }

    public static void setLanguage(String language) {
        lng = language;
    }

    public static void loadLanguageProperties(String language) {
        PropertiesController.loadLanguageProperties(language);
    }

    public Header header() {
        return pageManager.header();
    }

    @Step
    public PageManager clearBrowserCookies() {
        if (WebDriverRunner.hasWebDriverStarted()) Selenide.clearBrowserCookies();
        return pageManager;
    }

    @Override
    protected void load() {
        URI uri = this.getClass().getAnnotation(URI.class);
        Selenide.open(BASE_URL + lng + uri.value());
    }

    @Override
    protected void isLoaded() throws Error {
        if (!this.getClass().isAnnotationPresent(DynamicPage.class)) {
            if (!WebDriverRunner.hasWebDriverStarted()) {
                DriverFactory.initDriver(PropertiesController.getProperty("env.browser"));
            }
            URI uri = this.getClass().getAnnotation(URI.class);
            if (!WebDriverRunner.getWebDriver().getCurrentUrl().equals(BASE_URL + lng + uri.value())) {
                throw new Error();
            }
        }
    }
}