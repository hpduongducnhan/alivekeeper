package com.example.alivekeeper;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class KeepAliveService extends Service {
    private static final String CHANNEL_ID = "KeepAliveServiceChannel";
    private static final String TAG = "KeepAliveService";
    private Handler handler;
    private Runnable runnable;


    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        Notification notification = createNotification();
        startForeground(1, notification); // Start service in foreground

        // Initialize Handler and Runnable for periodic logging
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                logDateTime();
                handler.postDelayed(this, 5000); // Repeat every 5 seconds
            }
        };
        handler.post(runnable); // Start logging
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY; // Ensures service restarts if killed
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (handler != null && runnable != null){
            handler.removeCallbacks(runnable);
        }
        Log.d(TAG, "Service stopped and logging has been stopped.");

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null; // No binding needed
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Keep Alive Service",
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    private Notification createNotification() {
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Keep Alive Service")
                .setContentText("Preventing device from sleeping")
                .setSmallIcon(R.drawable.ic_launcher_foreground) // Change to your icon
                .setOngoing(true) // Prevent user from swiping it away
                .build();
    }

    // Logs the current date and time
    private void logDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentDateTime = sdf.format(new Date());
        Log.d(TAG, "Service is running at: " + currentDateTime);
    }
}
