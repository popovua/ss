package tests;

import io.qameta.allure.Step;
import kdt.pages.ItemListPage;
import kdt.pages.elements.Header;
import kdt.pages.elements.PopUpWindow;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.PropertiesController;

import java.util.List;

import static matchers.StringDigitsMatcher.shouldBeGreaterThan;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static storage.Storage.whatIsThe;
import static storage.Storage.whatIsTheObject;

public class MemoTest extends BaseTest {

    @DataProvider
    public Object[][] dataForTestAddItemToMemo() {
        return new Object[][] {
                {"home stuff", "health, beauty", "skin"},
                {"transport", "cars", "kia"}
        };
    }

    @BeforeClass
    public void prepareForTestClass() {
        pageManager
                .basePage().clearBrowserCookies();
    }

    @Test(dataProvider = "dataForTestAddItemToMemo")
    public void testAddItemToMemo(String category, String announcementType, String announcementName) {
        pageManager
                .homePage().chooseCategory(category)
                .homePage().chooseAnnouncementType(announcementType)
                .homePage().clickOnSelectedAnnouncementType()
                .announcementTypePage().clickOnAnnouncement(announcementName)
                .itemListPage().chooseItem(0)
                .itemListPage().rememberSelectedItemId()
                .itemListPage().clickOnSelectedItem()
                .header().rememberMemoValue();
        String expectedItemId = whatIsThe(ItemListPage.ITEM_ID);
        String beforeAddToFavorites = whatIsThe(Header.REMEMBERED_ITEMS);

        pageManager
                .itemPage().addToFavorites()
                .popUpWindow().rememberMessage()
                .header().rememberMemoValue();
        popUpWindowShouldBeDisplayedWithAppropriateText();

        String afterAddToFavorites = whatIsThe(Header.REMEMBERED_ITEMS);
        favoriteItemsShouldBeIncreasedBy1(beforeAddToFavorites, afterAddToFavorites);

        pageManager
                .header().clickOnMemo()
                .itemListPage().rememberItemsSize()
                .itemListPage().rememberItemIdsList();
        List<String> actualItemIdsList = whatIsTheObject(ItemListPage.ITEM_IDS_LIST, List.class);

        favoriteListSizeShouldBe(afterAddToFavorites);
        addedItemShouldBeInMemoList(actualItemIdsList, expectedItemId);
    }

    @Test
    public void testAddItemToMemoViaSearch() {
        pageManager
                .homePage()
                .header().clickOnSearch()
                .searchPage().typeWordOrPhrase("riga")
                .searchPage().clickOnSuggestedResultWhichFitsWordOrPhrase()
                .searchPage().clickOnSearchButton()
                .itemListPage().chooseItem(0)
                .itemListPage().rememberSelectedItemId()
                .itemListPage().clickOnSelectedItem()
                .header().rememberMemoValue();

        String expectedItemId = whatIsThe(ItemListPage.ITEM_ID);
        String beforeAddToFavorites = whatIsThe(Header.REMEMBERED_ITEMS);

        pageManager
                .itemPage().addToFavorites()
                .popUpWindow().rememberMessage()
                .header().rememberMemoValue();
        popUpWindowShouldBeDisplayedWithAppropriateText();

        String afterAddToFavorites = whatIsThe(Header.REMEMBERED_ITEMS);
        favoriteItemsShouldBeIncreasedBy1(beforeAddToFavorites, afterAddToFavorites);

        pageManager
                .header().clickOnMemo()
                .itemListPage().rememberItemsSize()
                .itemListPage().rememberItemIdsList();
        List<String> actualItemIdsList = whatIsTheObject(ItemListPage.ITEM_IDS_LIST, List.class);

        favoriteListSizeShouldBe(afterAddToFavorites);
        addedItemShouldBeInMemoList(actualItemIdsList, expectedItemId);
    }

    @Step
    private void popUpWindowShouldBeDisplayedWithAppropriateText() {
        String expected = PropertiesController.getProperty("itemPage.advertisementAdded");
        String actual = whatIsThe(PopUpWindow.MESSAGE);
        assertThat(actual, equalTo(expected));
    }

    @Step
    private void favoriteItemsShouldBeIncreasedBy1(String beforeAddToFavorites, String afterAddToFavorites) {
        assertThat(afterAddToFavorites, shouldBeGreaterThan(beforeAddToFavorites));
    }

    @Step
    private void addedItemShouldBeInMemoList(List<String> actual, String expected) {
        assertThat(actual, hasItem(expected));
    }

    @Step
    private void favoriteListSizeShouldBe(String expected) {
        assertThat(whatIsThe(ItemListPage.ITEMS_SIZE), equalTo(expected));
    }
}