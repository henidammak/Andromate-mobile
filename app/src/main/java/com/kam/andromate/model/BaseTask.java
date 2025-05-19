package com.kam.andromate.model;

import android.content.Context;

import com.kam.andromate.view.MainReportSection;

public abstract class BaseTask extends PipelineTask {

    public abstract String getBaseTaskStartMsg();
    public abstract String getBaseTaskEndMsg();
    public abstract void executeBaseTask(MainReportSection rs, Context context);

    @Override
    public void executeTask(MainReportSection rs, Context context) {
        rs.appendFmvKey("Base Task", getBaseTaskStartMsg());
        executeBaseTask(rs, context);
        rs.appendFmvKey("end Base Task", getBaseTaskEndMsg());
    }

    public BaseTask(String idTask, String title) {
        super(idTask, title);
    }

}
