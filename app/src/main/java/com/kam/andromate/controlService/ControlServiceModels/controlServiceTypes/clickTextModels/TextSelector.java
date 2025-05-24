package com.kam.andromate.controlService.ControlServiceModels.controlServiceTypes.clickTextModels;

import com.kam.andromate.controlService.ControlServiceModels.controlServiceException.ControlServiceErrorType;
import com.kam.andromate.controlService.ControlServiceModels.controlServiceException.ControlServiceException;

public enum TextSelector {

    TEXT_SELECTOR("Text"),
    CONTENT_DESCRIPTION("Content Description"),
    TOOL_TIP_TEXT("Tooltip text");


    final String angularText;

    TextSelector(String angularText) {
        this.angularText = angularText;
    }

    public static TextSelector getTextSelectorByAngularText(String angularText) throws ControlServiceException {
        TextSelector textSelectorResult = null;
        for (TextSelector textSelector : TextSelector.values()) {
            if (textSelector.angularText.equalsIgnoreCase(angularText)) {
                textSelectorResult = textSelector;
                break;
            }
        }
        if (textSelectorResult == null) {
            throw new ControlServiceException(ControlServiceErrorType.UNSUPPORTED_TEXT_SELECTOR);
        }
        return textSelectorResult;
    }

}
