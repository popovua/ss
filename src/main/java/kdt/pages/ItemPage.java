package kdt.pages;

import annotations.DynamicPage;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import utils.PropertiesController;

import static com.codeborne.selenide.Selenide.$;

@DynamicPage
public class ItemPage extends BasePage {

    private SelenideElement addToFavorites = $(By.id(PropertiesController.getProperty("itemPage.addToFavorites")));

    public PageManager addToFavorites() {
        addToFavorites.click();
        return pageManager;
    }
}
