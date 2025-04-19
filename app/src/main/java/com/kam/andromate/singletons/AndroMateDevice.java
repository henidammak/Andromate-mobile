package com.kam.andromate.singletons;

import android.content.Context;

import com.kam.andromate.utils.DeviceUtils;

public class AndroMateDevice {

    private final String deviceId;
    private final String cpuHardware;
    private final String screenResolution;
    private final String deviceFactory;

    public String getDeviceFactory() {
        return deviceFactory;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getCpuHardware() {
        return cpuHardware;
    }

    public String getScreenResolution() {
        return screenResolution;
    }
    private static AndroMateDevice instance = null;

    private AndroMateDevice(String deviceId, String cpuHardware, String screenResolution, String deviceFactory) {
        this.deviceId = deviceId;//device Id
        this.cpuHardware = cpuHardware;//cpu factory like intel
        this.screenResolution = screenResolution;//screen resolution like 1920*1080
        this.deviceFactory = deviceFactory;
    }

    public static AndroMateDevice getInstance() {
        return instance;
    }

    public static void setInstance(Context context) {
        if (instance == null) {
            instance = new AndroMateDevice(
                    DeviceUtils.getCurrentDeviceId(context),
                    DeviceUtils.getCpuHardware(),
                    DeviceUtils.getScreenResolution(context),
                    DeviceUtils.getDeviceFactory()
            );
        }
    }

}
