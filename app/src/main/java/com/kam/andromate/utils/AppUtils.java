package com.kam.andromate.utils;

import android.content.Context;
import android.content.Intent;

import com.kam.andromate.IConstants;
import com.kam.andromate.view.AndroMateProgressActivity;


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

    public static void moveToFront(Context context) {
        Intent intent = new Intent(IConstants.MOVE_APP_TO_FRONT_RECEIVER);
        context.sendBroadcast(intent);
    }

    public static void openAndromateApp(Context context) {
        Intent launchIntent = new Intent(context, AndroMateProgressActivity.class);
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(launchIntent);
    }

}
