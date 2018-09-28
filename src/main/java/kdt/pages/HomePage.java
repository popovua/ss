package kdt.pages;

import annotations.URI;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import utils.PropertiesController;

import static com.codeborne.selenide.Selenide.$$;

@URI("/")
public class HomePage extends BasePage {

    private ElementsCollection categories = $$(By.xpath(PropertiesController.getProperty("homePage.categories")));
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
    public PageManager clickOnSelectedCategory() {
        foundCategory.click();
        return pageManager;
    }

    @Step
    public PageManager chooseAnnouncementType(String itemName) {
        announcementType = foundCategory.$$(By.xpath(PropertiesController.getProperty("homePage.categories.announcementType"))).stream()
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