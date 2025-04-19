package com.kam.andromate.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.kam.andromate.IConstants;
import com.kam.andromate.R;

public class MainActivity extends AppCompatActivity {

    private MainReportSection mainReportSection = null;
    ImageButton execButton = null;

    private void initView() {
        mainReportSection = new MainReportSection(findViewById(R.id.androidMateReportSectionId));
        execButton = findViewById(R.id.send_btn);
        if (!IConstants.SHOW_EXECUTE_BAR) {
            findViewById(R.id.CommandLinearLayoutId).setVisibility(View.GONE);
        }
        for (int i=0; i<100; i++) {
            mainReportSection.appendFmvKey("tag", "hello from androMate");
        }
    }

    private void initClickEvent() {
        execButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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
        initView();
        initClickEvent();
        mainReportSection.appendMsg("hello from mainReport section");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}