package com.kam.andromate.controlService.ControlServiceModels.entity;

import com.kam.andromate.controlService.ControlServiceModels.controlServiceTypes.clickTextModels.CompareType;
import com.kam.andromate.controlService.ControlServiceModels.controlServiceTypes.clickTextModels.TextSelector;

public class ClickInTextEntity implements ControlServiceEntity{

    CompareType compareType;
    TextSelector textSelector;
    long textIndex;
    String text;

    public ClickInTextEntity(CompareType compareType, TextSelector textSelector, long textIndex, String text) {
        this.compareType = compareType;
        this.textSelector = textSelector;
        this.textIndex = textIndex;
        this.text = text;
    }

    public CompareType getCompareType() {
        return compareType;
    }

    public TextSelector getTextSelector() {
        return textSelector;
    }

    public long getTextIndex() {
        return textIndex;
    }

    public String getText() {
        return text;
    }
}
