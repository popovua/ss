package log.selenide.allure;

import attachments.ScreenshotAttachment;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SimpleReport;
import com.codeborne.selenide.testng.ScreenShooter;
import org.testng.ITestResult;

public class SelenideAllureListener extends ScreenShooter {

    protected SimpleReport simpleReport = new SimpleReport();

    @Override
    public void onTestStart(ITestResult result) {
        super.onTestStart(result);
        simpleReport.start();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        super.onTestSuccess(result);
        if (WebDriverRunner.hasWebDriverStarted()) {
            ScreenshotAttachment.screenshot();
        }
        simpleReport.finish(result.getName());
        simpleReport.clean();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        super.onTestFailure(result);
        if (WebDriverRunner.hasWebDriverStarted()) {
            ScreenshotAttachment.screenshot();
        }
        simpleReport.finish(result.getName());
        simpleReport.clean();
    }

    @Override
    public void onConfigurationFailure(ITestResult iTestResult) {
        super.onConfigurationFailure(iTestResult);
        if (WebDriverRunner.hasWebDriverStarted()) {
            ScreenshotAttachment.screenshot();
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        super.onTestFailedButWithinSuccessPercentage(result);
    }
}