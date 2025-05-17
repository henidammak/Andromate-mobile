package com.kam.andromate.controlService;

import android.content.Intent;

import com.kam.andromate.controlService.ControlServiceModels.ControlServiceEntity;
import com.kam.andromate.controlService.ControlServiceModels.GlobalActionEntity;
import com.kam.andromate.controlService.ControlServiceModels.controlServiceTypes.ControlServiceActionType;
import com.kam.andromate.controlService.ControlServiceModels.controlServiceTypes.GlobalActionType;
import com.kam.andromate.controlService.ControlServiceModels.controlServiceException.ControlServiceErrorType;
import com.kam.andromate.controlService.ControlServiceModels.controlServiceException.ControlServiceException;
import com.kam.andromate.model.baseStageModel.ScreenAutomatorTask;

import java.util.Objects;

/*
* class to create intent to send it to control service and extract entity from intent in service to perform action
* controlService actions can only perform in BaseControlServiceClass
* this class will be used in brodcast receiver in BaseControlService
* */
public class ControlServiceFactory {

    private final static String TAG_CONTROL_ACTION_TYPE = "control_action_type";

    private final static String TAG_GLOBAL_ACTION_TYPE = "global_action_type";
    private final static String TAG_GLOBAL_ACTION_VALUE = "global_action_value";

    private static Intent createGlobalActionIntent(ScreenAutomatorTask screenAutomatorTask) throws ControlServiceException {
        Intent intent = new Intent(ControlServiceConstants.RECEIVER_ACTION_NAME);
        intent.putExtra(TAG_CONTROL_ACTION_TYPE, TAG_GLOBAL_ACTION_TYPE);
        intent.putExtra(TAG_GLOBAL_ACTION_VALUE, GlobalActionType.getGlobalActionTypeFromInteger(screenAutomatorTask.getGlobalAction_type()));
        return intent;
    }

    public static Intent ScreenAutomatorToIntent(ScreenAutomatorTask screenAutomatorTask) throws ControlServiceException {
        ControlServiceActionType actionType = ControlServiceActionType.getControlServiceActionTypeFromText(screenAutomatorTask.getAction_type());
        Intent screenAutomatorIntent = null;
        if (actionType == ControlServiceActionType.GLOBAL_ACTION) {
            screenAutomatorIntent = createGlobalActionIntent(screenAutomatorTask);
        } else {
            throw new ControlServiceException(ControlServiceErrorType.INVALID_ACTION_TYPE);
        }
        return screenAutomatorIntent;
    }

    public static ControlServiceEntity CreateControlServiceEntityFromIntent(Intent intent) throws ControlServiceException {
        ControlServiceEntity controlServiceEntity = null;
        if (intent == null || intent.getStringExtra(TAG_CONTROL_ACTION_TYPE) == null) {
            throw new ControlServiceException(ControlServiceErrorType.INVALID_INTENT);
        } else {
            switch (Objects.requireNonNull(intent.getStringExtra(TAG_GLOBAL_ACTION_TYPE))) {
                case TAG_CONTROL_ACTION_TYPE:
                    try {
                        GlobalActionType globalActionType = (GlobalActionType) intent.getSerializableExtra(TAG_GLOBAL_ACTION_VALUE);
                        controlServiceEntity = new GlobalActionEntity(globalActionType);
                    } catch (Throwable t) {
                        throw new ControlServiceException(ControlServiceErrorType.INVALID_INTENT);
                    }
                    break;
                default:
                    throw new ControlServiceException(ControlServiceErrorType.INVALID_ACTION_TYPE);
            }
        }
        return controlServiceEntity;
    }


}
