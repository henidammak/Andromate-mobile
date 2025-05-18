package com.kam.andromate.view;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.kam.andromate.IConstants;
import com.kam.andromate.R;
import com.kam.andromate.controlService.ControlServiceModels.ControlServiceUtils;
import com.kam.andromate.singletons.AndroMateApp;
import com.kam.andromate.singletons.AndroMateDevice;
import com.kam.andromate.utils.ThreadUtils.AndroMateSynchronizer;
import com.kam.andromate.utils.ThreadUtils.AndroMateThread;
import com.kam.andromate.utils.ThreadUtils.ThreadHelper;

import java.util.Timer;
import java.util.TimerTask;

public class AndroMateProgressActivity extends AppCompatActivity {

    ProgressBar androMateLoadBar = null;
    TextView stepTextView = null;
    private Timer screenAutomatorPermissionMonitor = null;
    private final AndroMateSynchronizer<Boolean, Boolean> specialPermissionSynchronizer = new AndroMateSynchronizer<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.andromate_progress);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.progressMain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        androMateLoadBar = findViewById(R.id.progressBar);
        androMateLoadBar.setMax(InitStepsEnum.values().length * IConstants.PROGRESS_UNIT);
        stepTextView = findViewById(R.id.initStepsText);
        startInitAppSteps();
    }

    private static void animateProgressBar(ProgressBar progressBar, int from, int to) {
        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", from, to);
        animation.setDuration(500); // 1 second
        animation.start();
    }

    private void startInitAppSteps() {
        AndroMateDevice.setInstance(getApplicationContext());
        AndroMateApp.setInstance(getApplicationContext());
        AndroMateThread.runInBackground("initStepsThread", new Runnable() {
            @Override
            public void run() {
                for (InitStepsEnum initStepsEnum : InitStepsEnum.values()) {
                    runOnUiThread(() -> {
                        stepTextView.setText(initStepsEnum.stepName);
                        animateProgressBar(androMateLoadBar, androMateLoadBar.getProgress(),
                                androMateLoadBar.getProgress() + IConstants.PROGRESS_UNIT);
                    });
                    performInitAppSteps(initStepsEnum);
                }
                runOnUiThread(() -> {
                    animateProgressBar(androMateLoadBar, androMateLoadBar.getProgress(),
                            androMateLoadBar.getMax());
                    Intent mainActivityIntent = new Intent(AndroMateProgressActivity.this, MainActivity.class);
                    startActivity(mainActivityIntent);
                });
            }
        });
    }

    private void performInitAppSteps(InitStepsEnum initStepsEnum) {
        switch (initStepsEnum) {
            case INIT:
                //TODO: nothing to do for now can be usefull in the future
                ThreadHelper.deepSleep(4 * IConstants.SECONDS_VALUE);
                break;
            case CHECK_SCREEN_AUTOMATOR:
                checkSpecialPermission();
                break;
            case CHECK_DEVICE_PERMISSION:
                ThreadHelper.deepSleep(4 * IConstants.SECONDS_VALUE);
                break;
            case CHECK_MESSAGING_CONNECTION:
                ThreadHelper.deepSleep(4 * IConstants.SECONDS_VALUE);
                break;
            case CHECK_APP_PERMISSIONS:
                checkAppPermission();
                break;
            case FINISH:
                ThreadHelper.deepSleep(4 * IConstants.SECONDS_VALUE);
                break;
        }
    }

    private void checkAppPermission() {
        runOnUiThread(() -> ActivityCompat.requestPermissions(AndroMateProgressActivity.this, IConstants.PERMISSIONS, 100));
    }

    private AlertDialog getSpecialPermissionDialog(String title, String buttonName, String msgInfo, Runnable openPermssionUiRunnable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setPositiveButton(buttonName, (dialogInterface, i) -> {
                    runOnUiThread(openPermssionUiRunnable);
                })
                .setMessage(msgInfo)
                .setCancelable(false); // Prevent dismissal by back button or outside touch
        return builder.create();
    }

    private void openScreenAutomatorSettings() {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void openDrawOverlyPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + AndroMateApp.getInstance().getPackageName()));
        startActivity(intent);
    }

    private boolean hasSpecialPermssion(SpecialPermission specialPermission) {
        boolean hasPermission = false;
        switch (specialPermission) {
            case SCREEN_AUTOMATOR_PERMISSION:
                hasPermission = ControlServiceUtils.checkScreenAutomatorPermission(getApplicationContext());
                break;
            case FOREGROUND_SERVICE:
                hasPermission = Settings.canDrawOverlays(getApplicationContext());
                break;
        }
        return hasPermission;
    }

    private AlertDialog getDialogBySpecialPerimssion(SpecialPermission specialPermission) {
        switch (specialPermission) {
            case SCREEN_AUTOMATOR_PERMISSION:
                return getSpecialPermissionDialog("Screen Automator Permission",
                        "Open Screen automator settings", "Accord screen automator permission", this::openScreenAutomatorSettings);
            case FOREGROUND_SERVICE:
                return getSpecialPermissionDialog("First Plan Permission",
                        "Open overlay settings", "Accord first plane permission", this::openDrawOverlyPermission);

        }
        return null;
    }

    private void startMonitoringSpecialPermission(SpecialPermission specialPermission) {
        if (screenAutomatorPermissionMonitor == null) {
            final boolean[] hasPermission = {false};
            screenAutomatorPermissionMonitor = new Timer();
            final AlertDialog[] permissionDialog = {null};
            screenAutomatorPermissionMonitor.schedule(new TimerTask() {
                @Override
                public void run() {
                    hasPermission[0] = hasSpecialPermssion(specialPermission);
                    if (hasPermission[0]) {
                        runOnUiThread(() -> {
                            if (permissionDialog[0] == null) {
                                permissionDialog[0] = getDialogBySpecialPerimssion(specialPermission);
                            }
                            if (permissionDialog[0] != null && permissionDialog[0].isShowing()) {
                                permissionDialog[0].dismiss();
                                specialPermissionSynchronizer.notifySuccess(true);
                                if (screenAutomatorPermissionMonitor != null) {
                                    screenAutomatorPermissionMonitor.cancel();
                                    screenAutomatorPermissionMonitor = null;
                                }
                            }
                        });
                    } else {
                        runOnUiThread(() -> {
                            if (permissionDialog[0] == null) {
                                permissionDialog[0] = getDialogBySpecialPerimssion(specialPermission);
                            }
                            if (permissionDialog[0] != null && !permissionDialog[0].isShowing()) {
                                permissionDialog[0].show();
                            }// check every second
                        });
                    }
                }
            }, 0, IConstants.SECONDS_VALUE);
        }
    }

    private void checkSpecialPermission() {
        for (SpecialPermission specialPermission : SpecialPermission.values()) {
            if (!hasSpecialPermssion(specialPermission)) {
                specialPermissionSynchronizer.reset();
                startMonitoringSpecialPermission(specialPermission);
                specialPermissionSynchronizer.wait_notification();
            }
        }
    }

    enum SpecialPermission {
        SCREEN_AUTOMATOR_PERMISSION,
        FOREGROUND_SERVICE
    }


}