package com.kam.andromate.singletons;

import android.content.Context;

import com.kam.andromate.utils.AppUtils;

public class AndroMateApp {

    private static AndroMateApp instance = null;

    private final String versionName;

    private AndroMateApp(String versionName) {
        this.versionName = versionName;
    }

    public static void setInstance(Context context) {
        if (instance == null) {
            instance = new AndroMateApp(
                    AppUtils.getAndroMateVersionName(context)
            );
        }
    }

    public static AndroMateApp getInstance() {
        return instance;
    }

    public String getVersionName() {
        return versionName;
    }

}
