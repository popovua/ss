package tests;

import org.testng.annotations.Test;
import utils.PropertiesController;

public class SampleTest extends BaseTest {
    @Test
    public void sample() {
        pageManager
                .browser().open(PropertiesController.getProperty("env.url"))
                .homePage().submitAnnouncement();
    }
}
