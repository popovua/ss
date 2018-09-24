package storage;

import lombok.extern.slf4j.Slf4j;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

@Slf4j
public class StorageContextHandler implements IInvokedMethodListener {

    private static final Storage MEMORY = Storage.getInstance();

    @Override
    public void beforeInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        if (iInvokedMethod.getTestMethod().isBeforeMethodConfiguration()) {
            MEMORY.setContext(Context.TEST);
            MEMORY.clearContext();
            MEMORY.resetContext();
            log.info(Context.TEST.name() + " Context is cleared");
        }
        else if (iInvokedMethod.getTestMethod().isBeforeClassConfiguration()) {
            MEMORY.setContext(Context.CLASS);
            MEMORY.clearContext();
            MEMORY.resetContext();
            log.info(Context.CLASS.name() + " Context is cleared");
        }
        else if (iInvokedMethod.getTestMethod().isBeforeSuiteConfiguration()) {
            MEMORY.setContext(Context.SUITE);
            MEMORY.clearContext();
            MEMORY.resetContext();
            log.info(Context.SUITE.name() + " Context is cleared");
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {

    }
}
