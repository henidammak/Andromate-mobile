package com.kam.andromate.model.taskResult;

import com.kam.andromate.model.taskContext.AndromateTaskContext;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CompositeTaskResult extends TaskResult{

    List<TaskResult> taskResults = null;

    public CompositeTaskResult() {
        this.taskResults = new ArrayList<>();
    }

    public void addTaskResult(TaskResult taskResult) {
        taskResults.add(taskResult);
    }

    @Override
    boolean isTaskExecutedSuccessfully() {
        boolean success = true;
        for (TaskResult taskResult : taskResults) {
            success = taskResult.isTaskExecutedSuccessfully();
            if (!success)
                break;
        }
        return success;
    }

    @Override
    void updateAndromateContext(JSONObject variableMaps, AndromateTaskContext andromateTaskContext) {
        for (TaskResult taskResult : taskResults) {
            taskResult.updateAndromateContext(variableMaps, andromateTaskContext);
        }
    }
}
