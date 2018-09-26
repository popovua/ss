package kdt.pages.elements;

import annotations.DynamicPage;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import kdt.pages.BasePage;
import kdt.pages.PageManager;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static storage.Storage.rememberThe;

@DynamicPage
public class Header extends BasePage {

    private SelenideElement memo = $(By.id("mnu_fav_id"));
    private SelenideElement search = $(By.xpath("//span[@class='page_header_menu']/b[3]"));

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
