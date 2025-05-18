package com.kam.andromate.controlService.ControlServiceModels;


import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.kam.andromate.controlService.BaseControlService;

//class to put all static utils method used in ScreenAutomator
public class ControlServiceUtils {

    private static final String TAG = "ControlServiceUtils";

    public static boolean checkScreenAutomatorPermission(Context context) {
        boolean hasScreenAutomatorPermission = false;
        try {
            int serviceEnabled = 0;
            final String service = context.getPackageName() + "/" + BaseControlService.class.getCanonicalName();
            try {
                serviceEnabled = Settings.Secure.getInt (context.getContentResolver(), Settings.Secure.ACCESSIBILITY_ENABLED);
            } catch (Settings.SettingNotFoundException ignored) {}

            TextUtils.SimpleStringSplitter colonSplitter = new TextUtils.SimpleStringSplitter(':');

            if (serviceEnabled == 1) {
                String settingValue = Settings.Secure.getString (context.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
                if (settingValue != null) {
                    colonSplitter.setString(settingValue);
                    while (colonSplitter.hasNext()) {
                        String controlService = colonSplitter.next();

                        if (controlService.equalsIgnoreCase(service)) {
                            hasScreenAutomatorPermission = true;
                            break;
                        }
                    }
                }
            }
        } catch (Throwable t) {
            Log.e(TAG, "cannot check screen automator permission due to "+t);
        }
        return hasScreenAutomatorPermission;
    }

}
