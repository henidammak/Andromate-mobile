package com.kam.andromate.model.factory;

import android.util.Log;

import com.kam.andromate.model.CompositeTask;
import com.kam.andromate.model.Link;
import com.kam.andromate.model.PipelineTask;
import com.kam.andromate.model.androidStageModel.AndroMateIntentTask;
import com.kam.andromate.model.androidStageModel.AndroMateSleepTask;
import com.kam.andromate.model.baseStageModel.AndroMateCmdTask;
import com.kam.andromate.model.baseStageModel.ScreenAutomatorTask;
import com.kam.andromate.model.baseStageModel.TextReportTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AndroMateFactory {

    private final static String TAG = "AndroMateFactory";

    public final static String START_JSON_TAG_NAME = "Start";

    public final static String END_JSON_TAG_NAME = "End";

    public static CompositeTask createPipeLineFromJson(JSONObject jo) throws JSONException {
        CompositeTask compositeTask = null;
        if (jo != null) {
            compositeTask = new CompositeTask(jo.optString(PipelineTask.TAG_ID), jo.optString(PipelineTask.TAG_TITLE));
            Log.i(TAG,"compositeTask "+compositeTask);
            JSONArray tags = jo.names();
            if (tags != null) {
                String currentTag = null;
                for (int tagIndex = 0; tagIndex < tags.length(); tagIndex++) {
                    currentTag = tags.getString(tagIndex);
                    Log.i(TAG, "current tag received + "+currentTag);
                    switch (currentTag) {
                        case START_JSON_TAG_NAME:
                            JSONArray startArray = jo.optJSONArray(START_JSON_TAG_NAME);
                            Log.i(TAG,"startArray "+startArray);
                            for (int i = 0; i < startArray.length(); i++) {
                                JSONObject startObj = startArray.getJSONObject(i);
                                compositeTask.setAsThread(startObj.optBoolean(CompositeTask.TAG_AS_THREAD, CompositeTask.DEFAULT_AS_THREAD));
                                compositeTask.setTimeout(startObj.optLong(CompositeTask.TAG_TIMEOUT_MS, CompositeTask.DEFAULT_TAG_TIMEOUT_MS));
                                compositeTask.setSequentialExec(startObj.optBoolean(CompositeTask.TAG_SEQUENTIAL_EXEC, CompositeTask.DEFAULT_SEQUENTIAL_EXEC));
                                Log.i(TAG, "Set start config: AsThread=" + compositeTask.isAsThread() +
                                        ", Timeout=" + compositeTask.getTimeout() +
                                        ", SequentialExec=" + compositeTask.isSequentialExec());
                            }
                            break;


                        case END_JSON_TAG_NAME:
                            break;

                        case Link.JSON_TAG_NAME:
                            JSONArray linkArray = jo.optJSONArray(Link.JSON_TAG_NAME);
                            for (int i = 0; i < linkArray.length(); i++) {
                                JSONObject linkObj = linkArray.getJSONObject(i);
                                String from = linkObj.optString(Link.TAG_FROM,Link.DEFAULT_FROM );
                                String to = linkObj.optString(Link.TAG_TO, Link.DEFAULT_TO);
                                compositeTask.addLink(new Link(from, to));
                                Log.i(TAG,"taskList "+compositeTask.getLinks());
                            }
                            break;

                        case AndroMateIntentTask.JSON_TAG_NAME:
                            JSONArray intentArray = jo.optJSONArray(AndroMateIntentTask.JSON_TAG_NAME);
                            if (intentArray != null) {
                                for (int i = 0; i < intentArray.length(); i++) {
                                    JSONObject intentObj = intentArray.getJSONObject(i);
                                    AndroMateIntentTask intentTask = new AndroMateIntentTask(intentObj);
                                    compositeTask.addTask(intentTask);
                                }
                            }
                            break;

                        case AndroMateSleepTask.JSON_TAG_NAME:
                            try {
                                JSONArray sleepArray = jo.optJSONArray(AndroMateSleepTask.JSON_TAG_NAME);
                                if (sleepArray != null) {
                                    for (int i = 0; i < sleepArray.length(); i++) {
                                        JSONObject sleepObj = sleepArray.getJSONObject(i);
                                        AndroMateSleepTask sleepTask = new AndroMateSleepTask(sleepObj);
                                        Log.i(TAG,"sleepTask "+sleepTask);
                                        compositeTask.addTask(sleepTask);
                                        Log.i(TAG,"taskList "+compositeTask.getTaskList());
                                    }
                                }
                            } catch (Throwable t) {
                                Log.e(TAG, " cannot get jsonArray sleep due to "+t);
                            }
                            break;

                        case AndroMateCmdTask.JSON_TAG_NAME:
                            JSONArray cmdArray = jo.optJSONArray(AndroMateCmdTask.JSON_TAG_NAME);
                            if (cmdArray != null) {
                                for (int i = 0; i < cmdArray.length(); i++) {
                                    JSONObject cmdObj = cmdArray.getJSONObject(i);
                                    AndroMateCmdTask cmdTask = new AndroMateCmdTask(cmdObj);
                                    Log.i(TAG,"cmdTask "+cmdTask);
                                    compositeTask.addTask(cmdTask);
                                    Log.i(TAG,"taskList "+compositeTask.getTaskList());
                                }
                            }
                            break;

                        case ScreenAutomatorTask.JSON_TAG_NAME:
                            JSONArray screenArray = jo.optJSONArray(ScreenAutomatorTask.JSON_TAG_NAME);
                            if (screenArray != null) {
                                for (int i = 0; i < screenArray.length(); i++) {
                                    JSONObject screenObj = screenArray.getJSONObject(i);
                                    ScreenAutomatorTask screenTask = new ScreenAutomatorTask(screenObj);
                                    compositeTask.addTask(screenTask);
                                }
                            }
                            break;
                        case TextReportTask.JSON_TAG_NAME:
                            JSONArray textReportArray = jo.optJSONArray(TextReportTask.JSON_TAG_NAME);
                            if (textReportArray != null) {
                                for (int i = 0; i < textReportArray.length(); i++) {
                                    JSONObject screenObj = textReportArray.getJSONObject(i);
                                    TextReportTask textReportTask = new TextReportTask(screenObj);
                                    compositeTask.addTask(textReportTask);
                                }
                            }
                            break;
                        case CompositeTask.COMPOSITE_JSON_TAG_NAME:
                            JSONArray compositeArray = jo.optJSONArray(CompositeTask.COMPOSITE_JSON_TAG_NAME);
                            if (compositeArray != null) {
                                for (int i = 0; i < compositeArray.length(); i++) {
                                    JSONObject compObj = compositeArray.getJSONObject(i);
                                    PipelineTask subComposite = createPipeLineFromJson(compObj);
                                    compositeTask.addTask(subComposite);
                                }
                            }
                            break;
                    }
                }
            }
        }
        Log.i(TAG,"compositeTask before sort "+compositeTask);
        if (compositeTask != null)
            compositeTask.sort();
        Log.i(TAG,"compositeTask after sort "+compositeTask);
        return compositeTask;

    }

}

