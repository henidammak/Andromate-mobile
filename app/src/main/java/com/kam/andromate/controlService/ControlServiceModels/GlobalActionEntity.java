package com.kam.andromate.controlService.ControlServiceModels;

import com.kam.andromate.controlService.ControlServiceModels.controlServiceTypes.GlobalActionType;

public class GlobalActionEntity implements ControlServiceEntity{

    GlobalActionType globalActionType = null;

    public GlobalActionEntity(GlobalActionType globalActionType) {
        this.globalActionType = globalActionType;
    }

}
