package kdt.pages;

import com.codeborne.selenide.Selenide;
import kdt.pages.elements.Header;
import kdt.pages.elements.PopUpWindow;

public class PageManager {

    private static PageManager pageManager = null;

    private PageManager() {}

    public static PageManager getInstance() {
        if (pageManager == null) {
            pageManager = new PageManager();
        }
        return pageManager;
    }

    public HomePage homePage() {
        return Selenide.page(HomePage.class);
    }
    public AnnouncementTypePage announcementTypePage() {
        return Selenide.page(AnnouncementTypePage.class);
    }
    public ItemListPage itemListPage() {
        return Selenide.page(ItemListPage.class);
    }
    public ItemPage itemPage() {
        return Selenide.page(ItemPage.class);
    }
    public PopUpWindow popUpWindow() {
        return Selenide.page(PopUpWindow.class);
    }
    public Header header() {
        return Selenide.page(Header.class);
    }
    public SearchPage searchPage() {
        return Selenide.page(SearchPage.class);
    }
    public BasePage basePage() {
        return new BasePage();
    }
}
