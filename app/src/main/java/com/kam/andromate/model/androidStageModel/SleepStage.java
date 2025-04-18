package com.kam.andromate.model.androidStageModel;

import com.kam.andromate.IConstants;
import com.kam.andromate.model.AndroidStage;
import com.kam.andromate.model.PipelineStage;

import org.json.JSONObject;

public class SleepStage extends AndroidStage {

    public final static String TAG_TIME_SLEEP = "Time_sleep";


    public final static long DEFAULT_TIME_SLEEP = 0 ;


    private long timeSleep;

    public SleepStage(long timeSleep) {
        this.timeSleep = timeSleep;
    }

    public long getTimeSleep() {
        return timeSleep;
    }

    public void setTimeSleep(long timeSleep) {
        this.timeSleep = timeSleep;
    }

    @Override
    public PipelineStage jsonToPipeLine(JSONObject jo) {
        return new SleepStage(
                jo.optLong(TAG_TIME_SLEEP,DEFAULT_TIME_SLEEP)
        );
    }
}
