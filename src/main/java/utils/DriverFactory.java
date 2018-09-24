package utils;

import com.codeborne.selenide.WebDriverRunner;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
public class DriverFactory {

    private static WebDriver driver;

    public static void initDriver(String browser) {
        switch(PropertiesController.getProperty("grid.mode")) {
            case "local":
                if (browser.equals("chrome")) {
                    ChromeDriverManager.getInstance().setup();
                    log.info("webdriver path: " + System.getProperty("webdriver.chrome.driver"));
                    ChromeOptions options = new ChromeOptions();
                    driver = new ChromeDriver(options);

                }
                break;
            case "external":
                ChromeOptions options = new ChromeOptions();
                driver = setUpDriver(options);
                break;
        }

        WebDriverRunner.setWebDriver(driver);
    }

    private static WebDriver setUpDriver(ChromeOptions options) {
        try {
            return new RemoteWebDriver(new URL(PropertiesController.getProperty("external.grid.host")), options);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
