package log.restassured.steps;

import io.qameta.allure.Attachment;
import io.qameta.allure.listener.StepLifecycleListener;
import io.qameta.allure.model.StepResult;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class RestAssuredStepsListener implements StepLifecycleListener {

    private ByteArrayOutputStream request = new ByteArrayOutputStream();
    private ByteArrayOutputStream response = new ByteArrayOutputStream();

    private PrintStream requestStream = new PrintStream(request, true);
    private PrintStream responseStream = new PrintStream(response, true);

    @Override
    public void beforeStepStart(StepResult result) {
        RestAssured.filters(new ResponseLoggingFilter(LogDetail.ALL, responseStream),
                new RequestLoggingFilter(LogDetail.ALL, requestStream));
        logRequest(request);
        logResponse(response);
    }

    @Override
    public void afterStepStart(StepResult result) {

    }

    @Override
    public void beforeStepUpdate(StepResult result) {

    }

    @Override
    public void afterStepUpdate(StepResult result) {

    }

    @Override
    public void beforeStepStop(StepResult result) {

    }

    @Override
    public void afterStepStop(StepResult result) {

    }

    @Attachment(value = "request")
    public byte[] logRequest(ByteArrayOutputStream stream) {
        return attach(stream);
    }

    @Attachment(value = "response")
    public byte[] logResponse(ByteArrayOutputStream stream) {
        return attach(stream);
    }

    public byte[] attach(ByteArrayOutputStream log) {
        byte[] array = log.toByteArray();
        log.reset();
        return array;
    }
}
