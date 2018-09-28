package testng.listeners;

import com.google.auto.service.AutoService;
import condition.ConditionHolder;
import condition.ModeCondition;
import org.testng.IAnnotationTransformer;
import org.testng.ITestNGListener;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

@AutoService(ITestNGListener.class)
public class AnnotationTransformer implements IAnnotationTransformer {

    public AnnotationTransformer() {
        ConditionHolder.getInstance().addCondition(new ModeCondition());
    }

    @Override
    public void transform(ITestAnnotation iTestAnnotation, Class aClass, Constructor constructor, Method method) {
        ConditionHolder
                .getInstance()
                .getConditions()
                .stream()
                .forEach(c -> c.applyForTestAnnotation(iTestAnnotation, aClass, constructor, method));
    }
}
