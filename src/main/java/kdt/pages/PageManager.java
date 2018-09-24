package kdt.pages;

import com.codeborne.selenide.Selenide;
import kdt.pages.transport.CarsPage;

public class PageManager {

    private static PageManager pageManager = null;

    private PageManager() {}

    public static PageManager getInstance() {
        if (pageManager == null) {
            pageManager = new PageManager();
        }
        return pageManager;
    }

    public HomePage home() {
        return Selenide.page(HomePage.class);
    }

    public CarsPage cars() {
        return Selenide.page(CarsPage.class);
    }

    public ItemPage item() {
        return Selenide.page(ItemPage.class);
    }

    public ItemDescriptionPage itemDescription() {
        return Selenide.page(ItemDescriptionPage.class);
    }
}
