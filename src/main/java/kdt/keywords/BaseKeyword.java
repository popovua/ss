package kdt.keywords;

import io.qameta.allure.Step;
import storage.Context;
import storage.Storage;

public abstract class BaseKeyword {

    protected final KeywordManager keywordManager;
    public static final String REST_ASSURED_RESPONSE = "Response";
    public static final String REST_ASSURED_ERROR_RESPONSE = "restAssuredErrorResponse";

    public BaseKeyword(KeywordManager keywordManager) {
        this.keywordManager = keywordManager;
    }

    @Step
    public KeywordManager setContext(Context context) {
        Storage.setContext(context);
        return keywordManager;
    }

    @Step
    public KeywordManager resetContext() {
        Storage.resetContext();
        return keywordManager;
    }
}
