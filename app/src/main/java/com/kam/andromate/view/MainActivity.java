package com.kam.andromate.view;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.kam.andromate.AndromateManager.AndroMateTaskManager;
import com.kam.andromate.messagingController.AndromateWebSocket.WebSocketClient;
import com.kam.andromate.messagingController.AndromateWebSocket.WebSocketInterface;
import com.kam.andromate.messagingController.AndromateWebSocket.WebSocketObserver;
import com.kam.andromate.model.factory.AndroMateFactory;
import com.kam.andromate.IConstants;
import com.kam.andromate.R;
import com.kam.andromate.utils.AppUtils;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Response;
import okhttp3.WebSocket;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "AndroMateApp";

    /*
    / receiver to restart AndroMate app
    */
    BroadcastReceiver appRestartReceiver = null;

    /**
     * receiver to move app to foreground
     */
    BroadcastReceiver moveAppToFrontReceiver = null;


    private MainReportSection mainReportSection = null;
    ImageButton execButton = null;
    ToggleButton ctsButton = null;
    ToggleButton controlLineRts = null;
    ToggleButton cnxButton = null;
    ToggleButton startButton = null;
    ToggleButton stopButton = null;
    ToggleButton testButton = null;


    WebSocketClient webSocketClient = null;

    AndroMateTaskManager androMateTaskManager = null;


    private void initView() {
        mainReportSection = new MainReportSection(findViewById(R.id.androidMateReportSectionId));
        execButton = findViewById(R.id.send_btn);
        ctsButton = findViewById(R.id.controlLineCts);
        controlLineRts = findViewById(R.id.controlLineRts);
        cnxButton = findViewById(R.id.cnxButtonId);
        startButton = findViewById(R.id.startButtonId);
        stopButton = findViewById(R.id.stopButtonId);
        testButton = findViewById(R.id.testButtonId);
        if (!IConstants.SHOW_EXECUTE_BAR) {
            findViewById(R.id.CommandLinearLayoutId).setVisibility(View.GONE);
        }
        mainReportSection.initAndroMateReportInfo();
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    @Override
    public Intent registerReceiver(@Nullable BroadcastReceiver receiver, IntentFilter filter, int flags) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return super.registerReceiver(receiver, filter, flags);
        } else {
            return super.registerReceiver(receiver, filter);
        }
    }

    @SuppressLint("InlinedApi")
    private void initBroadcastReceiver() {
        if (appRestartReceiver == null) {
            appRestartReceiver = new BroadcastReceiver() {
                @SuppressLint("UnsafeIntentLaunch")
                @Override
                public void onReceive(Context context, Intent intent) {
                    try {
                        Intent restartIntent = getBaseContext().getPackageManager()
                                .getLaunchIntentForPackage(getBaseContext().getPackageName());
                        if (restartIntent != null) {
                            restartIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(restartIntent);
                            finish();
                            Runtime.getRuntime().exit(0);
                        }
                    } catch (Throwable ignored) {}
                }
            };
            registerReceiver(appRestartReceiver, new IntentFilter(IConstants.APP_RESTART_RECEIVER), Context.RECEIVER_EXPORTED);
        }
        if (moveAppToFrontReceiver == null) {
            moveAppToFrontReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    try {
                        //TODO: find other method to modify deprecated getRunngingTasks methods
                        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                        am.moveTaskToFront(getTaskId(), 0);
                    } catch (Throwable ignored) {}
                }
            };
            registerReceiver(moveAppToFrontReceiver, new IntentFilter(IConstants.MOVE_APP_TO_FRONT_RECEIVER), Context.RECEIVER_EXPORTED);
        }
    }

    private void unregisterReceiver() {
        if (appRestartReceiver != null) {
            try {
                unregisterReceiver(appRestartReceiver);
                appRestartReceiver = null;
            } catch (Throwable t) {
                Log.e(TAG, " cannot unregister app restart receiver due to "+t);
            }
        }
        if (moveAppToFrontReceiver != null) {
            try {
                unregisterReceiver(moveAppToFrontReceiver);
                moveAppToFrontReceiver = null;
            } catch (Throwable t) {
                Log.e(TAG, " cannot unregister move to front receiver due to "+t);
            }
        }
    }

    private void initClickEvent() {
        if (execButton != null) {
            execButton.setOnClickListener(view -> {

            });
        }
        if (ctsButton != null) {
            ctsButton.setOnClickListener(view -> {
                mainReportSection.clear();
            });
        }
        if (controlLineRts != null) {
            controlLineRts.setOnClickListener(view -> AppUtils.rebootApp(getApplicationContext()));
        }
        if (cnxButton != null) {
            cnxButton.setOnClickListener(view -> {
                if (webSocketClient == null) {
                    mainReportSection.appendFmvKey("open connection ", IConstants.WEB_SOCKET_DEFAULT_IP);
                    webSocketClient = new WebSocketClient(IConstants.WEB_SOCKET_DEFAULT_IP, new WebSocketObserver(new WebSocketInterface() {
                        @Override
                        public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
                            mainReportSection.info("connection success to "+IConstants.WEB_SOCKET_DEFAULT_IP+ " response "+response);
                        }

                        @Override
                        public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                            mainReportSection.errorMsg("connection closed"+IConstants.WEB_SOCKET_DEFAULT_IP);
                        }

                        @Override
                        public void onClosing(@NonNull WebSocket webSocket, int code, @NonNull String reason) {

                        }

                        @Override
                        public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, @Nullable Response response) {
                            mainReportSection.errorMsg("connection failed "+t+" response "+response);
                        }

                        @Override
                        public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
                            mainReportSection.appendFmvKey("message received ", text);
                            try {
                                JSONObject jo = new JSONObject(text);
                                AndroMateFactory.createPipeLineFromJson(jo);
                            } catch (Throwable e) {
                                Log.e(TAG,"exception jo"+e);
                            }
                        }
                    }));
                    webSocketClient.startWebSocketObserver();
                }
            });
        }
        if (startButton != null) {
            startButton.setOnClickListener(view -> {
                launchTaskManager();
            });
        }
        if (stopButton != null) {
            stopButton.setOnClickListener(view -> {
                if (androMateTaskManager != null) {
                    androMateTaskManager.stop("manuel stop");
                    androMateTaskManager = null;
                }
            });
        }
        if (testButton != null) {
            testButton.setOnClickListener(view -> {
                Intent intent = new Intent(MainActivity.this, AndroMateProgressActivity.class);
                startActivity(intent);
            });
        }
    }

    private void launchTaskManager() {
        if (androMateTaskManager == null) {
            androMateTaskManager = new AndroMateTaskManager(getApplicationContext(), mainReportSection);
            androMateTaskManager.start("start task");
        }
    }

    private void launchTaskManagerTimer() {
        if (androMateTaskManager == null) {
            Timer timer = new Timer("taskManagerTimer");
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    launchTaskManager();
                }
            }, 5 * IConstants.SECONDS_VALUE);
        }
    }

    private void initAndroMateApp() {
        initView();
        initClickEvent();
        initBroadcastReceiver();
        if (IConstants.IS_ROBOT_MODE) {
            launchTaskManagerTimer();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initAndroMateApp();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver();
        if (webSocketClient != null) {
            webSocketClient.close();
        }
        super.onDestroy();
    }

}