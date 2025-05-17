package com.kam.andromate.controlService.ControlServiceModels.controlServiceTypes;

import com.kam.andromate.controlService.ControlServiceModels.controlServiceException.ControlServiceErrorType;
import com.kam.andromate.controlService.ControlServiceModels.controlServiceException.ControlServiceException;

public enum ControlServiceActionType {

    GLOBAL_ACTION("Global Action"),
    CLICK_IN_TEXT("Click in Text"),
    CLICK_IN_X_Y("Click In (x, y)"),
    LOG_SCREEN("Log Screen"),
    ROOT_CLICK("Root Click");

    final String angularWebTextValue;

    ControlServiceActionType(String angularWebTextValue) {
        this.angularWebTextValue = angularWebTextValue;
    }

    public static ControlServiceActionType getControlServiceActionTypeFromText(String angularWebTextValue) throws ControlServiceException {
        ControlServiceActionType controlServiceActionTypeResult = null;
        for (ControlServiceActionType controlServiceActionType : ControlServiceActionType.values()) {
            if (controlServiceActionType.angularWebTextValue.equalsIgnoreCase(angularWebTextValue)) {
                controlServiceActionTypeResult = controlServiceActionType;
                break;
            }
        }
        if (controlServiceActionTypeResult == null) {
            throw new ControlServiceException(ControlServiceErrorType.INVALID_ACTION_TYPE);
        }
        return controlServiceActionTypeResult;
    }

}
