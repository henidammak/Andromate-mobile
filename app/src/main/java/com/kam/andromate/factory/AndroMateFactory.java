package com.kam.andromate.factory;

import com.kam.andromate.IConstants;
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

    private final static String START_NODE_NAME = "Start";
    private final static String END_NODE_NAME = "End";
    private final static String LINK_NODE_NAME = "Links";


    public PipelineTask createPipeLineFromJson(JSONObject jo) throws JSONException {
        CompositeTask compositeTask = new CompositeTask();
        if (jo != null) {
            JSONArray tags = jo.names();
            if (tags != null) {
                String currentTag = null;
                for (int tagIndex=0; tagIndex<tags.length(); tagIndex++) {
                    currentTag = tags.getString(tagIndex);
                    if (currentTag != null) {
                        switch (currentTag) {
                            case START_NODE_NAME:
                                //set the param of composite task !!! as_thread_tag, timeout ...
                                break;
                            case END_NODE_NAME:
                                break;
                            case LINK_NODE_NAME:
                                //create Link object
                                break;
                            case AndroMateIntentTask.JSON_TAG_NAME:
                                break;
                            case AndroMateSleepTask.JSON_TAG_NAME:
                                break;
                            case AndroMateCmdTask.JSON_TAG_NAME:
                                break;
                            case ScreenAutomatorTask.JSON_TAG_NAME:
                                break;
                            case IConstants.COMPOSITE_JSON_TAG_NAME:
                                //here we should extract the jo object and call create pipeline from json
                                break;
                        }
                    }
                }
            }

        }
        return compositeTask;
    }

}
