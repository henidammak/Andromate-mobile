package com.kam.andromate;

public interface IConstants {

    boolean DEBUG = true;
    String DEBUG_TAG = "AndroMateTag";

    boolean SHOW_EXECUTE_BAR = false;

    String WEB_SOCKET_DOMAIN = "92.243.6.161";

    int WEB_SOCKET_PORT = 8765;
    String WEB_SOCKET_DEFAULT_IP = "ws://"+WEB_SOCKET_DOMAIN+":"+WEB_SOCKET_PORT; // websocket wifi server

    String EMPTY_STRING = "";
    String DEVICE_ID_TAG = "device_id";

    String APP_RESTART_RECEIVER = "app_restart_receiver";
    String MOVE_APP_TO_FRONT_RECEIVER = "move_to_front_receiver";

    int SECONDS_VALUE = 1000;

    int PROGRESS_UNIT = 10;

    String[] PERMISSIONS = {
            "android.permission.POST_NOTIFICATIONS",
            "android.permission.READ_PHONE_STATE",
            "android.permission.ACCESS_COARSE_LOCATION",
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.SEND_SMS",
            "android.permission.READ_SMS",
            "android.permission.RECEIVE_SMS"

    };

}
