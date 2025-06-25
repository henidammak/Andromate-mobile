package com.kam.andromate.model.baseStageModel;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.kam.andromate.IConstants;
import com.kam.andromate.controlService.ControlServiceConstants;
import com.kam.andromate.controlService.ControlServiceFactory;
import com.kam.andromate.controlService.ControlServiceModels.ControlServiceSync;
import com.kam.andromate.controlService.ControlServiceModels.controlServiceException.ControlServiceException;
import com.kam.andromate.model.BaseTask;
import com.kam.andromate.model.PipelineTask;
import com.kam.andromate.model.taskContext.AndromateTaskContext;
import com.kam.andromate.model.taskResult.ScreenAutomatorResult;
import com.kam.andromate.utils.DeviceUtils;
import com.kam.andromate.utils.ThreadUtils.AndroMateSynchronizer;
import com.kam.andromate.utils.ThreadUtils.ThreadHelper;
import com.kam.andromate.view.MainReportSection;

import org.json.JSONObject;

import java.util.concurrent.TimeoutException;

public class ScreenAutomatorTask extends BaseTask {

    public final static String JSON_TAG_NAME = "ScreenAutomator";

    public final static String TAG_ACTION_TYPE= "Action_type";
    public final static String TAG_GLOBAL_ACTION_TYPE= "GlobalAction_type";
    public final static String TAG_CLICK_IN_TEXT_SELECTOR= "ClickInText_textSelector";
    public final static String TAG_CLICK_IN_TEXT_COMPARE_TYPE= "ClickInText_CompareType";
    public final static String TAG_CLICK_IN_TEXT_INDEX= "ClickInText_Index";
    public final static String TAG_CLICK_IN_TEXT_TEXT= "ClickInText_text";
    public final static String TAG_CLICK_IN_XY_X= "ClickInXY_X";
    public final static String TAG_CLICK_IN_XY_Y= "ClickInXY_Y";
    public final static String TAG_LOG_SCREEN= "LogScreen";


    public final static String DEFAULT_ACTION_TYPE= IConstants.EMPTY_STRING;
    public final static long DEFAULT_GLOBAL_ACTION_TYPE= 0;
    public final static String DEFAULT_CLICK_IN_TEXT_SELECTOR= IConstants.EMPTY_STRING;
    public final static String DEFAULT_CLICK_IN_TEXT_COMPARE_TYPE= IConstants.EMPTY_STRING;
    public final static long DEFAULT_CLICK_IN_TEXT_INDEX= 0;
    public final static String DEFAULT_CLICK_IN_TEXT_TEXT= IConstants.EMPTY_STRING;
    public final static long DEFAULT_CLICK_IN_XY_X= 0;
    public final static long DEFAULT_CLICK_IN_XY_Y= 0;
    public final static boolean DEFAULT_LOG_SCREEN= false;



    private String action_type;
    private long globalAction_type;
    private String clickInText_textSelector;
    private String clickInText_CompareType;
    private long clickInText_Index;
    private String clickInText_text;
    private long clickInXY_X;
    private long clickInXY_Y;
    private boolean log_screen;

    public ScreenAutomatorTask(String id, String title, String action_type, long globalAction_type,
                               String clickInText_textSelector, String clickInText_CompareType,
                               long clickInText_Index, String clickInText_text, long clickInXY_X,
                               long clickInXY_Y, boolean log_screen) {
        super(id, title);
        this.action_type = action_type;
        this.globalAction_type = globalAction_type;
        this.clickInText_textSelector = clickInText_textSelector;
        this.clickInText_CompareType = clickInText_CompareType;
        this.clickInText_Index = clickInText_Index;
        this.clickInText_text = clickInText_text;
        this.clickInXY_X = clickInXY_X;
        this.clickInXY_Y = clickInXY_Y;
        this.log_screen = log_screen;
    }

