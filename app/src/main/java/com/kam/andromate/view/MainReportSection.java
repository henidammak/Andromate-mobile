package com.kam.andromate.view;

import android.widget.TextView;

import com.kam.andromate.AndroMateDevice;
import com.kam.andromate.utils.TimeUtils;
import com.kam.andromate.view.fragmentUx.ReportSection;

public class MainReportSection extends ReportSection{


    public MainReportSection(TextView terminalView) {
        super(terminalView);
    }

    public void initAndroMateReportInfo() {
        appendFmvKey("Date", TimeUtils.getCurrentTimeAsSimpleFormat());
        AndroMateDevice androMateDevice = AndroMateDevice.getInstance();
        if (androMateDevice != null) {
            appendTitle("Informations appareil");
            incMargin();
            appendFmvKey("Device factory", androMateDevice.getDeviceFactory());
            appendFmvKey("Device id", androMateDevice.getDeviceId());
            appendFmvKey("Hardware", androMateDevice.getCpuHardware());
            appendFmvKey("Resolution", androMateDevice.getScreenResolution());
            discMargin();
        }
    }

}
