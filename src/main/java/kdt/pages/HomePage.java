package kdt.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class HomePage extends Browser {

    private SelenideElement submitAnnouncement = Selenide.$(By.xpath("span[@class='page_header_menu']/b"));

    @Step
    public PageManager submitAnnouncement() {
        submitAnnouncement.click();
        return pageManager;
    }
}