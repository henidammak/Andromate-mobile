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

    int SECONDS_VALUE = 1000;
}
