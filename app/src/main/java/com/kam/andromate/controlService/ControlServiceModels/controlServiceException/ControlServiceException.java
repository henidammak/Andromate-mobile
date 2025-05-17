package com.kam.andromate.controlService.ControlServiceModels.controlServiceException;

import androidx.annotation.NonNull;

public class ControlServiceException extends Exception {


    private final ControlServiceErrorType errorCode;

    public ControlServiceException(ControlServiceErrorType errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }

    public ControlServiceException(ControlServiceErrorType errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ControlServiceErrorType getErrorCode() {
        return errorCode;
    }

    @NonNull
    @Override
    public String toString() {
        return "CustomException{" +
                "errorCode=" + errorCode.getCode() +
                ", message=" + getMessage() +
                '}';
    }

}