    public ScreenAutomatorTask(JSONObject jo) {
        super(jo.optString(PipelineTask.TAG_ID, PipelineTask.DEFAULT_ID),
                jo.optString(PipelineTask.TAG_TITLE, PipelineTask.DEFAULT_TITLE));
        this.action_type = jo.optString(TAG_ACTION_TYPE,DEFAULT_ACTION_TYPE);
        this.globalAction_type = jo.optLong(TAG_GLOBAL_ACTION_TYPE,DEFAULT_GLOBAL_ACTION_TYPE);
        this.clickInText_textSelector = jo.optString(TAG_CLICK_IN_TEXT_SELECTOR,DEFAULT_CLICK_IN_TEXT_SELECTOR);
        this.clickInText_CompareType = jo.optString(TAG_CLICK_IN_TEXT_COMPARE_TYPE,DEFAULT_CLICK_IN_TEXT_COMPARE_TYPE);
        this.clickInText_Index = jo.optLong(TAG_CLICK_IN_TEXT_INDEX,DEFAULT_CLICK_IN_TEXT_INDEX);
        this.clickInText_text = jo.optString(TAG_CLICK_IN_TEXT_TEXT,DEFAULT_CLICK_IN_TEXT_TEXT);
        this.clickInXY_X = jo.optLong(TAG_CLICK_IN_XY_X,DEFAULT_CLICK_IN_XY_X);
        this.clickInXY_Y = jo.optLong(TAG_CLICK_IN_XY_Y,DEFAULT_CLICK_IN_XY_Y);
        this.log_screen = jo.optBoolean(TAG_LOG_SCREEN,DEFAULT_LOG_SCREEN);
    }

    public String getAction_type() {
        return action_type;
    }

    public void setAction_type(String action_type) {
        this.action_type = action_type;
    }

    public int getGlobalAction_type() {
        return (int) globalAction_type;
    }

    public void setGlobalAction_type(long globalAction_type) {
        this.globalAction_type = globalAction_type;
    }

    public String getClickInText_textSelector() {
        return clickInText_textSelector;
    }

    public void setClickInText_textSelector(String clickInText_textSelector) {
        this.clickInText_textSelector = clickInText_textSelector;
    }

    public String getClickInText_CompareType() {
        return clickInText_CompareType;
    }

    public void setClickInText_CompareType(String clickInText_CompareType) {
        this.clickInText_CompareType = clickInText_CompareType;
    }

    public long getClickInText_Index() {
        return clickInText_Index;
    }

    public void setClickInText_Index(long clickInText_Index) {
        this.clickInText_Index = clickInText_Index;
    }

    public String getClickInText_text() {
        return clickInText_text;
    }

    public void setClickInText_text(String clickInText_text) {
        this.clickInText_text = clickInText_text;
    }

    public long getClickInXY_X() {
        return clickInXY_X;
    }

    public void setClickInXY_X(long clickInXY_X) {
        this.clickInXY_X = clickInXY_X;
    }

    public long getClickInXY_Y() {
        return clickInXY_Y;
    }

    public void setClickInXY_Y(long clickInXY_Y) {
        this.clickInXY_Y = clickInXY_Y;
    }

    public boolean isLog_screen() {
        return log_screen;
    }

    public void setLog_screen(boolean log_screen) {
        this.log_screen = log_screen;
    }

    @Override
    public PipelineTask jsonToPipeLine(JSONObject jo) {
        return new ScreenAutomatorTask(
                jo.optString(PipelineTask.TAG_ID, PipelineTask.DEFAULT_ID),
                jo.optString(PipelineTask.TAG_TITLE, PipelineTask.DEFAULT_TITLE),
                jo.optString(TAG_ACTION_TYPE,DEFAULT_ACTION_TYPE),
                jo.optLong(TAG_GLOBAL_ACTION_TYPE,DEFAULT_GLOBAL_ACTION_TYPE),
                jo.optString(TAG_CLICK_IN_TEXT_SELECTOR,DEFAULT_CLICK_IN_TEXT_SELECTOR),
                jo.optString(TAG_CLICK_IN_TEXT_COMPARE_TYPE,DEFAULT_CLICK_IN_TEXT_COMPARE_TYPE),
                jo.optLong(TAG_CLICK_IN_TEXT_INDEX,DEFAULT_CLICK_IN_TEXT_INDEX),
                jo.optString(TAG_CLICK_IN_TEXT_TEXT,DEFAULT_CLICK_IN_TEXT_TEXT),
                jo.optLong(TAG_CLICK_IN_XY_X,DEFAULT_CLICK_IN_XY_X),
                jo.optLong(TAG_CLICK_IN_XY_Y,DEFAULT_CLICK_IN_XY_Y),
                jo.optBoolean(TAG_LOG_SCREEN,DEFAULT_LOG_SCREEN)

                );
    }

