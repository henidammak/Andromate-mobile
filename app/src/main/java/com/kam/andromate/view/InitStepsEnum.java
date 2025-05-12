package com.kam.andromate.view;

public enum InitStepsEnum {//enum to define steps to init app

    INIT("Init apk"),
    CHECK_DEVICE_PERMISSION("Check device permission"),
    CHECK_APP_PERMISSIONS("Check app permission"),
    CHECK_MESSAGING_CONNECTION("Check connection"),
    FINISH("Finish");


    public final String stepName;

    private InitStepsEnum(String stepName) {
        this.stepName = stepName;
    }

}
