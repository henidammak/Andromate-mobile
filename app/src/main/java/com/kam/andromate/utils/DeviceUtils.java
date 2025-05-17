package com.kam.andromate.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class DeviceUtils {

    public static String getCurrentDeviceId(Context context) {
        String result = "000000000000000";
        try {
            @SuppressLint("HardwareIds")
            String str = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            result = str != null ? str.substring(str.length() - 15) : "";
        } catch (Throwable ignored) {}
        return result;
    }

    public static String getCpuHardware() {
        return Build.HARDWARE;
    }

    public static String getScreenResolution(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            windowManager.getDefaultDisplay().getRealMetrics(metrics);
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;
            return width + " x " + height;
        }
        return "Unavailable";
    }

    public static String getDeviceFactory() {
        return Build.MANUFACTURER;
    }

    public static boolean isDeviceRoot() {
        String[] paths = {
                "/sbin/su",
                "/system/bin/su",
                "/system/xbin/su",
                "/data/local/xbin/su",
                "/data/local/bin/su",
                "/system/sd/xbin/su",
                "/system/bin/failsafe/su",
                "/data/local/su"
        };

        for (String path : paths) {
            if (new java.io.File(path).exists()) {
                return true;
            }
        }

        return false;
    }


    public static int getDeviceSdk() {
        return Build.VERSION.SDK_INT;
    }

}
