package com.kam.andromate.messagingController.AndromateWebSocket;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class WebSocketObserver extends WebSocketListener {

    private static final String TAG = "WebSocketObserver";

    private WebSocketInterface webSocketInterface = null;

    public WebSocketObserver(WebSocketInterface webSocketInterface) {
        this.webSocketInterface = webSocketInterface;
    }

    public WebSocketObserver() {

    }

    @Override
    public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
        super.onOpen(webSocket, response);
        Log.i(TAG, "on open connection");
        if (webSocketInterface != null)
            webSocketInterface.onOpen(webSocket, response);
    }

    @Override
    public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
        Log.i(TAG, "on connection closed");
        if (webSocketInterface != null)
            webSocketInterface.onClosed(webSocket, code, reason);
    }

    @Override
    public void onClosing(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
        Log.i(TAG, "on connection closing");
        super.onClosing(webSocket, code, reason);
        if (webSocketInterface != null)
            webSocketInterface.onClosing(webSocket, code, reason);
    }

    @Override
    public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, @Nullable Response response) {
        Log.i(TAG, "connection failure "+t);
        super.onFailure(webSocket, t, response);
        if (webSocketInterface != null)
            webSocketInterface.onFailure(webSocket, t, response);
    }

    @Override
    public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
        Log.i(TAG," connection message "+text);
        super.onMessage(webSocket, text);
        if (webSocketInterface != null)
            webSocketInterface.onMessage(webSocket, text);
    }

    @Override
    public void onMessage(@NonNull WebSocket webSocket, @NonNull ByteString bytes) {
        Log.i(TAG," connection message bytes");
        super.onMessage(webSocket, bytes);
    }
}
