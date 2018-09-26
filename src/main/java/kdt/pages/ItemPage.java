package kdt.pages;

import annotations.DynamicPage;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

@DynamicPage
public class ItemPage extends BasePage {

    private SelenideElement addTofavorites = $(By.id("a_fav"));

    public PageManager addToFavorites() {
        addTofavorites.click();
        return pageManager;
    }
}
