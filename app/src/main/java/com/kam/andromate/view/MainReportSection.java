package com.kam.andromate.view;

import android.widget.TextView;

import com.kam.andromate.IConstants;
import com.kam.andromate.singletons.AndroMateApp;
import com.kam.andromate.singletons.AndroMateDevice;
import com.kam.andromate.utils.TimeUtils;
import com.kam.andromate.view.fragmentUx.ReportSection;

public class MainReportSection extends ReportSection{


    public MainReportSection(TextView terminalView) {
        super(terminalView);
    }

    public void initAndroMateReportInfo() {
        appendFmvKey("Date", TimeUtils.getCurrentDateAsSimpleFormat());
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
        AndroMateApp androMateApp = AndroMateApp.getInstance();
        if (androMateApp != null) {
            appendTitle("Informations application");
            incMargin();
            appendFmvKey("version", androMateApp.getVersionName());
            discMargin();
        }
        appendTitle("Messaging info");
        incMargin();
        appendFmvKey("type", "Web socket");
        appendFmvKey("ip", IConstants.WEB_SOCKET_DOMAIN);
        appendFmvKey("port", IConstants.WEB_SOCKET_PORT+"");
    }

}
