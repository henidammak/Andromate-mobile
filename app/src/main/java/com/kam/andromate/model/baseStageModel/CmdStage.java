package com.kam.andromate.model.baseStageModel;

import com.kam.andromate.IConstants;
import com.kam.andromate.model.BaseStage;
import com.kam.andromate.model.PipelineStage;

import org.json.JSONObject;

public class CmdStage extends BaseStage {

    public final static String TAG_CMD_TEXT = "cmd_text";
    public final static String TAG_CMD_ROOT = "root";


    public final static String DEFAULT_CMD_TEXT = IConstants.EMPTY_STRING;
    public final static boolean DEFAULT_CMD_ROOT = false;


    private String cmdText;
    private boolean cmdRoot;

    public CmdStage(String cmdText, boolean cmdRoot) {
        this.cmdText = cmdText;
        this.cmdRoot = cmdRoot;
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
    public PipelineStage jsonToPipeLine(JSONObject jo) {
        return new CmdStage(
                jo.optString(TAG_CMD_TEXT, DEFAULT_CMD_TEXT),
                jo.optBoolean(TAG_CMD_ROOT,DEFAULT_CMD_ROOT)
        );
    }
}
