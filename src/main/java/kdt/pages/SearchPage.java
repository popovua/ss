package kdt.pages;

import annotations.DynamicPage;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import utils.PropertiesController;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@DynamicPage
public class SearchPage extends BasePage {

    private SelenideElement input = $(By.id(PropertiesController.getProperty("searchPage.input")));
    private ElementsCollection dropDownResultList = $$(By.xpath(PropertiesController.getProperty("searchPage.dropDownResultList")));
    private SelenideElement searchButton = $(By.id(PropertiesController.getProperty("searchPage.searchButton")));

    private static String wordOrPhrase;

    @Step
    public PageManager typeWordOrPhrase(String wordOrPhrase) {
        input.sendKeys(wordOrPhrase);
        this.wordOrPhrase = wordOrPhrase;
        return pageManager;
    }

    @Step
    public PageManager clickOnSuggestedResultWhichFitsWordOrPhrase() {
        dropDownResultList.shouldBe(sizeGreaterThan(0))
                .stream()
                .filter(result -> result.getText().toLowerCase().trim()
                        .equals(wordOrPhrase.toLowerCase().trim()))
                .findFirst()
                .orElse(
                        dropDownResultList.stream()
                                .filter(result -> result.getText().toLowerCase().trim()
                                        .contains(wordOrPhrase.toLowerCase().trim()))
                                .findFirst()
                                .orElseThrow(
                                        () -> new AssertionError(wordOrPhrase + " doesn't fit suggested results")
                                )
                )
                .click();
        return pageManager;
    }

    @Step
    public PageManager clickOnSearchButton() {
        searchButton.click();
        return pageManager;
    }

}
