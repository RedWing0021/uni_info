package com.example.uni_info;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

public class DataService extends Service {

    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "DataRefreshChannel";
    private static final long REFRESH_INTERVAL = 10000; // 10 seconds

    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable dataRefreshRunnable = new Runnable() {
        @Override
        public void run() {
            // Fetch data from the API here and update the data source

            // Notify UI components of the data changes (e.g., using a broadcast or callback)

            // Schedule the next data refresh
            handler.postDelayed(this, REFRESH_INTERVAL);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        startForeground(NOTIFICATION_ID, createNotification());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Start the data refresh loop
        handler.post(dataRefreshRunnable);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop the data refresh loop
        handler.removeCallbacks(dataRefreshRunnable);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Data Refresh Service",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    private Notification createNotification() {
        Notification.Builder builder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            builder = new Notification.Builder(this, CHANNEL_ID)
                    .setContentTitle("Data Refresh Service")
                    .setContentText("Refreshing data from API");
        }

        return builder.build();
    }
}
