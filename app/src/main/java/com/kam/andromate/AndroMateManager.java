package com.kam.andromate;

import android.util.Log;

import com.kam.andromate.singletons.AndroMateDevice;
import com.kam.andromate.utils.HttpUtils.AndroMateRestClient;


public class AndroMateManager {

    private static final String TAG = "androMateManager";

    public static boolean checkAuthorization(String url) throws Exception {
        AndroMateRestClient restClient = new AndroMateRestClient(url);
        String result = restClient.get(AndroMateDevice.getInstance().getDeviceId());
        Log.i(TAG, "authorization result = "+result);
        return true;
    }

}
