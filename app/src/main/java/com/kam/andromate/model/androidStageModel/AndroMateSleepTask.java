package com.kam.andromate.model.androidStageModel;

import androidx.annotation.NonNull;

import com.kam.andromate.IConstants;
import com.kam.andromate.model.AndroidTask;
import com.kam.andromate.model.PipelineTask;
import com.kam.andromate.utils.ThreadUtils.ThreadHelper;
import com.kam.andromate.view.MainReportSection;

import org.json.JSONObject;

public class AndroMateSleepTask extends AndroidTask {

    public final static String TAG_TIME_SLEEP = "Time_sleep";


    public final static long DEFAULT_TIME_SLEEP = 0 ;

    public final static String JSON_TAG_NAME = "Sleep" ;


    private long timeSleep;

    public AndroMateSleepTask(String id, String title, long timeSleep) {
        super(id, title);
        this.timeSleep = timeSleep;
    }

    public AndroMateSleepTask(JSONObject jo) {
        super(jo.optString(PipelineTask.TAG_ID, PipelineTask.DEFAULT_ID),
                jo.optString(PipelineTask.TAG_TITLE, PipelineTask.DEFAULT_TITLE));
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
                jo.optString(PipelineTask.TAG_ID, PipelineTask.DEFAULT_ID),
                jo.optString(PipelineTask.TAG_TITLE, PipelineTask.DEFAULT_TITLE),
                jo.optLong(TAG_TIME_SLEEP,DEFAULT_TIME_SLEEP)
        );
    }

    @Override
    public void executeTask(MainReportSection rs) {
        rs.appendFmvKey("SleepTask", toString());
        ThreadHelper.deepSleep(timeSleep);
    }

    @NonNull
    @Override
    public String toString() {
        return "AndroMateSleepTask{" +
                "timeSleep=" + timeSleep +
                '}';
    }
}
