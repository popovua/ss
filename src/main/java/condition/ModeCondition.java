package condition;

import annotations.MobileMode;
import org.testng.annotations.ITestAnnotation;
import utils.PropertiesController;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class ModeCondition implements Condition {

    @Override
    public void applyForTestAnnotation(ITestAnnotation iTestAnnotation, Class aClass, Constructor constructor, Method method) {
        if (PropertiesController.getProperty("mode").equals("mob") && method.getAnnotation(MobileMode.class) == null) {
            iTestAnnotation.setEnabled(false);
        } else if (PropertiesController.getProperty("mode").equals("web") && method.getAnnotation(MobileMode.class) != null) {
            iTestAnnotation.setEnabled(false);
        }
    }
}
