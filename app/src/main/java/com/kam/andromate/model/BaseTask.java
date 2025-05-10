package com.kam.andromate.model;

import com.kam.andromate.view.MainReportSection;

public abstract class BaseTask extends PipelineTask {

    public abstract String getBaseTaskStartMsg();
    public abstract String getBaseTaskEndMsg();
    public abstract void executeBaseTask(MainReportSection rs);

    @Override
    public void executeTask(MainReportSection rs) {
        rs.appendFmvKey("Base Task", getBaseTaskStartMsg());
        executeBaseTask(rs);
        rs.appendFmvKey("end Base Task", getBaseTaskEndMsg());
    }

    public BaseTask(String idTask, String title) {
        super(idTask, title);
    }

}
