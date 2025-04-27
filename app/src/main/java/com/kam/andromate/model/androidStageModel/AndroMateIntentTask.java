package com.kam.andromate.model.androidStageModel;

import com.kam.andromate.IConstants;
import com.kam.andromate.model.AndroidTask;
import com.kam.andromate.model.PipelineTask;

import org.json.JSONObject;


public class AndroMateIntentTask extends AndroidTask {

    public final static String JSON_TAG_NAME = "Intent";

    public final static String TAG_INTENT_ACTION = "Action";
    public final static String TAG_INTENT_PACKAGE_NAME = "PackageName";
    public final static String TAG_INTENT_CLASS_NAME = "ClassName";
    public final static String TAG_INTENT_DATA = "Data";
    public final static String TAG_INTENT_ACTION_TYPE = "ActionType";
    
    
    
    public final static String DEFAULT_INTENT_ACTION = IConstants.EMPTY_STRING;
    public final static String DEFAULT_INTENT_PACKAGE_NAME = IConstants.EMPTY_STRING;
    public final static String DEFAULT_INTENT_CLASS_NAME = IConstants.EMPTY_STRING;
    public final static String DEFAULT_INTENT_DATA = IConstants.EMPTY_STRING;
    public final static String DEFAULT_INTENT_ACTION_TYPE = IConstants.EMPTY_STRING;
    
    
    
    private String intentAction;
    private String intentPackageName;
    private String intentClassName;
    private String intentData;
    private String intentActionType;

    public AndroMateIntentTask(String id, String title, String intentAction, String intentPackageName, String intentClassName,
                                String intentData, String intentActionType) {
        super(id, title);
        this.intentAction = intentAction;
        this.intentPackageName = intentPackageName;
        this.intentClassName = intentClassName;
        this.intentData = intentData;
        this.intentActionType = intentActionType;
    }

    public AndroMateIntentTask(JSONObject jo) {
        super(jo.optString(PipelineTask.TAG_ID, PipelineTask.DEFAULT_ID),
                jo.optString(PipelineTask.TAG_TITLE, PipelineTask.DEFAULT_TITLE));
        this.intentAction = jo.optString(TAG_INTENT_ACTION, DEFAULT_INTENT_ACTION);
        this.intentPackageName = jo.optString(TAG_INTENT_PACKAGE_NAME, DEFAULT_INTENT_PACKAGE_NAME);
        this.intentClassName = jo.optString(TAG_INTENT_CLASS_NAME, DEFAULT_INTENT_CLASS_NAME);
        this.intentData = jo.optString(TAG_INTENT_DATA, DEFAULT_INTENT_DATA);
        this.intentActionType = jo.optString(TAG_INTENT_ACTION_TYPE, DEFAULT_INTENT_ACTION_TYPE);
    }
    
    public String getIntentAction() {
        return intentAction;
    }

    public void setIntentAction(String intentAction) {
        this.intentAction = intentAction;
    }

    public String getIntentPackageName() {
        return intentPackageName;
    }

    public void setIntentPackageName(String intentPackageName) {
        this.intentPackageName = intentPackageName;
    }

    public String getIntentClassName() {
        return intentClassName;
    }

    public void setIntentClassName(String intentClassName) {
        this.intentClassName = intentClassName;
    }

    public String getIntentData() {
        return intentData;
    }

    public void setIntentData(String intentData) {
        this.intentData = intentData;
    }

    public String getIntentActionType() {
        return intentActionType;
    }

    public void setIntentActionType(String intentActionType) {
        this.intentActionType = intentActionType;
    }

    @Override
    public PipelineTask jsonToPipeLine(JSONObject jo) {
        return new AndroMateIntentTask(
                jo.optString(PipelineTask.TAG_ID, PipelineTask.DEFAULT_ID),
                jo.optString(PipelineTask.TAG_TITLE, PipelineTask.DEFAULT_TITLE),
                jo.optString(TAG_INTENT_ACTION, DEFAULT_INTENT_ACTION),
                jo.optString(TAG_INTENT_PACKAGE_NAME, DEFAULT_INTENT_PACKAGE_NAME),
                jo.optString(TAG_INTENT_CLASS_NAME, DEFAULT_INTENT_CLASS_NAME),
                jo.optString(TAG_INTENT_DATA, DEFAULT_INTENT_DATA),
                jo.optString(TAG_INTENT_ACTION_TYPE, DEFAULT_INTENT_ACTION_TYPE)
        );
    }

    @Override
    public void executeTask() {

    }


    @Override
    public String toString() {
        return "AndroMateIntentTask{" +
                "intentAction='" + intentAction + '\'' +
                ", intentPackageName='" + intentPackageName + '\'' +
                ", intentClassName='" + intentClassName + '\'' +
                ", intentData='" + intentData + '\'' +
                ", intentActionType='" + intentActionType + '\'' +
                '}';
    }
}
