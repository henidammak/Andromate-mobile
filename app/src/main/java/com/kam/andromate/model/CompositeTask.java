package com.kam.andromate.model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CompositeTask extends PipelineTask{


    public final static String COMPOSITE_JSON_TAG_NAME = "CompositeTask" ;

    List<PipelineTask> taskList = null;

    List<Link> links = new ArrayList<>();

    public CompositeTask(String id, String title) {
        super(id, title);
        this.taskList = new ArrayList<>();
    }

    public List<PipelineTask> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<PipelineTask> taskList) {
        this.taskList = taskList;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public void addLink(Link link) {
        this.links.add(link);
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
