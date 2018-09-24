package tests;

import org.testng.annotations.Test;

public class SampleTest extends BaseTest {
    @Test
    public void sample() {
        pageManager
                .home().selectCategory("transport")
                .home().selectItemFromCategory("cars")
                .cars().selectModel("kia")
                .item().selectItem(0)
                .itemDescription().addToFavorites();
    }
}