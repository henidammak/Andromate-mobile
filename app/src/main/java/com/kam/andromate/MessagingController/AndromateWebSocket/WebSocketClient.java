package com.kam.andromate.MessagingController.AndromateWebSocket;

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

    public void close() {
        if (webSocket != null) {
            webSocket.close(1000, "Closing normally");
            webSocket = null;
        }
    }

}