    @Override
    public void resolveTaskWithContext(AndromateTaskContext andromateTaskContext) {

    }

    @Override
    public String getBaseTaskStartMsg() {
        return "Screen Automator TYPE:"+action_type ;
    }

    @Override
    public String getBaseTaskEndMsg() {
        return "Screen Automator";
    }

    @Override
    public ScreenAutomatorResult executeBaseTask(MainReportSection rs, Context context, AndromateTaskContext andromateTaskContext) {
        ScreenAutomatorResult screenAutomatorResult = new ScreenAutomatorResult();
        boolean intentCreated = false;
        try {
            Intent intent = ControlServiceFactory.ScreenAutomatorToIntent(this);
            context.sendBroadcast(intent);
            intentCreated = true;
        } catch (ControlServiceException e) {
            String errorMsg = e.getErrorCode().getDescription();
            String errorMsgDetail = null;
            switch (e.getErrorCode()) {
                case INVALID_ACTION_TYPE:
                    errorMsgDetail="action type = "+action_type;
                    break;
                case INVALID_INTENT:
                    break;
                case INVALID_X_Y_INPUT:
                    errorMsgDetail = "negative x or y input";
                    break;
                case INVALID_CONTROL_SERVICE_GLOBAL_ACTION:
                    errorMsgDetail = globalAction_type + " not exist";
                    break;
                case UNSUPPORTED_CONTROL_SERVICE_GLOBAL_ACTION:
                    errorMsgDetail = globalAction_type+" cannot performed in sdk "+ DeviceUtils.getDeviceSdk();
                    break;
            }
            if (errorMsgDetail != null) {
                errorMsg = errorMsg + " : "+errorMsgDetail;
            }
            rs.errorMsg(errorMsg);
            screenAutomatorResult.setErrorMsg(errorMsg);
            ThreadHelper.deepSleep(2 * IConstants.SECONDS_VALUE);
        } catch (Throwable t) {
            rs.errorMsg("error not supported "+t);
            screenAutomatorResult.setErrorMsg(t.toString());
        }
        if (intentCreated) {
            try {
                AndroMateSynchronizer<Boolean, ControlServiceException> sync = ControlServiceSync.getInstance()
                        .waitNotification(ControlServiceConstants.DEFAULT_CONTROL_SERVICE_TIME_OUT_MS);
                if (sync.result) {
                    rs.info("action performed");
                } else {
                    rs.errorMsg("action cannot performed");
                    screenAutomatorResult.setErrorMsg("action cannot performed");
                }
            } catch (TimeoutException e) {
                rs.errorMsg("timeout action expired "+ControlServiceConstants.DEFAULT_CONTROL_SERVICE_TIME_OUT_MS);
                screenAutomatorResult.setErrorMsg("timeout action expired "+ControlServiceConstants.DEFAULT_CONTROL_SERVICE_TIME_OUT_MS);
            } catch (ControlServiceException e) {
                rs.errorMsg("exception received from control service "+e.getErrorCode().getCode());
                screenAutomatorResult.setErrorMsg("exception received from control service "+e.getErrorCode().getCode());
            }
        }
        if (screenAutomatorResult.getErrorMsg() != null && !screenAutomatorResult.getErrorMsg().isEmpty()) {
            screenAutomatorResult.setAutomatorResult(false);
        } else {
            screenAutomatorResult.setAutomatorResult(true);
        }
        return screenAutomatorResult;
    }

    @NonNull
    @Override
    public String toString() {
        return "ScreenAutomatorTask{" +
                "action_type='" + action_type + '\'' +
                ", globalAction_type=" + globalAction_type +
                ", clickInText_textSelector='" + clickInText_textSelector + '\'' +
                ", clickInText_CompareType='" + clickInText_CompareType + '\'' +
                ", clickInText_Index=" + clickInText_Index +
                ", clickInText_text='" + clickInText_text + '\'' +
                ", clickInXY_X=" + clickInXY_X +
                ", clickInXY_Y=" + clickInXY_Y +
                ", log_screen=" + log_screen +
                '}';
    }

}
