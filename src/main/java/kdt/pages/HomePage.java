package kdt.pages;

import annotations.URI;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@URI("/")
public class HomePage extends Browser {

    private SelenideElement submitAnnouncement = $(By.xpath("//span[@class='page_header_menu']/b[1]"));
    private ElementsCollection categories = $$(By.xpath("(//div[@class='main_head2'])/h2"));
    private static SelenideElement foundCategory;

    @Step
    public PageManager submitAnnouncement() {
        submitAnnouncement.click();
        return pageManager;
    }

    @Step
    public PageManager selectCategory(String categoryName) {
        foundCategory = categories.stream()
                .filter(category -> category.getText().toLowerCase().trim()
                        .equals(categoryName.toLowerCase().trim()))
                .findFirst()
                .get();
        return pageManager;
    }

    @Step
    public PageManager selectItemFromCategory(String itemName) {
        foundCategory.$$(By.xpath("../../../div[@class='main_category']/a")).stream()
                .filter(item -> item.getText().toLowerCase().trim()
                        .equals(itemName.toLowerCase().trim()))
                .findFirst()
                .get()
                .click();
        return pageManager;
    }
}