package com.kam.andromate.model.androidStageModel;

import com.kam.andromate.model.AndroidTask;
import com.kam.andromate.model.PipelineTask;

import org.json.JSONObject;

public class AndroMateSleepTask extends AndroidTask {

    public final static String TAG_TIME_SLEEP = "Time_sleep";


    public final static long DEFAULT_TIME_SLEEP = 0 ;


    private long timeSleep;

    public AndroMateSleepTask(long timeSleep) {
        this.timeSleep = timeSleep;
    }

    public AndroMateSleepTask(JSONObject jo) {
        this.timeSleep = jo.optLong(TAG_TIME_SLEEP,DEFAULT_TIME_SLEEP);
    }

    public long getTimeSleep() {
        return timeSleep;
    }

    public void setTimeSleep(long timeSleep) {
        this.timeSleep = timeSleep;
    }

    @Override
    public PipelineTask jsonToPipeLine(JSONObject jo) {
        return new AndroMateSleepTask(
                jo.optLong(TAG_TIME_SLEEP,DEFAULT_TIME_SLEEP)
        );
    }
}
