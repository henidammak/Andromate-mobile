package com.kam.andromate.model;

import android.util.Log;

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

    public CompositeTask(String idTask, String title, boolean asThread, long timeout_ms, boolean sequentialExec) {
        super(idTask, title);
        this.asThread = asThread;
        this.timeout_ms = timeout_ms;
        this.sequentialExec = sequentialExec;
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
        List<PipelineTask> sortedList = new ArrayList<>();
        Map<String, PipelineTask> taskMap = new HashMap<>();

        // Indexer les tâches par id
        for (PipelineTask task : taskList) {
            taskMap.put(task.getIdTask(), task);
        }

        // Construire un graphe des dépendances
        Map<String, List<String>> graph = new HashMap<>();
        Map<String, Integer> inDegree = new HashMap<>();

        // Initialiser inDegree
        for (PipelineTask task : taskList) {
            inDegree.put(task.getIdTask(), 0);
            graph.put(task.getIdTask(), new ArrayList<>());
        }

        // Ajouter les arêtes
        for (Link link : links) {
            String from = link.getFrom();
            String to = link.getTo();
            graph.get(from).add(to);
            inDegree.put(to, inDegree.get(to) + 1);
        }

        // Tri topologique (Kahn’s algorithm)
        Queue<String> queue = new LinkedList<>();
        for (String id : inDegree.keySet()) {
            if (inDegree.get(id) == 0) {
                queue.add(id);
            }
        }

        while (!queue.isEmpty()) {
            String currentId = queue.poll();
            PipelineTask currentTask = taskMap.get(currentId);
            if (currentTask != null) {
                sortedList.add(currentTask);
            }

            for (String neighbor : graph.get(currentId)) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        // Mettre à jour la taskList triée
        setTaskList(sortedList);


    }






    @Override
    public String toString() {
        return "CompositeTask{" +
                "asThread=" + asThread +
                ", timeout_ms=" + timeout_ms +
                ", sequentialExec=" + sequentialExec +
                ", taskList=" + taskList +
                ", links=" + links +
                ", idTask='" + idTask + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
