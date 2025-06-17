package com.kam.andromate.andromateService;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.kam.andromate.R;
import com.pedro.rtmp.utils.ConnectCheckerRtmp;
import com.pedro.rtplibrary.rtmp.RtmpDisplay;

import java.nio.ByteBuffer;

public class ScreenCaptureService extends Service {

    private static final int SCREEN_CAPTURE_REQUEST_CODE = 1000;


    MediaProjection mediaProjection;
    VirtualDisplay virtualDisplay;
    ImageReader imageReader;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            Log.i("my_tag","start command");
            startForeground(1, createNotification());
            int resultCode = intent.getIntExtra("code", -1);
            Intent data = intent.getParcelableExtra("data");
            MediaProjectionManager mpm = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
            mediaProjection = mpm.getMediaProjection(resultCode, data);
            startRtmpStream(data, resultCode);
            //captureScreen();
        } catch (Throwable t) {
            Log.e("my_tag","cannot start command due to "+t);
        }
        return START_NOT_STICKY;
    }

    private Notification createNotification() {
        String CHANNEL_ID = "screen_stream_channel";
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "Screen Sharing",
                NotificationManager.IMPORTANCE_LOW
        );
        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Streaming Screen")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build();
    }

    private void captureScreen() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        int density = metrics.densityDpi;

        imageReader = ImageReader.newInstance(width, height, PixelFormat.RGBA_8888, 2);
        mediaProjection.registerCallback(new MediaProjection.Callback() {
            @Override
            public void onStop() {
                super.onStop();
                Log.d("MediaProjection", "Projection stopped by system or user");

                // Nettoyer les ressources
                if (virtualDisplay != null) virtualDisplay.release();
                if (imageReader != null) imageReader.close();
                mediaProjection = null;
            }
        }, new Handler(Looper.getMainLooper())); // ou ton propre Handler
        virtualDisplay = mediaProjection.createVirtualDisplay(
                "ScreenCapture",
                width, height, density,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                imageReader.getSurface(),
                null, null
        );

        imageReader.setOnImageAvailableListener(reader -> {
            Image image = null;
            try {
                image = reader.acquireLatestImage();
                if (image != null) {
                    Bitmap bitmap = imageToBitmap(image);
                    image.close();

                    // Do what you want with the bitmap (e.g., save or send)
                    Log.d("my_tag", "Bitmap captured: " + bitmap.getWidth() + "x" + bitmap.getHeight());
                }
            } catch (Exception e) {
                Log.e("my_tag","exception capture "+e);
            } finally {
                if (image != null) image.close();
            }
        }, null);
    }

    private void startRtmpStream(Intent data, int resultCode) {
        RtmpDisplay rtmpDisplay = new RtmpDisplay(this, true, new ConnectCheckerRtmp() {
            @Override
            public void onConnectionStartedRtmp(@NonNull String s) {
                Log.i("my_tag", "connection started "+s);
            }

            @Override
            public void onConnectionSuccessRtmp() {
                Log.i("my_tag", "connection success");
            }

            @Override
            public void onConnectionFailedRtmp(@NonNull String s) {
                Log.i("my_tag","connection failed "+s);
            }

            @Override
            public void onNewBitrateRtmp(long l) {
                Log.i("my_tag","new bitrate rtmp "+l);
            }

            @Override
            public void onDisconnectRtmp() {
                Log.i("my_tag","disconnect rtmp");
            }

            @Override
            public void onAuthErrorRtmp() {
                Log.i("my_tag","auth error");
            }

            @Override
            public void onAuthSuccessRtmp() {
                Log.i("my_tag","success  rtmp");
            }
        });
        rtmpDisplay.setIntentResult(resultCode, data);
        rtmpDisplay.startStream("rtmp://live.restream.io/live/re_9838597_bdf8c7bebf2517a371b5");
    }

    private Bitmap imageToBitmap(Image image) {
        Image.Plane[] planes = image.getPlanes();
        ByteBuffer buffer = planes[0].getBuffer();
        int width = image.getWidth();
        int height = image.getHeight();
        int pixelStride = planes[0].getPixelStride();
        int rowStride = planes[0].getRowStride();
        int rowPadding = rowStride - pixelStride * width;
        Bitmap bitmap = Bitmap.createBitmap(width + rowPadding / pixelStride, height, Bitmap.Config.ARGB_8888);
        bitmap.copyPixelsFromBuffer(buffer);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height); // Crop padding
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
