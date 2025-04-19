package com.kam.andromate.utils;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class DeviceUtils {

    public static String getCurrentDeviceId(Context context) {
        String result = "000000000000000";
        try {
            String str = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            result = str != null ? str.substring(str.length() - 15) : "";
        } catch (Throwable t) {}
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

}
