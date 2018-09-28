package condition;

import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public interface Condition {
    void applyForTestAnnotation(ITestAnnotation iTestAnnotation, Class aClass, Constructor constructor, Method method);
}
