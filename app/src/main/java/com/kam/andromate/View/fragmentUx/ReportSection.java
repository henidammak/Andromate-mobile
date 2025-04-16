package com.kam.andromate.View.fragmentUx;

import android.widget.TextView;

import com.kam.andromate.utils.TimeUtils;

public class ReportSection {

    TextView terminalView = null;

    public ReportSection(TextView terminalView) {
        this.terminalView = terminalView;
    }

    public void appendMsg(String text) {
        terminalView.append("["+TimeUtils.getCurrentTimeAsSimpleFormat()+"]" + text + "\n");
    }

}
