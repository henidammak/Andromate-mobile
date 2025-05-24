package com.kam.andromate.controlService;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Path;
import android.os.Build;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.annotation.Nullable;

import com.kam.andromate.controlService.ControlServiceModels.ControlServiceUtils;
import com.kam.andromate.controlService.ControlServiceModels.entity.ClickInTextEntity;
import com.kam.andromate.controlService.ControlServiceModels.entity.ClickIn_X_Y_Entity;
import com.kam.andromate.controlService.ControlServiceModels.entity.ControlServiceEntity;
import com.kam.andromate.controlService.ControlServiceModels.ControlServiceSync;
import com.kam.andromate.controlService.ControlServiceModels.entity.GlobalActionEntity;
import com.kam.andromate.controlService.ControlServiceModels.controlServiceException.ControlServiceException;

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
        try {
            ControlServiceEntity controlServiceEntity = ControlServiceFactory.CreateControlServiceEntityFromIntent(intent);
            boolean done = false;
            if (controlServiceEntity instanceof GlobalActionEntity) {
                GlobalActionEntity globalActionEntity = (GlobalActionEntity) controlServiceEntity;
                done = performGlobalAction(globalActionEntity.getGlobalActionType().actionType);
            } else if (controlServiceEntity instanceof ClickInTextEntity) {
                ClickInTextEntity clickInTextEntity = (ClickInTextEntity) controlServiceEntity;
                AccessibilityNodeInfo nodeInfo = ControlServiceUtils.getNodeInfoFromText(clickInTextEntity.getCompareType(), clickInTextEntity.getTextSelector(), getRootInActiveWindow(), clickInTextEntity.getText(), clickInTextEntity.getTextIndex());
                if (nodeInfo != null) {
                    done = nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
            } else if (controlServiceEntity instanceof ClickIn_X_Y_Entity) {
                ClickIn_X_Y_Entity clickInXYEntity = (ClickIn_X_Y_Entity) controlServiceEntity;
                done = clickIn_XY(clickInXYEntity.getX(), clickInXYEntity.getY());
            }
            if (done) {
                ControlServiceSync.getInstance().notifyDone();
            } else {
                ControlServiceSync.getInstance().notifyNotDone();
            }
        } catch (ControlServiceException e) {
            ControlServiceSync.getInstance().notifyWithError(e);
        } catch (Throwable ignored) {}
    }

    private synchronized void unregisterReceiver() {
        try {
            if (baseControlReceiver != null) {
                unregisterReceiver(baseControlReceiver);
                baseControlReceiver = null;
            }
        } catch (Throwable ignored) {}
    }

    private boolean clickIn_XY(float x, float y) {
        GestureDescription.Builder builder = new GestureDescription.Builder();
        Path p = new Path();
        p.moveTo(x, y);
        builder.addStroke(new GestureDescription.StrokeDescription(p, 100L, 100));
        GestureDescription gesture = builder.build();
        dispatchGesture(gesture,null,null);
        return true;
    }


}
