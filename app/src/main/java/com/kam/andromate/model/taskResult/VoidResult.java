package com.kam.andromate.model.taskResult;

import com.kam.andromate.model.taskContext.AndromateTaskContext;

import org.json.JSONObject;

public class VoidResult extends TaskResult{

    @Override
    boolean isTaskExecutedSuccessfully() {
        return true;
    }

    @Override
    void updateAndromateContext(JSONObject variableMaps, AndromateTaskContext andromateTaskContext) {
        //nothing to do void result has no return
    }

}
