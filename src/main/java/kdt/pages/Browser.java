package kdt.pages;

import annotations.DynamicPage;
import annotations.URI;
import com.codeborne.selenide.*;
import io.qameta.allure.Step;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.LoadableComponent;
import utils.DriverFactory;
import utils.PropertiesController;

public class Browser extends LoadableComponent<Browser> {

    protected final PageManager pageManager;
    private static final String BASE_URL = PropertiesController.getProperty("env.url");
    private static String uri;
    private static final String LNG = PropertiesController.getProperty("lng");

    public Browser() {
        this.pageManager = PageManager.getInstance();
        get();
    }

    @Step
    public PageManager open(String url) {
        if (!WebDriverRunner.hasWebDriverStarted()) {
            DriverFactory.initDriver(PropertiesController.getProperty("env.browser"));
        }
        Selenide.open(url);
        return pageManager;
    }

    @Step
    public PageManager quite() {
        if (WebDriverRunner.hasWebDriverStarted()) Selenide.close();
        return pageManager;
    }

    @Step
    public PageManager closeTab() {
        WebDriverRunner.getWebDriver().close();
        return pageManager;
    }

    @Step
    public PageManager switchToTab(int tab) {
        Selenide.switchTo().window(tab);
        return pageManager;
    }

    @Step
    public PageManager pressEscape() {
        Selenide.getFocusedElement().sendKeys(Keys.ESCAPE);
        return pageManager;
    }

    @Step
    public PageManager refresh() {
        Selenide.refresh();
        return pageManager;
    }

    @Step
    public PageManager pressEnter() {
        Selenide.getFocusedElement().sendKeys(Keys.ENTER);
        return pageManager;
    }

    @Step
    public PageManager zoom(int percent) {
        JavascriptExecutor js = (JavascriptExecutor) WebDriverRunner.getWebDriver();
        js.executeScript("document.body.style.zoom='" + percent + "%'");
        return pageManager;
    }

    @Step
    public PageManager resetBrowserZoom() {
        JavascriptExecutor js = (JavascriptExecutor) WebDriverRunner.getWebDriver();
        js.executeScript("document.body.style.zoom='100%'");
        return pageManager;
    }

    @Step
    public PageManager maximize() {
        WebDriverRunner.getWebDriver().manage().window().maximize();
        return pageManager;
    }

    @Override
    protected void load() {
        URI uri = this.getClass().getAnnotation(URI.class);
        Selenide.open(BASE_URL + LNG + uri.value());
    }

    @Override
    protected void isLoaded() throws Error {
        if (!this.getClass().isAnnotationPresent(DynamicPage.class)) {
            if (!WebDriverRunner.hasWebDriverStarted()) {
                DriverFactory.initDriver(PropertiesController.getProperty("env.browser"));
            }
            URI uri = this.getClass().getAnnotation(URI.class);
            if (!WebDriverRunner.getWebDriver().getCurrentUrl().equals(BASE_URL + LNG + uri.value())) {
                throw new Error();
            }
        }
        else {
            System.out.println(1);
        }
    }
}