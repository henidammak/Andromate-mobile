package com.kam.andromate.View;

import android.widget.TextView;

import com.kam.andromate.View.fragmentUx.ReportSection;

public class MainReportSection {

    private static MainReportSection mainReportSection = null;
    private ReportSection reportSection = null;

    private MainReportSection(ReportSection reportSection) {
        this.reportSection = reportSection;
    }

    public static MainReportSection initInstance(TextView textView) {
        if (mainReportSection == null) {
            mainReportSection = new MainReportSection(new ReportSection(textView));
        }
        return mainReportSection;
    }

    public static MainReportSection getMainReportSection() {
        return mainReportSection;
    }

    public void appendMsg(String text) {
        reportSection.appendMsg(text);
    }

}
