package kdt.pages;

import annotations.URI;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import kdt.pages.elements.Header;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@URI("/")
public class HomePage extends BasePage {

    private ElementsCollection categories = $$(By.xpath("(//div[@class='main_head2'])/h2"));
    private static SelenideElement foundCategory;
    private static SelenideElement announcementType;

    @Step
    public PageManager chooseCategory(String categoryName) {
        foundCategory = categories.stream()
                .filter(category -> category.getText().toLowerCase().trim()
                        .equals(categoryName.toLowerCase().trim()))
                .findFirst()
                .get();
        return pageManager;
    }

    @Step
    public PageManager chooseAnnouncementType(String itemName) {
        announcementType = foundCategory.$$(By.xpath("../../../div[@class='main_category']/a")).stream()
                .filter(item -> item.getText().toLowerCase().trim()
                        .equals(itemName.toLowerCase().trim()))
                .findFirst()
                .get();
        return pageManager;
    }

    @Step
    public PageManager clickOnSelectedAnnouncementType() {
        announcementType.click();
        return pageManager;
    }
}