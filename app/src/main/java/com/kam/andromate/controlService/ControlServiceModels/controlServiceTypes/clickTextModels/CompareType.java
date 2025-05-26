package com.kam.andromate.controlService.ControlServiceModels.controlServiceTypes.clickTextModels;

import com.kam.andromate.controlService.ControlServiceModels.controlServiceException.ControlServiceErrorType;
import com.kam.andromate.controlService.ControlServiceModels.controlServiceException.ControlServiceException;

public enum CompareType {

    EXACT_TEXT("exactText"),
    START_WITH("startWith"),
    CONTAIN("Contain");


    final String angularText;

    CompareType(String angularText) {
        this.angularText = angularText;
    }

    public static CompareType getCompareTypeByAngularText(String angularText) throws ControlServiceException {
        CompareType compareTypeResult = null;
        for (CompareType compareType : CompareType.values()) {
            if (compareType.angularText.equalsIgnoreCase(angularText)) {
                compareTypeResult = compareType;
                break;
            }
        }
        if (compareTypeResult == null) {
            throw new ControlServiceException(ControlServiceErrorType.UNSUPPORTED_TEXT_SELECTOR);
        }
        return compareTypeResult;
    }

}
