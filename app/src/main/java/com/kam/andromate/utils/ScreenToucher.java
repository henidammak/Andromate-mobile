package com.kam.andromate.utils;

import android.os.Build;
import android.util.Log;

import com.kam.andromate.utils.ThreadUtils.ThreadHelper;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;

/*
* Only fo rooted device to click in text
* */

public class ScreenToucher {

    private static final String TAG = "ScreenToucher";
    private static final String SCREEN_READ_COMMAND = "su -c uiautomator dump /sdcard/file.txt";
    private static final String SAMSUNG_SWITCH_ID = "com.samsung.android.app.telephonyui:id/on_off_switch";
    private static final String ROOT_INPUT_TAP = "su -c input tap %s %s";
    private static final  String ROOT_INPUT_EVENT_DOWN = "su -c input motionevent DOWN %s %s";
    private static final  String ROOT_INPUT_EVENT_UP = "su -c input motionevent UP %s %s";
    private static final String READ_SCREEN_XML = "cat /sdcard/file.txt";
    private static final String HIERARCHY_TAG = "hierarchy";
    private static final String XML_END = "</hierarchy>";
    private static final String BOUNDS_TAG = "bounds";
    private static final String CHECKED_TAG = "checked";
    private static boolean forceTapSdk33 = false;
    private static final int MIN_CLICK_MARGIN = 500;
    public static int rootMinClickDelay = 250;
    public static int rootClickMarginX = 100;
    public static int rootClickMarginY = 30;

