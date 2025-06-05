package com.kam.andromate.messagingController.AndromateWebSocket;


import com.kam.andromate.IConstants;
import com.kam.andromate.singletons.AndroMateDevice;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

public class WebSocketClient {

    /*
    * observer to notify client of web socket life cycle steps
    * */
    private WebSocketObserver webSocketObserver = null;

    /*
    * url of web socket messaging channel
    */
    private String webSocketUrl = null;

    /*
    * web socket instance
     */
    private WebSocket webSocket = null;

    public WebSocketClient(String webSocketUrl, WebSocketObserver webSocketObserver) {
        this.webSocketUrl = webSocketUrl;
        this.webSocketObserver = webSocketObserver;
    }

    public void startWebSocketObserver() {
        if (webSocket == null) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(webSocketUrl).build();
            webSocket = client.newWebSocket(request, webSocketObserver);
            client.dispatcher().executorService().shutdown();
        }
    }

    public void sendDeviceIdToBackend() {
        if (webSocket != null && AndroMateDevice.getInstance() != null) {
            try {
                JSONObject deviceIdPayload = new JSONObject();
                deviceIdPayload.put(IConstants.DEVICE_ID_TAG, AndroMateDevice.getInstance().getDeviceId());
                webSocket.send(deviceIdPayload.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void close() {
        if (webSocket != null) {
            webSocket.close(1000, "Closing normally");
            webSocket = null;
        }
    }

}
