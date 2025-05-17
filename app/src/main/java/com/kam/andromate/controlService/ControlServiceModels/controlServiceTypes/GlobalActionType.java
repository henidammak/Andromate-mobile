package com.kam.andromate.controlService.ControlServiceModels.controlServiceTypes;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;

import com.kam.andromate.controlService.ControlServiceModels.controlServiceException.ControlServiceErrorType;
import com.kam.andromate.controlService.ControlServiceModels.controlServiceException.ControlServiceException;
import com.kam.andromate.utils.DeviceUtils;


@SuppressLint("InlinedApi")
public enum GlobalActionType {

    SHOW_LAUNCHERS_ALL_APPS(            "Action to show Launcher's all apps.",                          AccessibilityService.GLOBAL_ACTION_ACCESSIBILITY_ALL_APPS,          31) ,
    SHOW_SCREEN_AUTOMATOR_BUTTON(       "Action to trigger the Accessibility Button",                   AccessibilityService.GLOBAL_ACTION_ACCESSIBILITY_BUTTON,            31),
    SCREEN_AUTOMATOR_BUTTON_CHOOSER(    "Action to bring up the Accessibility Button's chooser menu",   AccessibilityService.GLOBAL_ACTION_ACCESSIBILITY_BUTTON_CHOOSER,    31),
    SCREEN_AUTOMATOR_SHORT_CUT(         "Action to trigger the Accessibility Shortcut",                 AccessibilityService.GLOBAL_ACTION_ACCESSIBILITY_SHORTCUT,          31),
    ACTION_BACK(                        "Action to go back",                                            AccessibilityService.GLOBAL_ACTION_BACK,                            16),
    DISMISS_NOTIFICATION_SHADE(         "Action to dismiss the notification shade",                     AccessibilityService.GLOBAL_ACTION_DISMISS_NOTIFICATION_SHADE,      31),
    GLOBAL_ACTION_DPAD_CENTER(          "Action to trigger dpad center keyevent",                       AccessibilityService.GLOBAL_ACTION_DPAD_CENTER,                     33),
    GLOBAL_ACTION_DPAD_DOWN(            "Action to trigger dpad down keyevent",                         AccessibilityService.GLOBAL_ACTION_DPAD_LEFT,                       33),
    GLOBAL_ACTION_DPAD_RIGHT(           "Action to trigger dpad right keyevent",                        AccessibilityService.GLOBAL_ACTION_DPAD_RIGHT,                      33),
    GLOBAL_ACTION_DPAD_UP(              "Action to trigger dpad right keyevent",                        AccessibilityService.GLOBAL_ACTION_DPAD_UP,                         33),
    GLOBAL_ACTION_HOME(                 "Action to go home",                                            AccessibilityService.GLOBAL_ACTION_HOME,                            16),
    GLOBAL_ACTION_KEYCODE_HEADSETHOOK(  "Action to send the KEYCODE_HEADSETHOOK KeyEvent",              AccessibilityService.GLOBAL_ACTION_KEYCODE_HEADSETHOOK,             31),
    GLOBAL_ACTION_LOCK_SCREEN(          "Action to lock the screen",                                    AccessibilityService.GLOBAL_ACTION_LOCK_SCREEN,                     28),
    GLOBAL_ACTION_MEDIA_PLAY_PAUSE(     "Action to trigger media play/pause key event",       22,                                                                 36),//TODO: add it when migrate compile sdk to 36
    GLOBAL_ACTION_MENU(                 "Action to trigger menu key event",                   21,                                                                 36),//TODO: add it when migrate compile sdk to 36
    GLOBAL_ACTION_NOTIFICATIONS(        "Action to open the notifications.",                            AccessibilityService.GLOBAL_ACTION_NOTIFICATIONS,                   16),
    GLOBAL_ACTION_POWER_DIALOG(         "Action to open the power long-press dialog.",                  AccessibilityService.GLOBAL_ACTION_POWER_DIALOG,                    21),
    GLOBAL_ACTION_QUICK_SETTINGS(       "Action to open the quick settings",                            AccessibilityService.GLOBAL_ACTION_QUICK_SETTINGS,                  17),
    GLOBAL_ACTION_RECENTS(              "Action to toggle showing the overview of recent apps",         AccessibilityService.GLOBAL_ACTION_RECENTS,                         16),
    GLOBAL_ACTION_TAKE_SCREENSHOT(      "Action to take a screenshot",                                  AccessibilityService.GLOBAL_ACTION_TAKE_SCREENSHOT,                 28),
    GLOBAL_ACTION_TOGGLE_SPLIT_SCREEN(  "Action to toggle docking the current app's window.",           AccessibilityService.GLOBAL_ACTION_TOGGLE_SPLIT_SCREEN,             24);


    final String descr;
    final int actionType;
    final int minSdk;

    private GlobalActionType(String descr, int actionType, int minSdk) {
        this.descr = descr;
        this.actionType = actionType;
        this.minSdk = minSdk;
    }

    public static GlobalActionType getGlobalActionTypeFromInteger(int actionTypeInteger) throws ControlServiceException {
        GlobalActionType globalActionTypeResult = null;
        for (GlobalActionType globalActionType : GlobalActionType.values()) {
            if (globalActionType.actionType == actionTypeInteger) {
                globalActionTypeResult = globalActionType;
                break;
            }
        }
        if (globalActionTypeResult == null) {
            throw new ControlServiceException(ControlServiceErrorType.INVALID_CONTROL_SERVICE_GLOBAL_ACTION);
        } else {
            if (DeviceUtils.getDeviceSdk() < globalActionTypeResult.minSdk) {
                throw new ControlServiceException(ControlServiceErrorType.UNSUPPORTED_CONTROL_SERVICE_GLOBAL_ACTION);
            }
        }
        return globalActionTypeResult;
    }

}
