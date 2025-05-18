package com.kam.andromate.controlService;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.view.accessibility.AccessibilityEvent;

import androidx.annotation.Nullable;

public class BaseControlService extends AccessibilityService {


    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    @Override
    public Intent registerReceiver(@Nullable BroadcastReceiver receiver, IntentFilter filter, int flags) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return super.registerReceiver(receiver, filter, flags);
        } else {
            return super.registerReceiver(receiver, filter);
        }
    }

    /*
    * Receiver used to get actions from Main activity
    * */
    BroadcastReceiver baseControlReceiver = null;

    @SuppressLint("InlinedApi")
    @Override
    public void onCreate() {
        try {
            if (baseControlReceiver == null) {
                baseControlReceiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        if (intent != null)
                            onReceiveAction(intent);
                    }
                };
                registerReceiver(baseControlReceiver, new IntentFilter(ControlServiceConstants.RECEIVER_ACTION_NAME), Context.RECEIVER_EXPORTED);
            }
        } catch (Throwable ignored) {}
        super.onCreate();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {

    }

    @Override
    public void onInterrupt() {
        unregisterReceiver();
    }

    @Override
    public void onDestroy() {
        unregisterReceiver();
        super.onDestroy();
    }

    private void onReceiveAction(Intent intent) {
        
    }

    private synchronized void unregisterReceiver() {
        try {
            if (baseControlReceiver != null) {
                unregisterReceiver(baseControlReceiver);
                baseControlReceiver = null;
            }
        } catch (Throwable ignored) {}
    }


}
