package com.kam.andromate.model;

import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class CompositeTask extends PipelineTask{


    public final static String COMPOSITE_JSON_TAG_NAME = "CompositeTask" ;

    public static final String TAG_AS_THREAD = "As_thread";
    public static final String TAG_TIMEOUT_MS = "Time_out";
    public static final String TAG_SEQUENTIAL_EXEC = "Sequential_exec";

    public static final boolean DEFAULT_AS_THREAD = false;
    public static final long DEFAULT_TAG_TIMEOUT_MS= 0;
    public static final boolean DEFAULT_SEQUENTIAL_EXEC = false;

    private boolean asThread;
    private long timeout_ms;
    private boolean sequentialExec;
    private List<PipelineTask> taskList ;
    private List<Link> links ;

    public CompositeTask(String idTask, String title) {
        super(idTask, title);
        this.taskList = new ArrayList<>();
        this.links = new ArrayList<>();

    }

    public CompositeTask(JSONObject jo) {
        super(jo.optString(PipelineTask.TAG_ID, PipelineTask.DEFAULT_ID),
                jo.optString(PipelineTask.TAG_TITLE, PipelineTask.DEFAULT_TITLE));
        this.asThread = jo.optBoolean(CompositeTask.TAG_AS_THREAD,CompositeTask.DEFAULT_AS_THREAD);
        this.timeout_ms = jo.optLong(CompositeTask.TAG_TIMEOUT_MS,CompositeTask.DEFAULT_TAG_TIMEOUT_MS);
        this.sequentialExec = jo.optBoolean(CompositeTask.TAG_SEQUENTIAL_EXEC,CompositeTask.DEFAULT_SEQUENTIAL_EXEC);
        this.taskList = new ArrayList<>();
        this.links = new ArrayList<>();
    }

    public boolean isAsThread() {
        return asThread;
    }

    public void setAsThread(boolean asThread) {
        this.asThread = asThread;
    }

    public long getTimeout() {
        return timeout_ms;
    }

    public void setTimeout(long timeout_ms) {
        this.timeout_ms = timeout_ms;
    }

    public boolean isSequentialExec() {
        return sequentialExec;
    }

    public void setSequentialExec(boolean sequentialExec) {
        this.sequentialExec = sequentialExec;
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

    public void sort() {
        if (!taskList.isEmpty() && !links.isEmpty()) {
            Map<String, PipelineTask> taskMap = new HashMap<>();
            for (PipelineTask task : taskList) {
                taskMap.put(task.getIdTask(), task);
            }
            Map<String, String> linkMap = new HashMap<>();
            for (Link link : links) {
                linkMap.put(link.getFrom(), link.getTo());
            }
            String startId = null;
            for (String fromId : linkMap.keySet()) {
                boolean isStart = true;
                for (Link link : links) {
                    if (link.getTo().equals(fromId)) {
                        isStart = false;
                        break;
                    }
                }
                if (isStart) {
                    startId = fromId;
                    break;
                }
            }
            if (startId != null) {
                List<PipelineTask> sortedTasks = new ArrayList<>();
                String currentId = startId;
                while (currentId != null) {
                    PipelineTask currentTask = taskMap.get(currentId);
                    if (currentTask != null) {
                        sortedTasks.add(currentTask);
                    }
                    currentId = linkMap.get(currentId);
                }
                this.taskList = sortedTasks;
            }
        }
    }


    @NonNull
    @Override
    public String toString() {
        return "CompositeTask{" +
                "asThread=" + asThread +
                ", timeout_ms=" + timeout_ms +
                ", sequentialExec=" + sequentialExec +
                ", taskList=" + taskList +

                ", idTask='" + idTask + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
