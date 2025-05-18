package com.kam.andromate.singletons;

import android.content.Context;

import com.kam.andromate.utils.AppUtils;

public class AndroMateApp {

    private static AndroMateApp instance = null;

    private final String versionName;
    private final String packageName;

    private AndroMateApp(String versionName, String packageName) {
        this.versionName = versionName;
        this.packageName = packageName;
    }

    public static void setInstance(Context context) {
        if (instance == null) {
            instance = new AndroMateApp(
                    AppUtils.getAndroMateVersionName(context),
                    AppUtils.getPackageName(context)
            );
        }
    }

    public static AndroMateApp getInstance() {
        return instance;
    }

    public String getVersionName() {
        return versionName;
    }
    public String getPackageName() {
        return packageName;
    }

}
