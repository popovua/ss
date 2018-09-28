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
public class PopUpWindow extends BasePage {

    private SelenideElement popUp = $(By.id(PropertiesController.getProperty("popupWindow.popup")));
    public static final String MESSAGE = "popUpWindowMessage";

    @Step
    public PageManager rememberMessage() {
        rememberThe(MESSAGE, popUp.$(By.id(PropertiesController.getProperty("popupWindow.popup.alertMsg"))).getText());
        return pageManager;
    }
}
