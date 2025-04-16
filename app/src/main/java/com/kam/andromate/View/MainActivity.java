package com.kam.andromate.View;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.kam.andromate.IConstants;
import com.kam.andromate.MessagingController.AndromateWebSocket.WebSocketClient;
import com.kam.andromate.MessagingController.AndromateWebSocket.WebSocketInterface;
import com.kam.andromate.MessagingController.AndromateWebSocket.WebSocketObserver;
import com.kam.andromate.R;

import okhttp3.Response;
import okhttp3.WebSocket;

public class MainActivity extends AppCompatActivity {

    private MainReportSection mainReportSection = null;

    public void initView() {
        mainReportSection = MainReportSection.initInstance(findViewById(R.id.androidMateReportSectionId));
        mainReportSection.appendMsg("hellooo");
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
        ImageButton execButton = findViewById(R.id.send_btn);
        execButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebSocketClient webSocketClient = new WebSocketClient(IConstants.WEB_SOCKET_DEFAULT_IP, new WebSocketObserver(new WebSocketInterface() {
                    @Override
                    public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
                        mainReportSection.appendMsg("open response "+response);
                    }

                    @Override
                    public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                        mainReportSection.appendMsg("close reason "+reason + " code "+code);
                    }

                    @Override
                    public void onClosing(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                        mainReportSection.appendMsg("on Closing code "+code + " reason "+code);
                    }

                    @Override
                    public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, @Nullable Response response) {
                        mainReportSection.appendMsg("on Failure ex "+t + " response "+response);
                    }

                    @Override
                    public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
                        mainReportSection.appendMsg("msg "+text);
                    }
                }));
                webSocketClient.startWebSocketObserver();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}