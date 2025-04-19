package com.kam.andromate.model.baseStageModel;

import com.kam.andromate.IConstants;
import com.kam.andromate.model.BaseTask;
import com.kam.andromate.model.PipelineTask;

import org.json.JSONObject;

public class ScreenAutomatorTask extends BaseTask {

    public final static String TAG_ACTION_TYPE= "Action_type";
    public final static String TAG_GLOBAL_ACTION_TYPE= "GlobalAction_type";
    public final static String TAG_CLICK_IN_TEXT_SELECTOR= "ClickInText_textSelector";
    public final static String TAG_CLICK_IN_TEXT_COMPARE_TYPE= "ClickInText_CompareType";
    public final static String TAG_CLICK_IN_TEXT_INDEX= "ClickInText_Index";
    public final static String TAG_CLICK_IN_TEXT_TEXT= "ClickInText_text";
    public final static String TAG_CLICK_IN_XY_X= "ClickInXY_X";
    public final static String TAG_CLICK_IN_XY_Y= "ClickInXY_Y";
    public final static String TAG_LOGSCREEN= "LogScreen";


    public final static String DEFAULT_ACTION_TYPE= IConstants.EMPTY_STRING;
    public final static long DEFAULT_GLOBAL_ACTION_TYPE= 0;
    public final static String DEFAULT_CLICK_IN_TEXT_SELECTOR= IConstants.EMPTY_STRING;
    public final static String DEFAULT_CLICK_IN_TEXT_COMPARE_TYPE= IConstants.EMPTY_STRING;
    public final static long DEFAULT_CLICK_IN_TEXT_INDEX= 0;
    public final static String DEFAULT_CLICK_IN_TEXT_TEXT= IConstants.EMPTY_STRING;
    public final static long DEFAULT_CLICK_IN_XY_X= 0;
    public final static long DEFAULT_CLICK_IN_XY_Y= 0;
    public final static boolean DEFAULT_LOGSCREEN= false;

    private String Action_type;
    private long GlobalAction_type;
    private String ClickInText_textSelector;
    private String ClickInText_CompareType;
    private long ClickInText_Index;
    private String ClickInText_text;
    private long ClickInXY_X;
    private long ClickInXY_Y;
    private boolean LogScreen;

    public ScreenAutomatorTask(String action_type, long globalAction_type,
                               String clickInText_textSelector, String clickInText_CompareType,
                               long clickInText_Index, String clickInText_text,
                               long clickInXY_X, long clickInXY_Y, boolean logScreen) {
        this.Action_type = action_type;
        this.GlobalAction_type = globalAction_type;
        this.ClickInText_textSelector = clickInText_textSelector;
        this.ClickInText_CompareType = clickInText_CompareType;
        this.ClickInText_Index = clickInText_Index;
        this.ClickInText_text = clickInText_text;
        this.ClickInXY_X = clickInXY_X;
        this.ClickInXY_Y = clickInXY_Y;
        this.LogScreen = logScreen;
    }

    public ScreenAutomatorTask(JSONObject jo) {
        this.Action_type = jo.optString(TAG_ACTION_TYPE, DEFAULT_ACTION_TYPE);
        this.GlobalAction_type = jo.optLong(TAG_GLOBAL_ACTION_TYPE, DEFAULT_GLOBAL_ACTION_TYPE);
        this.ClickInText_textSelector = jo.optString(TAG_CLICK_IN_TEXT_SELECTOR, DEFAULT_CLICK_IN_TEXT_SELECTOR);
        this.ClickInText_CompareType = jo.optString(TAG_CLICK_IN_TEXT_COMPARE_TYPE, DEFAULT_CLICK_IN_TEXT_COMPARE_TYPE);
        this.ClickInText_Index = jo.optLong(TAG_CLICK_IN_TEXT_INDEX, DEFAULT_CLICK_IN_TEXT_INDEX);
        this.ClickInText_text = jo.optString(TAG_CLICK_IN_TEXT_TEXT, DEFAULT_CLICK_IN_TEXT_TEXT);
        this.ClickInXY_X = jo.optLong(TAG_CLICK_IN_XY_X, DEFAULT_CLICK_IN_XY_X);
        this.ClickInXY_Y = jo.optLong(TAG_CLICK_IN_XY_Y, DEFAULT_CLICK_IN_XY_Y);
        this.LogScreen = jo.optBoolean(TAG_LOGSCREEN, DEFAULT_LOGSCREEN);
    }

    public String getAction_type() {
        return Action_type;
    }

    public void setAction_type(String action_type) {
        Action_type = action_type;
    }

    public long getGlobalAction_type() {
        return GlobalAction_type;
    }

    public void setGlobalAction_type(long globalAction_type) {
        GlobalAction_type = globalAction_type;
    }

    public String getClickInText_textSelector() {
        return ClickInText_textSelector;
    }

    public void setClickInText_textSelector(String clickInText_textSelector) {
        ClickInText_textSelector = clickInText_textSelector;
    }

    public String getClickInText_CompareType() {
        return ClickInText_CompareType;
    }

    public void setClickInText_CompareType(String clickInText_CompareType) {
        ClickInText_CompareType = clickInText_CompareType;
    }

    public long getClickInText_Index() {
        return ClickInText_Index;
    }

    public void setClickInText_Index(long clickInText_Index) {
        ClickInText_Index = clickInText_Index;
    }

    public String getClickInText_text() {
        return ClickInText_text;
    }

    public void setClickInText_text(String clickInText_text) {
        ClickInText_text = clickInText_text;
    }

    public long getClickInXY_X() {
        return ClickInXY_X;
    }

    public void setClickInXY_X(long clickInXY_X) {
        ClickInXY_X = clickInXY_X;
    }

    public long getClickInXY_Y() {
        return ClickInXY_Y;
    }

    public void setClickInXY_Y(long clickInXY_Y) {
        ClickInXY_Y = clickInXY_Y;
    }

    public boolean isLogScreen() {
        return LogScreen;
    }

    public void setLogScreen(boolean logScreen) {
        LogScreen = logScreen;
    }

    @Override
    public PipelineTask jsonToPipeLine(JSONObject jo) {
        return new ScreenAutomatorTask(
                jo.optString(TAG_ACTION_TYPE,DEFAULT_ACTION_TYPE),
                jo.optLong(TAG_GLOBAL_ACTION_TYPE,DEFAULT_GLOBAL_ACTION_TYPE),
                jo.optString(TAG_CLICK_IN_TEXT_SELECTOR,DEFAULT_CLICK_IN_TEXT_SELECTOR),
                jo.optString(TAG_CLICK_IN_TEXT_COMPARE_TYPE,DEFAULT_CLICK_IN_TEXT_COMPARE_TYPE),
                jo.optLong(TAG_CLICK_IN_TEXT_INDEX,DEFAULT_CLICK_IN_TEXT_INDEX),
                jo.optString(TAG_CLICK_IN_TEXT_TEXT,DEFAULT_CLICK_IN_TEXT_TEXT),
                jo.optLong(TAG_CLICK_IN_XY_X,DEFAULT_CLICK_IN_XY_X),
                jo.optLong(TAG_CLICK_IN_XY_Y,DEFAULT_CLICK_IN_XY_Y),
                jo.optBoolean(TAG_LOGSCREEN,DEFAULT_LOGSCREEN)

                );
    }

    @Override
    public void executeTask() {

    }
}
