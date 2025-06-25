package com.kam.andromate.model.taskResult;

import com.kam.andromate.model.taskContext.AndromateTaskContext;

import org.json.JSONObject;

public class CmdTaskResult extends TaskResult{

    private static final String TAG_RESULT_MSG = "tag_result_msg";
    private static final String TAG_ERROR_MSG = "tag_error_msg";

    private String resultMsg;
    private String errorMsg;

    public CmdTaskResult(String resultMsg, String errorMsg) {
        this.resultMsg = resultMsg;
        this.errorMsg = errorMsg;
    }

    public CmdTaskResult() {

    }

    public String getResultMsg() {
        return resultMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    boolean isTaskExecutedSuccessfully() {
        return errorMsg == null || errorMsg.isEmpty();
    }

    @Override
    void updateAndromateContext(JSONObject variableMaps, AndromateTaskContext andromateTaskContext) {
        if (variableMaps.has(TAG_RESULT_MSG)) {
            String resultVariableTag = variableMaps.optString(TAG_RESULT_MSG);
            andromateTaskContext.setInput(resultVariableTag, resultMsg);
        }
        if (variableMaps.has(TAG_ERROR_MSG)) {
            String errorVariableTag = variableMaps.optString(TAG_ERROR_MSG);
            andromateTaskContext.setInput(errorVariableTag, errorMsg);
        }
    }

}
