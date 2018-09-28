package kdt.pages.elements;

import annotations.DynamicPage;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import kdt.pages.BasePage;
import kdt.pages.PageManager;
import org.openqa.selenium.By;
import utils.PropertiesController;

import static com.codeborne.selenide.Selenide.$;
import static storage.Storage.rememberThe;

@DynamicPage
public class Header extends BasePage {

    private SelenideElement memo = $(By.id(PropertiesController.getProperty("header.memo")));
    private SelenideElement search = $(By.xpath(PropertiesController.getProperty("header.search")));

    public static final String REMEMBERED_ITEMS = "headerRememberedItems";

    @Step
    public PageManager clickOnMemo() {
        memo.$(By.xpath("..")).click();
        return pageManager;
    }

    @Step
    public PageManager clickOnSearch() {
        search.click();
        return pageManager;
    }

    @Step
    public PageManager rememberMemoValue() {
        rememberThe(REMEMBERED_ITEMS, memo.getText().replaceAll("\\D", ""));
        return pageManager;
    }

}
