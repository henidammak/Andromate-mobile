package com.kam.andromate.model.baseStageModel;

import androidx.annotation.NonNull;

import com.kam.andromate.IConstants;
import com.kam.andromate.model.BaseTask;
import com.kam.andromate.model.PipelineTask;
import com.kam.andromate.utils.ThreadUtils.CmdHelper;
import com.kam.andromate.utils.ThreadUtils.CmdObserver;
import com.kam.andromate.utils.ThreadUtils.ThreadHelper;
import com.kam.andromate.view.MainReportSection;

import org.json.JSONObject;

public class AndroMateCmdTask extends BaseTask {

    public final static String TAG_CMD_TEXT = "cmd_text";
    public final static String TAG_CMD_ROOT = "root";


    public final static String DEFAULT_CMD_TEXT = IConstants.EMPTY_STRING;
    public final static boolean DEFAULT_CMD_ROOT = false;
    public final static String JSON_TAG_NAME = "CmdStage" ;


    private String cmdText;
    private boolean cmdRoot;

    public AndroMateCmdTask(String id, String title, String cmdText, boolean cmdRoot) {
        super(id, title);
        this.cmdText = cmdText;
        this.cmdRoot = cmdRoot;
    }

    public AndroMateCmdTask(JSONObject jo) {
        super(jo.optString(PipelineTask.TAG_ID, PipelineTask.DEFAULT_ID),
                jo.optString(PipelineTask.TAG_TITLE, PipelineTask.DEFAULT_TITLE));
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
                jo.optString(PipelineTask.TAG_ID, PipelineTask.DEFAULT_ID),
                jo.optString(PipelineTask.TAG_TITLE, PipelineTask.DEFAULT_TITLE),
                jo.optString(TAG_CMD_TEXT, DEFAULT_CMD_TEXT),
                jo.optBoolean(TAG_CMD_ROOT,DEFAULT_CMD_ROOT)
        );
    }

//    @Override
//    public void executeTask(MainReportSection rs) {
//        ThreadHelper.deepSleep(IConstants.SECONDS_VALUE);
//        rs.appendFmvKey("cmdTask", toString());
//    }

    @Override
    public void executeTask(MainReportSection rs) {
        String result = CmdHelper.executeCommand(cmdText, cmdRoot, new CmdObserver() {
            @Override
            public void onCommandSuccess(String resultCmd) {
                rs.info("Commande exécutée avec succès.");
                rs.appendFmvKey("Résultat", resultCmd);
            }

            @Override
            public void onCommandError(String errorCmd) {
                rs.errorMsg("Erreur lors de l'exécution de la commande : " + errorCmd);
            }
        });

        // Affiche toujours une trace, même si on a déjà loggé dans le callback
        rs.appendFmvKey("CmdTask", toString() + "\nRésultat brut:\n" + result);
    }



    @NonNull
    @Override
    public String toString() {
        return "AndroMateCmdTask{" +
                "cmdText='" + cmdText + '\'' +
                ",cmdRoot='" + cmdRoot + '\'' +
                '}';
    }


}
