package com.kam.andromate.view;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.kam.andromate.IConstants;
import com.kam.andromate.R;
import com.kam.andromate.utils.ThreadUtils.AndroMateThread;
import com.kam.andromate.utils.ThreadUtils.ThreadHelper;

public class AndroMateProgressActivity extends AppCompatActivity {

    ProgressBar androMateLoadBar = null;
    TextView stepTextView = null;

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
            case CHECK_DEVICE_PERMISSION:
                checkAppPermission();
                break;
            case CHECK_APP_PERMISSIONS:
                ThreadHelper.deepSleep(4 * IConstants.SECONDS_VALUE);
                break;
            case CHECK_MESSAGING_CONNECTION:
                ThreadHelper.deepSleep(4 * IConstants.SECONDS_VALUE);
                break;
            case FINISH:
                ThreadHelper.deepSleep(4 * IConstants.SECONDS_VALUE);
                break;
        }
    }

    private void checkAppPermission() {
        runOnUiThread(() -> ActivityCompat.requestPermissions(AndroMateProgressActivity.this, IConstants.PERMISSIONS, 100));
    }

}