    private static String readScreenAttributes() {
        String result = "";
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(SCREEN_READ_COMMAND);
            process.waitFor();
            process = Runtime.getRuntime().exec(READ_SCREEN_XML);
            String input, errorLine;
            try (BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                while ((input = stdInput.readLine()) != null) {
                    result = input;
                }
            } catch (Throwable t_s) {
                Log.e(TAG, "readScreenEvents input stream threw: " + t_s);
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                while ((errorLine = br.readLine()) != null) {
                    Log.e(TAG, "errorLine: " + errorLine);
                }
            } catch (Throwable t_e) {
                Log.e(TAG, "readScreenEvents error stream threw: " + t_e);
            }
            if (!result.isEmpty())
                result = result.split(XML_END)[0] + XML_END;
        } catch (Throwable t) {
            Log.e(TAG, "readScreenEvents threw: " + t);
        } finally {
            if (process != null) {
                try {process.waitFor();} catch (Throwable ignored) {}
                try {
                    process.destroy();
                } catch (Throwable ignored) {}
            }
        }
        return result;
    }

    private static Document stringToXml(String xmlText) {
        Document doc = null;
        try {
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(xmlText)));
            doc.getDocumentElement().normalize();
        } catch (Throwable t) {
            Log.e(TAG, "stringToXml threw: " + t);
        }
        Log.i(TAG, "xmlText: " + xmlText);
        return doc;
    }

    private static int occurrenceIndex;

    private static boolean foundButton = false;

    private static Element elementSought = null;

    private static Element getNodeChild(Node node, String text1, String text2, String text3, String exactText1, String exactText2, int repetition, String type) {
        Element result = null;
        Element element = (Element) node;
        NodeList childNodes = element.getChildNodes();
        Node item;
        NamedNodeMap attributes;
        for (int i = 0; i < childNodes.getLength(); i++) {
            item = childNodes.item(i);
            if (item.getNodeType() == Node.ELEMENT_NODE) {
                if (type != null) {
                    if (foundButton) {
                        result = getNodeChild(item, "", "", "", type, "", repetition, type);
                        if (elementSought != null)
                            break;
                        else if (result != null) {
                            Log.i(TAG, "elementSought: " + elementSought);
                            elementSought = result;
                            break;
                        }
                    } else {
                        if (getNodeChild(item, text1, text2, text3, exactText1, exactText2, repetition, type) != null)
                            foundButton = true;
                    }
                } else {
                    result = getNodeChild(item, text1, text2, text3, exactText1, exactText2, repetition, null);
                    if (result != null)
                        break;
                }
            }
        }
        if (result == null) {
            attributes = element.getAttributes();
            for (int i = 0; i < attributes.getLength(); i++) {
                item = attributes.item(i);
                final String itemValue = item.getNodeValue().toLowerCase();
                if ((itemValue.startsWith(text1) && !text1.isEmpty()) || ((!text2.isEmpty() && itemValue.startsWith(text2)))
                        || ((!text3.isEmpty() && itemValue.startsWith(text3)))
                        || ((!exactText1.isEmpty() && item.getNodeValue().equalsIgnoreCase(exactText1)))
                        || ((!exactText2.isEmpty() && item.getNodeValue().equalsIgnoreCase(exactText2)))
                ) {
                    Log.i(TAG, "Found, itemValue: " + itemValue + " - occurrenceIndex: " + occurrenceIndex + " - repetition: " + repetition);
                    occurrenceIndex += 1;
                    if (repetition <= 1 || occurrenceIndex >= repetition)
                        result = element;
                    break;
                }
            }
        }
        return result;
    }

    private static boolean clickElement(Element child) {
        boolean pushed = false;
        Log.i(TAG, "clickElement: child: " + child + (child != null ? (" - has BOUNDS_TAG: " + child.hasAttribute(BOUNDS_TAG)) : ""));
        if (child != null && child.hasAttribute(BOUNDS_TAG)) {
            String[] coordinates = child.getAttribute(BOUNDS_TAG).split("]")[1].replace("[", "").split(",");
            pushButton(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));
            pushed = true;
        }
        elementSought = null;
        return pushed;
    }

    private static void activateSwitch(Element child) {
        Log.i(TAG, "activateSwitch: child: " + child + (child != null ? (" - has BOUNDS_TAG: " + child.hasAttribute(BOUNDS_TAG) + " - has CHECKED_TAG: " + child.hasAttribute(CHECKED_TAG)) : ""));
        try {
            if (child != null && child.hasAttribute(CHECKED_TAG) && child.hasAttribute(BOUNDS_TAG)) {
                if (!Boolean.parseBoolean(child.getAttribute(CHECKED_TAG))) {
                    String[] coordinates = child.getAttribute(BOUNDS_TAG).split("]")[1].replace("[", "").split(",");
                    pushButton(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));
                }
            }
        } catch (Throwable t) {
            Log.e(TAG, "Cannot activate switch due to: " + t);
        }
    }

    /*
     * Click a button on the screen that contains text1 or text2, according to system language French / English
     */
    public static void clickButton(String text1, String text2, String text3, String exactText1, String exactText2, int repetition, boolean forceClick) {
        Document doc = stringToXml(readScreenAttributes());
        boolean pushed = false;
        if (doc != null) {
            try {
                if (repetition > 1)
                    occurrenceIndex = 0;
                elementSought = null;
                NodeList nodeList = doc.getElementsByTagName(HIERARCHY_TAG);
                for (int temp = 0; temp < nodeList.getLength(); temp++) {
                    Node node = nodeList.item(temp);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element child = getNodeChild(node, text1.toLowerCase(), text2.toLowerCase(), text3.toLowerCase(), exactText1, exactText2, repetition, null);
                        if (clickElement(child))
                            break;
                    }
                }
            } catch (Throwable t) {
                Log.e(TAG, "clickButton threw: " + t);
            }
        }
        if (!pushed && forceClick)
            pushButton(500, 2000);
    }

    public static void clickButton(String text1, String text2, String text3, boolean forceClick) {
        clickButton(text1, text2, text3, "", "", 1, forceClick);
    }

    public static void clickButton(String text) {
        clickButton(text, text, text, text, "", 1, false);
    }

    public static void clickSwitchAfterButton(String tag) {
        Document doc = stringToXml(readScreenAttributes());
        if (doc != null) {
            try {
                foundButton = false;
                elementSought = null;
                NodeList nodeList = doc.getElementsByTagName(HIERARCHY_TAG);
                for (int temp = 0; temp < nodeList.getLength(); temp++) {
                    Node node = nodeList.item(temp);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        getNodeChild(node, tag.toLowerCase(), tag.toLowerCase(), tag.toLowerCase(), tag, "", 1, SAMSUNG_SWITCH_ID);
                        if (elementSought != null) {
                            activateSwitch(elementSought);
                            break;
                        }
                    }
                }
            } catch (Throwable t) {
                Log.e(TAG, "clickSwitchAfterButton threw: " + t);
            }
        }
    }

    public static void setTapForcedSdk33() {
        forceTapSdk33 = true;
    }

    private static long lastClickTs = 0;

    /*
     ** Execute only on rooted devices
     */
    private static void pushButton(int x, int y) {
        final long currentTs = System.currentTimeMillis();
        if (currentTs - lastClickTs > MIN_CLICK_MARGIN) {
            lastClickTs = currentTs;
            Process process = null;
            try {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S && !forceTapSdk33) {
                    process = Runtime.getRuntime().exec(String.format(ROOT_INPUT_EVENT_DOWN, x - rootClickMarginX, y - rootClickMarginY));
                    ThreadHelper.deepSleep(rootMinClickDelay);
                    process = Runtime.getRuntime().exec(String.format(ROOT_INPUT_EVENT_UP, x - rootClickMarginX, y - rootClickMarginY));
                } else {
                    process = Runtime.getRuntime().exec(String.format(ROOT_INPUT_TAP, x - rootClickMarginX, y - rootClickMarginY));
                }
                process.waitFor();
            } catch (Throwable t) {
                Log.e(TAG, "Error when try to reboot device: " + t);
            } finally {
                forceTapSdk33 = false;
                if (process != null) {
                    try {
                        process.waitFor();
                    } catch (Throwable ignored) {
                    }
                    try {
                        process.destroy();
                    } catch (Throwable ignored) {
                    }
                }
            }
        }
    }

    public static String getAllTextInNode(String text) {
        Document doc = stringToXml(readScreenAttributes());
        NodeList nodes = doc.getElementsByTagName("node");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element node = (Element) nodes.item(i);
            String NodeText = node.getAttribute("text");
            if (NodeText.contains(text)) {
                return NodeText;
            }
        }
        return "";
    }
}