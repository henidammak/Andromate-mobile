package com.kam.andromate.controlService.ControlServiceModels;


import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import com.kam.andromate.controlService.BaseControlService;
import com.kam.andromate.controlService.ControlServiceModels.controlServiceException.ControlServiceErrorType;
import com.kam.andromate.controlService.ControlServiceModels.controlServiceException.ControlServiceException;
import com.kam.andromate.controlService.ControlServiceModels.controlServiceTypes.clickTextModels.CompareType;
import com.kam.andromate.controlService.ControlServiceModels.controlServiceTypes.clickTextModels.TextSelector;

import java.util.ArrayList;
import java.util.List;

//class to put all static utils method used in ScreenAutomator
public class ControlServiceUtils {

    private static final String TAG = "ControlServiceUtils";

    private interface NodeIterator {
        void onNode(AccessibilityNodeInfo node);
    }

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

    private static boolean compareNodeWithText(CompareType compareType, TextSelector textSelector, AccessibilityNodeInfo nodeInfo, String text) throws ControlServiceException {
        String nodeText = null;
        boolean compareResult = false;
        switch (textSelector) {
            case TEXT_SELECTOR:
                if (nodeInfo.getText() != null) {
                    nodeText = nodeInfo.getText().toString();
                }
                break;
            case CONTENT_DESCRIPTION:
                if (nodeInfo.getContentDescription() != null) {
                    nodeText = nodeInfo.getContentDescription().toString();
                }
                break;
            case TOOL_TIP_TEXT:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    if (nodeInfo.getTooltipText() != null) {
                        nodeText = nodeInfo.getTooltipText().toString();
                    }
                } else {
                    throw new ControlServiceException(ControlServiceErrorType.UNSUPPORTED_TEXT_SELECTOR);
                }
                break;
            default:
                throw new ControlServiceException(ControlServiceErrorType.UNSUPPORTED_TEXT_SELECTOR);
        }
        if (nodeText != null) {
            switch (compareType) {
                case EXACT_TEXT:
                    compareResult = nodeText.equalsIgnoreCase(text);
                    break;
                case START_WITH:
                    compareResult = nodeText.toLowerCase().startsWith(text.toLowerCase());
                    break;
                case CONTAIN:
                    compareResult = nodeText.toLowerCase().contains(text.toLowerCase());
                    break;
            }
        }
        return compareResult;
    }

    private static void iterateNode(AccessibilityNodeInfo nodeInfo, NodeIterator nodeIterator) {
        if (nodeInfo != null) {
            nodeIterator.onNode(nodeInfo);
            for (int nodeIndex=0; nodeIndex < nodeInfo.getChildCount(); nodeIndex++) {
                iterateNode(nodeInfo.getChild(nodeIndex), nodeIterator);
            }
        }
    }

    public static List<AccessibilityNodeInfo> getListNodeInfoFromText(CompareType compareType, TextSelector textSelector, AccessibilityNodeInfo nodeInfo, String text) {
        List<AccessibilityNodeInfo> nodeInfos = new ArrayList<>();
        iterateNode(nodeInfo, node -> {
            try {
                if (compareNodeWithText(compareType, textSelector, node, text)) {
                    if (node.isClickable()) {
                        nodeInfos.add(node);
                    } else if (node.getParent() != null && node.getParent().isClickable()) {
                        nodeInfos.add(node.getParent());
                    }
                }
            } catch (ControlServiceException ignored) {;
            }
        });
        return nodeInfos;
    }

    public static AccessibilityNodeInfo getNodeInfoFromText(CompareType compareType, TextSelector textSelector, AccessibilityNodeInfo nodeInfo, String text, long index) {
        AccessibilityNodeInfo node = null;
        List<AccessibilityNodeInfo> nodeInfos = getListNodeInfoFromText(compareType, textSelector, nodeInfo, text);
        if (!nodeInfos.isEmpty()) {
            if (nodeInfos.size() >= index+1) {
                node = nodeInfos.get((int) index);
            } else {
                node = nodeInfos.get(0);
            }
        }
        return node;
    }

}
