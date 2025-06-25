package com.kam.andromate.model.taskResult;

import com.kam.andromate.model.taskContext.AndromateTaskContext;

import org.json.JSONObject;

public abstract class TaskResult {

    abstract boolean isTaskExecutedSuccessfully();

    abstract void updateAndromateContext(JSONObject variableMaps, AndromateTaskContext andromateTaskContext);

}
