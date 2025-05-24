package com.kam.andromate.controlService;

import android.content.Intent;

import com.kam.andromate.controlService.ControlServiceModels.entity.ClickInTextEntity;
import com.kam.andromate.controlService.ControlServiceModels.entity.ClickIn_X_Y_Entity;
import com.kam.andromate.controlService.ControlServiceModels.entity.ControlServiceEntity;
import com.kam.andromate.controlService.ControlServiceModels.entity.GlobalActionEntity;
import com.kam.andromate.controlService.ControlServiceModels.controlServiceTypes.ControlServiceActionType;
import com.kam.andromate.controlService.ControlServiceModels.controlServiceTypes.GlobalActionType;
import com.kam.andromate.controlService.ControlServiceModels.controlServiceException.ControlServiceErrorType;
import com.kam.andromate.controlService.ControlServiceModels.controlServiceException.ControlServiceException;
import com.kam.andromate.controlService.ControlServiceModels.controlServiceTypes.clickTextModels.CompareType;
import com.kam.andromate.controlService.ControlServiceModels.controlServiceTypes.clickTextModels.TextSelector;
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

    private final static String TAG_CLICK_IN_TEXT_ACTION_TYPE = "click_in_text_type";
    private final static String TAG_TEXT_SELECTOR_TYPE = "text_selector_type";
    private final static String TAG_TEXT_COMPARE_TYPE = "text_compare_type";
    private final static String TAG_TEXT_INDEX = "text_index";
    private final static String TAG_TEXT_VALUE_TAG = "text_value";

    private final static String TAG_CLICK_IN_XY_ACTION_TYPE = "click_in_x_y_type";
    private final static String TAG_X_VALUE = "x_value";
    private final static String TAG_Y_VALUE = "y_value";

    private static Intent createGlobalActionIntent(ScreenAutomatorTask screenAutomatorTask) throws ControlServiceException {
        Intent intent = new Intent(ControlServiceConstants.RECEIVER_ACTION_NAME);
        intent.putExtra(TAG_CONTROL_ACTION_TYPE, TAG_GLOBAL_ACTION_TYPE);
        intent.putExtra(TAG_GLOBAL_ACTION_VALUE, GlobalActionType.getGlobalActionTypeFromInteger(screenAutomatorTask.getGlobalAction_type()));
        return intent;
    }

    private static Intent createClickInTextActionIntent(ScreenAutomatorTask screenAutomatorTask) throws ControlServiceException {
        Intent intent = new Intent(ControlServiceConstants.RECEIVER_ACTION_NAME);
        intent.putExtra(TAG_CONTROL_ACTION_TYPE, TAG_CLICK_IN_TEXT_ACTION_TYPE);
        intent.putExtra(TAG_TEXT_SELECTOR_TYPE, TextSelector.getTextSelectorByAngularText(screenAutomatorTask.getClickInText_textSelector()));
        intent.putExtra(TAG_TEXT_COMPARE_TYPE, CompareType.getCompareTypeByAngularText(screenAutomatorTask.getClickInText_CompareType()));
        intent.putExtra(TAG_TEXT_INDEX, screenAutomatorTask.getClickInText_Index());
        intent.putExtra(TAG_TEXT_VALUE_TAG, screenAutomatorTask.getClickInText_text());
        return intent;
    }

    private static Intent createClickIn_XY_actionIntent(ScreenAutomatorTask screenAutomatorTask) throws ControlServiceException {
        Intent intent = new Intent(ControlServiceConstants.RECEIVER_ACTION_NAME);
        if (screenAutomatorTask.getClickInXY_X() < 0 || screenAutomatorTask.getClickInXY_Y() < 0) {
            throw new ControlServiceException(ControlServiceErrorType.INVALID_X_Y_INPUT);
        } else {
            intent.putExtra(TAG_CONTROL_ACTION_TYPE, TAG_CLICK_IN_XY_ACTION_TYPE);
            intent.putExtra(TAG_X_VALUE, screenAutomatorTask.getClickInXY_X());
            intent.putExtra(TAG_Y_VALUE, screenAutomatorTask.getClickInXY_Y());
        }
        return intent;
    }

    public static Intent ScreenAutomatorToIntent(ScreenAutomatorTask screenAutomatorTask) throws ControlServiceException {
        ControlServiceActionType actionType = ControlServiceActionType.getControlServiceActionTypeFromText(screenAutomatorTask.getAction_type());
        Intent screenAutomatorIntent = null;
        if (actionType == ControlServiceActionType.GLOBAL_ACTION) {
            screenAutomatorIntent = createGlobalActionIntent(screenAutomatorTask);
        } else if (actionType == ControlServiceActionType.CLICK_IN_TEXT) {
            screenAutomatorIntent = createClickInTextActionIntent(screenAutomatorTask);
        } else if (actionType == ControlServiceActionType.CLICK_IN_X_Y) {
            screenAutomatorIntent = createClickIn_XY_actionIntent(screenAutomatorTask);
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
            switch (Objects.requireNonNull(intent.getStringExtra(TAG_CONTROL_ACTION_TYPE))) {
                case TAG_GLOBAL_ACTION_TYPE:
                    try {
                        GlobalActionType globalActionType = (GlobalActionType) intent.getSerializableExtra(TAG_GLOBAL_ACTION_VALUE);
                        controlServiceEntity = new GlobalActionEntity(globalActionType);
                    } catch (Throwable t) {
                        throw new ControlServiceException(ControlServiceErrorType.INVALID_INTENT);
                    }
                    break;
                case TAG_CLICK_IN_TEXT_ACTION_TYPE:
                    try {
                        CompareType compareType = (CompareType) intent.getSerializableExtra(TAG_TEXT_COMPARE_TYPE);
                        TextSelector textSelector = (TextSelector) intent.getSerializableExtra(TAG_TEXT_SELECTOR_TYPE);
                        long textIndex = intent.getLongExtra(TAG_TEXT_INDEX, 0);
                        String text = intent.getStringExtra(TAG_TEXT_VALUE_TAG);
                        if (text == null) {
                            throw new ControlServiceException(ControlServiceErrorType.INVALID_INTENT);
                        }
                        controlServiceEntity = new ClickInTextEntity(compareType, textSelector, textIndex, text);
                    } catch (Throwable t) {
                        throw new ControlServiceException(ControlServiceErrorType.INVALID_INTENT);
                    }
                    break;
                case TAG_CLICK_IN_XY_ACTION_TYPE:
                    try {
                        long x = intent.getLongExtra(TAG_X_VALUE, -1);
                        long y = intent.getLongExtra(TAG_Y_VALUE, -1);
                        if (x < 0 || y <0) {
                            throw new ControlServiceException(ControlServiceErrorType.INVALID_INTENT);
                        }
                        controlServiceEntity = new ClickIn_X_Y_Entity(x, y);
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
