package com.kam.andromate.model;

import org.json.JSONObject;

public class StartTask extends PipelineTask {

    public final static String JSON_TAG_NAME = "Start";

    public static final String TAG_AS_THREAD = "As_thread";
    public static final String TAG_TIMEOUT = "Time_out";
    public static final String TAG_SEQUENTIAL_EXEC = "Sequential_exec";

    public static final boolean DEFAULT_AS_THREAD = false;
    public static final long DEFAULT_TIMEOUT = 0;
    public static final boolean DEFAULT_SEQUENTIAL_EXEC = false;

    private boolean asThread;
    private long timeout;
    private boolean sequentialExec;

    public StartTask(String idTask, String title, boolean asThread, long timeout, boolean sequentialExec) {
        super(idTask, title);
        this.asThread = asThread;
        this.timeout = timeout;
        this.sequentialExec = sequentialExec;
    }

    public StartTask(JSONObject jo) {
        super(jo.optString(PipelineTask.TAG_ID, PipelineTask.DEFAULT_ID),
                jo.optString(PipelineTask.TAG_TITLE, PipelineTask.DEFAULT_TITLE));
        this.asThread = jo.optBoolean(TAG_AS_THREAD,DEFAULT_AS_THREAD);
        this.timeout = jo.optLong(TAG_TIMEOUT, DEFAULT_TIMEOUT);
        this.sequentialExec = jo.optBoolean(TAG_SEQUENTIAL_EXEC, DEFAULT_SEQUENTIAL_EXEC);
    }

    @Override
    public PipelineTask jsonToPipeLine(JSONObject jo) {
        return new StartTask(
                jo.optString(PipelineTask.TAG_ID, PipelineTask.DEFAULT_ID),
                jo.optString(PipelineTask.TAG_TITLE, PipelineTask.DEFAULT_TITLE),
                jo.optBoolean(TAG_AS_THREAD,DEFAULT_AS_THREAD),
                jo.optLong(TAG_TIMEOUT, DEFAULT_TIMEOUT),
                jo.optBoolean(TAG_SEQUENTIAL_EXEC, DEFAULT_SEQUENTIAL_EXEC)
        );
    }

    @Override
    public void executeTask() {}

    public String toString() {
        return "["+super.toString()+"  StartTask{" +
                "asThread='" + asThread + '\'' +
                ",timeout='" + timeout + '\'' +
                ",sequentialExec='" + sequentialExec + '\'' +
                '}'+"]";
    }
}
