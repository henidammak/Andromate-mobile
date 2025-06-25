package com.kam.andromate.model;

import android.content.Context;

import androidx.annotation.NonNull;

import com.kam.andromate.model.taskContext.AndromateTaskContext;
import com.kam.andromate.model.taskResult.TaskResult;
import com.kam.andromate.view.MainReportSection;

import org.json.JSONObject;

public abstract class PipelineTask {

    public final static String TAG_ID = "id";
    public final static String TAG_TITLE = "title";

    public final static String DEFAULT_ID = "0";
    public final static String DEFAULT_TITLE = "task";

    String idTask;
    String title;

    public PipelineTask(String idTask, String title) {
        this.idTask = idTask;
        this.title = title;
    }

    public String getIdTask() {
        return idTask;
    }

    public String getTitle() {
        return title;
    }

    public void execute(MainReportSection rs, Context context, AndromateTaskContext andromateTaskContext) {
        //TODO: Here start recording MainReportSection to get file and save it in db
        resolveTaskWithContext(andromateTaskContext);
        rs.appendTitle("execute task "+title);
        rs.incTaskMargin();
        executeTask(rs, context, andromateTaskContext);
        rs.discTaskMargin();
        rs.appendTitle("end task "+title);
        //TODO stop recording and get file and upload it in db
        //TODO WEB FRONT SHOULD GET THE FILE EXECUTION FOR EACH DEVICE
    }

    abstract public TaskResult executeTask(MainReportSection rs, Context context, AndromateTaskContext andromateTaskContext);

    abstract public PipelineTask jsonToPipeLine(JSONObject jo);

    abstract public void resolveTaskWithContext(AndromateTaskContext andromateTaskContext);

    @NonNull
    @Override
    public String toString() {
        return "PipelineTask{" +
                "idTask='" + idTask + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
