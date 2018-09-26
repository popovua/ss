package kdt.pages;

import annotations.DynamicPage;
import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$$;

@DynamicPage
public class AnnouncementTypePage extends BasePage {

    private ElementsCollection announcements = $$(By.xpath("(//tbody)//h4[@class='category']/a"));

    @Step
    public PageManager clickOnAnnouncement(String announcementName) {
        announcements.stream()
                .filter(announcement -> announcement.getText().toLowerCase().trim()
                        .equals(announcementName.toLowerCase().trim()))
                .findFirst()
                .get()
                .click();
        return pageManager;
    }

}
