package com.kam.andromate.MessagingController.AndromateWebSocket;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import okhttp3.Response;
import okhttp3.WebSocket;

public interface WebSocketInterface {

    /*
    * when connection is opened;
    * */
    void onOpen(@NonNull WebSocket webSocket, @NonNull Response response);

    /*
     * when connection is closed;
     * */
    void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason);

    /*
     * before connection is closed;
     * */
    void onClosing(@NonNull WebSocket webSocket, int code, @NonNull String reason);

    /*
     * when is failed;
     * */
    void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, @Nullable Response response);

    /*
     * before message received from web socket chanel;
     * */
    void onMessage(@NonNull WebSocket webSocket, @NonNull String text);

}
