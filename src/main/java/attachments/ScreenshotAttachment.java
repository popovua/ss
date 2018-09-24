package attachments;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotAttachment {

    @Attachment
    public static byte[] screenshot() {
        byte[] result;
        WebDriver driver = WebDriverRunner.getWebDriver();
        result = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        return result;
    }
}

