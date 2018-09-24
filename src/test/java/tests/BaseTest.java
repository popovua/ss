package tests;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import kdt.pages.PageManager;
import log.selenide.allure.SelenideAllureListener;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Listeners;
import storage.Storage;
import storage.StorageContextHandler;

@Listeners(value = {SelenideAllureListener.class, StorageContextHandler.class})
public class BaseTest {

    protected final PageManager pageManager = PageManager.getInstance();
    protected static final Storage storage = Storage.getInstance();

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        if (WebDriverRunner.hasWebDriverStarted()) Selenide.close();
    }
}