package com.kam.andromate.model;

import android.content.Context;

import com.kam.andromate.model.taskContext.AndromateTaskContext;
import com.kam.andromate.model.taskResult.TaskResult;
import com.kam.andromate.view.MainReportSection;

public abstract class BaseTask extends PipelineTask {

    public abstract String getBaseTaskStartMsg();
    public abstract String getBaseTaskEndMsg();
    public abstract TaskResult executeBaseTask(MainReportSection rs, Context context, AndromateTaskContext andromateTaskContext);

    @Override
    public TaskResult executeTask(MainReportSection rs, Context context, AndromateTaskContext andromateTaskContext) {
        rs.appendFmvKey("Base Task", getBaseTaskStartMsg());
        TaskResult taskResult = executeBaseTask(rs, context, andromateTaskContext);
        rs.appendFmvKey("end Base Task", getBaseTaskEndMsg());
        return taskResult;
    }

    public BaseTask(String idTask, String title) {
        super(idTask, title);
    }

}
