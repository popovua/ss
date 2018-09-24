package kdt.pages;

import annotations.DynamicPage;
import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$$;

@DynamicPage
public class ItemPage extends Browser {

    private ElementsCollection items = $$(By.xpath("(//form[@id='filter_frm'])//div[@class='d1']"));

    @Step
    public PageManager selectItem(String itemName) {
        items.stream()
                .filter(item -> item.getText().toLowerCase().trim()
                        .contains(itemName.toLowerCase().trim()))
                .findFirst()
                .get()
                .click();
        return pageManager;
    }

    @Step
    public PageManager selectItem(int itemIndex) {
        items.get(itemIndex).click();
        return pageManager;
    }
}
