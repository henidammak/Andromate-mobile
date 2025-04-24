package com.kam.andromate.model;

import org.json.JSONObject;


public class EndTask extends PipelineTask {

    public final static String JSON_TAG_NAME = "End";

    public EndTask(String idTask, String title) {
        super(idTask, title);
    }

    public EndTask(JSONObject jo) {
        super(jo.optString(PipelineTask.TAG_ID, PipelineTask.DEFAULT_ID),
                jo.optString(PipelineTask.TAG_TITLE, PipelineTask.DEFAULT_TITLE));
    }

    @Override
    public PipelineTask jsonToPipeLine(JSONObject jo) {
        return new EndTask(
                jo.optString(PipelineTask.TAG_ID, PipelineTask.DEFAULT_ID),
                jo.optString(PipelineTask.TAG_TITLE, PipelineTask.DEFAULT_TITLE)
        );
    }

    @Override
    public void executeTask() {}

    public String toString() {
        return "["+super.toString()+" ]";
    }
}

