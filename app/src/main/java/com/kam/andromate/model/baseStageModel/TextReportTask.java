package com.kam.andromate.model.baseStageModel;

import android.content.Context;

import com.kam.andromate.IConstants;
import com.kam.andromate.model.BaseTask;
import com.kam.andromate.model.PipelineTask;
import com.kam.andromate.model.taskContext.AndromateTaskContext;
import com.kam.andromate.model.taskResult.VoidResult;
import com.kam.andromate.view.MainReportSection;

import org.json.JSONObject;

public class TextReportTask extends BaseTask {

    public final static String JSON_TAG_NAME = "TextReport";

    enum TEXT_TYPE {
        ERROR,
        TITLE,
        INFO,
        UNSUPPORTED;

        public static TEXT_TYPE getTypeFromString(String textType) {
            TEXT_TYPE type = UNSUPPORTED;
            if (textType.equalsIgnoreCase("Error Text")) {
                type = ERROR;
            } else if (textType.equalsIgnoreCase("Info Text")) {
                type = INFO;
            } else if (textType.equalsIgnoreCase("Title Text")) {
                type = TITLE;
            }
            return type;
        }
    }

    private static final String TAG_TEXT = "texte";
    private static final String TAG_TEXT_TYPE = "texte_type";

    private static final String DEFAULT_TEXT = IConstants.EMPTY_STRING;
    private static final TEXT_TYPE DEFAULT_TEXT_TYPE = TEXT_TYPE.UNSUPPORTED;

    private final String text;
    private final TEXT_TYPE type;

    public TextReportTask(String idTask, String title, String textType, String text) {
        super(idTask, title);
        this.type = TEXT_TYPE.getTypeFromString(textType);
        this.text = text;
    }

    public TextReportTask(JSONObject jo) {
        super(jo.optString(PipelineTask.TAG_ID, PipelineTask.DEFAULT_ID),
                jo.optString(PipelineTask.TAG_TITLE, PipelineTask.DEFAULT_TITLE));
        this.type = TEXT_TYPE.getTypeFromString(jo.optString(TAG_TEXT_TYPE, IConstants.EMPTY_STRING));
        this.text = jo.optString(TAG_TEXT, DEFAULT_TEXT);
    }

    @Override
    public String getBaseTaskStartMsg() {
        return "text report task";
    }

    @Override
    public String getBaseTaskEndMsg() {
        return "text report task";
    }

    @Override
    public VoidResult executeBaseTask(MainReportSection rs, Context context, AndromateTaskContext andromateTaskContext) {
        switch (type) {
            case ERROR:
                rs.errorMsg(text);
                break;
            case INFO:
                rs.info(text);
                break;
            case TITLE:
                rs.appendTitle(text);
                break;
            case UNSUPPORTED:
                rs.errorMsg("unsupported text type received");
                break;
        }
        return new VoidResult();
    }

    @Override
    public PipelineTask jsonToPipeLine(JSONObject jo) {
        return null;
    }

    @Override
    public void resolveTaskWithContext(AndromateTaskContext andromateTaskContext) {

    }
}
