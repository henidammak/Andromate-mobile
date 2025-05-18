package com.kam.andromate.utils;

import android.content.Context;
import android.content.Intent;

import com.kam.andromate.IConstants;


public class AppUtils {

    public static String getAndroMateVersionName(Context context) {
        String versionName = "1.0.0";
        try {
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Throwable ignored) {}
        return versionName;
    }

    public static String getPackageName(Context context) {
        return context.getPackageName();
    }

    public static void rebootApp(Context context) {
        Intent intent = new Intent(IConstants.APP_RESTART_RECEIVER);
        context.sendBroadcast(intent);
    }

}
