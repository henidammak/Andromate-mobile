package com.kam.andromate.controlService.ControlServiceModels.controlServiceException;

public enum ControlServiceErrorType {



    INVALID_CONTROL_SERVICE_GLOBAL_ACTION("E001", "Invalid global action"),
    UNSUPPORTED_CONTROL_SERVICE_GLOBAL_ACTION("E002", "Unsupported control service global action"),
    INVALID_ACTION_TYPE("E003","Invalid Action Type"),
    INVALID_X_Y_INPUT("E004","Invalid x y input"),
    INVALID_INTENT("E005", "Invalid Intent Received"),
    UNSUPPORTED_TEXT_SELECTOR("E006", "Invalid text selector received");

    private final String code;
    private final String description;

    ControlServiceErrorType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}
