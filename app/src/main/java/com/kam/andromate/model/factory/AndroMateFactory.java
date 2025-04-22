package com.kam.andromate.model.factory;

import android.util.Log;

import com.kam.andromate.model.CompositeTask;
import com.kam.andromate.model.PipelineTask;
import com.kam.andromate.model.androidStageModel.AndroMateIntentTask;
import com.kam.andromate.model.androidStageModel.AndroMateSleepTask;
import com.kam.andromate.model.baseStageModel.AndroMateCmdTask;
import com.kam.andromate.model.baseStageModel.ScreenAutomatorTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AndroMateFactory {

    private final static String TAG = "AndroMateFactory";

    private final static String START_NODE_NAME = "Start";
    private final static String END_NODE_NAME = "End";
    private final static String LINK_NODE_NAME = "Links";



    public static PipelineTask createPipeLineFromJson(JSONObject jo) throws JSONException {
        CompositeTask compositeTask = new CompositeTask(jo.optString(PipelineTask.TAG_ID), jo.optString(PipelineTask.TAG_TITLE));
        if (jo != null) {
            JSONArray tags = jo.names();
            if (tags != null) {
                String currentTag = null;
                for (int tagIndex = 0; tagIndex < tags.length(); tagIndex++) {
                    currentTag = tags.getString(tagIndex);
                    Log.i(TAG, "current tag received + "+currentTag);
                    switch (currentTag) {
                        case START_NODE_NAME:
                            // set the param of composite task !!! as_thread_tag, timeout ...
                            break;

                        case END_NODE_NAME:
                            break;

                        case LINK_NODE_NAME:
                            JSONArray linkArray = jo.optJSONArray(LINK_NODE_NAME);
                            for (int i = 0; i < linkArray.length(); i++) {
                                JSONObject linkObj = linkArray.getJSONObject(i);
                                Log.i(TAG,"linkObj "+linkObj);
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
                                        Log.i(TAG," sleepTask "+sleepTask);
                                        compositeTask.addTask(sleepTask);
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
                                    compositeTask.addTask(cmdTask);
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
        return compositeTask;
    }

}

