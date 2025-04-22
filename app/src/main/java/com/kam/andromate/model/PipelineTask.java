package com.kam.andromate.model;

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

    abstract public PipelineTask jsonToPipeLine(JSONObject jo);

    abstract public void executeTask();


    @Override
    public String toString() {
        return "PipelineTask{" +
                "idTask='" + idTask + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
