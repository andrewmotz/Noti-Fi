package com.example.noti_fiwifinotificitions;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotiFiService extends Service {

    //Constant Variable decelerations
    public static final String CHANNEL_ID = "ONE";
    public static boolean isRunning = false;

    //Variables
    WifiChangeReceiver wifiChangeReceiver = new WifiChangeReceiver();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //OnCreate, also runs on service start, register the receiver
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("NOTIFI", "NotiFiService onCreate() ran.");

        //Register broadcast receiver for android.net.conn.CONNECTIVITY_CHANGE. This takes the private class
        //below and the type of broadcast your looking for.
        registerReceiver(wifiChangeReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        isRunning = true;
    }

    //Runs when service is killed
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true); //true will remove notification
        unregisterReceiver(wifiChangeReceiver);
        Log.d("NOTIFI", "NotiFiService Stopped");
        isRunning = false;
    }

    //Runs on service start
    //Part of this was taken from stack overflow, so unsure why return START_NOT_STICKY
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        intent.putExtra("inputExtra", "NotiFi is waiting for network changes");
        String input = intent.getStringExtra("inputExtra");
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo_white)
                .setContentTitle("Noti-Fi is running")
                .setContentText(input)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);

        Log.d("NOTIFI", "NotiFiService onStartCommand() ran.");
        return START_NOT_STICKY;
    }

    //For creating a notification the service is running
    //This was copied from stack overflow, idk what it does.
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    //Method that creates a notification with a custom description
    private void createNotification(String description){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo_white)
                .setContentTitle("Noti-Fi Alert")
                .setContentText(description)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(description))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(123,builder.build());
    }

    //Private class that works with broadcast receiver from above.
    private class WifiChangeReceiver extends BroadcastReceiver {
        String currentSSID = "";

        //onReceive runs when a broadcast is received.
        @Override
        public void onReceive(Context context, Intent intent) {

            //Get the SSID
            NetworkInfo netInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (ConnectivityManager.TYPE_WIFI == netInfo.getType()) {
                WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                WifiInfo info = wifiManager.getConnectionInfo();
                currentSSID = info.getSSID();
            }

            //If saved, create a notification
            SavedNetworks savedNetworks = new SavedNetworks(getSharedPreferences(MainActivity.NOTI_FI_PREF, MODE_PRIVATE));
                if (savedNetworks.notifiIsSaved(currentSSID)) {
                    createNotification(savedNetworks.getNotiFiDesc(currentSSID));
                }
        }
    }
}

