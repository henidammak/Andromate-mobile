package com.kam.andromate.utils;

import android.content.Context;
import android.content.pm.PackageManager;

public class AppUtils {

    public static String getAndroMateVersionName(Context context) {
        String versionName = "1.0.0";
        try {
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Throwable ignored) {}
        return versionName;
    }

}
