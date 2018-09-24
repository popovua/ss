package kdt.pages;

import com.codeborne.selenide.Selenide;

public class PageManager {

    private static PageManager pageManager = null;

    private PageManager() {}

    public static PageManager getInstance() {
        if (pageManager == null) {
            pageManager = new PageManager();
        }
        return pageManager;
    }

    public Browser browser() {
        return new Browser();
    }

    public HomePage homePage() {
        return Selenide.page(HomePage.class);
    }
}
