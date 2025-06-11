package com.kam.andromate.AndromateManager;



import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kam.andromate.IConstants;
import com.kam.andromate.messagingController.AndromateWebSocket.WebSocketClient;
import com.kam.andromate.messagingController.AndromateWebSocket.WebSocketInterface;
import com.kam.andromate.messagingController.AndromateWebSocket.WebSocketObserver;
import com.kam.andromate.model.CompositeTask;
import com.kam.andromate.model.factory.AndroMateFactory;
import com.kam.andromate.utils.AppUtils;
import com.kam.andromate.utils.ThreadUtils.AndroMateSynchronizer;
import com.kam.andromate.utils.ThreadUtils.ThreadHelper;
import com.kam.andromate.view.MainReportSection;

import org.json.JSONObject;

import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.Response;
import okhttp3.WebSocket;

public class AndroMateTaskManager {

    private static final String TAG = "AndroMateTaskManager";

    WebSocketClient webSocketClient = null;

    private enum TaskSyncErrorType {
        FAILURE,
        CONNECTION_CLOSED,
        STOP
    }

    private final AndroMateSynchronizer<Boolean, String> connectionSynchronizer ;
    private final AndroMateSynchronizer<CompositeTask, TaskSyncErrorType> compositeTaskSynchronizer;



    private static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
            1, 20, 3,TimeUnit.SECONDS, new SynchronousQueue<>(),
            new ThreadFactory() {
                private final AtomicInteger mCount = new AtomicInteger(1);
                public Thread newThread(Runnable r) {
                    return new Thread(r, "AsyncTask #" + mCount.getAndIncrement());
                }
            });

    private final MainReportSection rs;
    private final Context context;

    public AndroMateTaskManager(Context context, MainReportSection reportSection) {
        this.context = context;
        this.rs = reportSection;
        this.compositeTaskSynchronizer = new AndroMateSynchronizer<>();
        this.connectionSynchronizer = new AndroMateSynchronizer<>();
    }

    public Context getContext() {
        return context;
    }

    public void start(String name) {
        AndroMataTaskExecutor androMateTaskExecutor = new AndroMataTaskExecutor(name);
        androMateTaskExecutor.executeOnExecutor(THREAD_POOL_EXECUTOR, "");
    }

    public void stop(String reason) {
        stopConnectToWebSocketClient();
        this.connectionSynchronizer.reset();
        this.compositeTaskSynchronizer.reset();
    }


    private void startConnectToWebSocketClient(WebSocketInterface webSocketInterface) {
        if (webSocketClient == null) {
            webSocketClient = new WebSocketClient(IConstants.WEB_SOCKET_DEFAULT_IP, new WebSocketObserver(webSocketInterface));
            webSocketClient.startWebSocketObserver();
        }
    }

    private void stopConnectToWebSocketClient() {
        if (webSocketClient != null) {
            webSocketClient.close();
            webSocketClient = null;
        }
    }

    private class AndroMataTaskExecutor extends AndroMateAsyncTask<String, String> {


        protected AndroMataTaskExecutor(String name) {
            super(name);
        }

        @Override
        protected String _doInBackground(String... p) {
            rs.appendTitle("Start listener to Messaging service");
            rs.incMargin();
            rs.appendFmvKey("Connection to", IConstants.WEB_SOCKET_DEFAULT_IP);
            startConnectToWebSocketClient(new WebSocketInterface() {
                @Override
                public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
                    connectionSynchronizer.notifySuccess(true);
                }

                @Override
                public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                    rs.errorMsg("connection closed"+IConstants.WEB_SOCKET_DEFAULT_IP);
                    compositeTaskSynchronizer.notifyError(TaskSyncErrorType.CONNECTION_CLOSED);
                }

                @Override
                public void onClosing(@NonNull WebSocket webSocket, int code, @NonNull String reason) {

                }

                @Override
                public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, @Nullable Response response) {
                    connectionSynchronizer.notifyError("response = "+response + " error "+t);
                    compositeTaskSynchronizer.notifyError(TaskSyncErrorType.FAILURE);
                }

                @Override
                public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
                    try {
                        JSONObject jo = new JSONObject(text);
                        CompositeTask compositeTask = AndroMateFactory.createPipeLineFromJson(jo);
                        compositeTaskSynchronizer.notifySuccess(compositeTask);
                    } catch (Throwable e) {
                        Log.e(TAG,"exception jo"+e);
                    }
                }
            });
            connectionSynchronizer.wait_notification(15 * IConstants.SECONDS_VALUE);
            if (connectionSynchronizer.done()) {
                if (connectionSynchronizer.hasError()) {
                    rs.errorMsg("cannot connect due to "+connectionSynchronizer.error);
                } else {
                    rs.info("connection success");
                    if (webSocketClient != null) {
                        webSocketClient.sendDeviceIdToBackend();
                    }
                    rs.appendMsg("start wait for tasks order");
                    waitForTasksAndPerform();
                }
            }
            return "";
        }
    }

    public void waitForTasksAndPerform() {
        for (int i=0; i<Integer.MAX_VALUE; i++) {
            compositeTaskSynchronizer.reset();
            compositeTaskSynchronizer.wait_notification();
            if (compositeTaskSynchronizer.hasError()) {
                stop(compositeTaskSynchronizer.error.name());
                break;
            } else {
                if (compositeTaskSynchronizer.result != null) {
                    rs.discMargin();
                    rs.appendFmvKey("Tasks received: ",compositeTaskSynchronizer.result.info());
                    ThreadHelper.deepSleep(5 * IConstants.SECONDS_VALUE);
                    rs.incMargin();
                    //TODO: replace executeTask by execute
                    compositeTaskSynchronizer.result.execute(rs, context);
                    rs.info("end task execution");
                    rs.discMargin();
                    AppUtils.moveToFront(context);
                }
            }
        }
    }

}
