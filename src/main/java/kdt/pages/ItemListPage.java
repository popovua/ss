package kdt.pages;

import annotations.DynamicPage;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.$$;
import static storage.Storage.rememberThe;

@DynamicPage
public class ItemListPage extends BasePage {

    private ElementsCollection items = $$(By.xpath("(//tbody)//div[@class='d1']"));
    private static SelenideElement selectedItem;

    public static final String ITEM_ID = "itemId";
    public static final String ITEM_IDS_LIST = "itemIdsList";
    public static final String ITEMS_SIZE = "itemsSize";

    @Step
    public PageManager chooseItem(String itemName) {
        selectedItem = items.stream()
                .filter(item -> item.getText().toLowerCase().trim()
                        .contains(itemName.toLowerCase().trim()))
                .findFirst()
                .get();
        return pageManager;
    }

    @Step
    public PageManager chooseItem(int itemIndex) {
        selectedItem = items.get(itemIndex);
        return pageManager;
    }

    @Step
    public PageManager clickOnSelectedItem() {
        selectedItem.click();
        return pageManager;
    }

    @Step
    public PageManager rememberSelectedItemId() {
        rememberThe(ITEM_ID, selectedItem.$(By.xpath("../..")).attr("id")
                .replaceAll("\\D", ""));
        return pageManager;
    }

    @Step
    public PageManager rememberItemIdsList() {
        rememberThe(ITEM_IDS_LIST, items.stream()
                .map(item -> item.$(By.xpath("../..")).attr("id")
                .replaceAll("\\D", ""))
                .collect(Collectors.toList()));
        return pageManager;
    }

    @Step
    public PageManager rememberItemsSize() {
        rememberThe(ITEMS_SIZE, String.valueOf(items.size()));
        return pageManager;
    }
}