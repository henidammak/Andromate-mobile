package com.kam.andromate.model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CompositeTask extends PipelineTask{


    public final static String COMPOSITE_JSON_TAG_NAME = "CompositeTask" ;

    List<PipelineTask> taskList = null;

    public CompositeTask() {
        this.taskList = new ArrayList<>();
    }

    public void addTask(PipelineTask task) {
        this.taskList.add(task);
    }

    @Override
    public PipelineTask jsonToPipeLine(JSONObject jo) {
        return null;
    }

    @Override
    public void executeTask() {
        for (PipelineTask task : taskList) {
            task.executeTask();
        }
    }

}
