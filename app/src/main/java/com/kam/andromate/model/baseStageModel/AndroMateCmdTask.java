package com.kam.andromate.model.baseStageModel;

import com.kam.andromate.IConstants;
import com.kam.andromate.model.BaseTask;
import com.kam.andromate.model.PipelineTask;

import org.json.JSONObject;

public class AndroMateCmdTask extends BaseTask {

    public final static String JSON_TAG_NAME = "Cmd Stage";

    public final static String TAG_CMD_TEXT = "cmd_text";
    public final static String TAG_CMD_ROOT = "root";


    public final static String DEFAULT_CMD_TEXT = IConstants.EMPTY_STRING;
    public final static boolean DEFAULT_CMD_ROOT = false;


    private String cmdText;
    private boolean cmdRoot;

    public AndroMateCmdTask(String cmdText, boolean cmdRoot) {
        this.cmdText = cmdText;
        this.cmdRoot = cmdRoot;
    }

    public AndroMateCmdTask(JSONObject jo) {
        this.cmdText = jo.optString(TAG_CMD_TEXT, DEFAULT_CMD_TEXT);
        this.cmdRoot = jo.optBoolean(TAG_CMD_ROOT,DEFAULT_CMD_ROOT);
    }

    public String getCmdText() {
        return cmdText;
    }

    public void setCmdText(String cmdText) {
        this.cmdText = cmdText;
    }

    public boolean isCmdRoot() {
        return cmdRoot;
    }

    public void setCmdRoot(boolean cmdRoot) {
        this.cmdRoot = cmdRoot;
    }


    @Override
    public PipelineTask jsonToPipeLine(JSONObject jo) {
        return new AndroMateCmdTask(
                jo.optString(TAG_CMD_TEXT, DEFAULT_CMD_TEXT),
                jo.optBoolean(TAG_CMD_ROOT,DEFAULT_CMD_ROOT)
        );
    }

    @Override
    public void executeTask() {

    }

    @Override
    protected String getJsonTaskTagName() {
        return "Cmd Stage";
    }
}
