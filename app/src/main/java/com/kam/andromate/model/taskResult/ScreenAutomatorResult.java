package com.kam.andromate.model.taskResult;

import com.kam.andromate.model.taskContext.AndromateTaskContext;

import org.json.JSONObject;

public class ScreenAutomatorResult extends TaskResult{

    private final static String TAG_AUTOMATOR_RESULT = "tag_automator_result";
    private final static String TAG_ERROR_RESULT = "tag_automator_result";

    boolean automatorResult;

    String errorMsg;

    public ScreenAutomatorResult(boolean automatorResult) {
        this.automatorResult = automatorResult;
    }

    public ScreenAutomatorResult() {

    }

    public boolean isAutomatorResult() {
        return automatorResult;
    }

    public void setAutomatorResult(boolean automatorResult) {
        this.automatorResult = automatorResult;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    boolean isTaskExecutedSuccessfully() {
        return automatorResult;
    }

    @Override
    void updateAndromateContext(JSONObject variableMaps, AndromateTaskContext andromateTaskContext) {
        if (variableMaps.has(TAG_AUTOMATOR_RESULT)) {
            andromateTaskContext.setInput(variableMaps.optString(TAG_AUTOMATOR_RESULT), automatorResult);
        }
        if (variableMaps.has(TAG_ERROR_RESULT)) {
            andromateTaskContext.setInput(variableMaps.optString(TAG_ERROR_RESULT), errorMsg);
        }
    }

}
