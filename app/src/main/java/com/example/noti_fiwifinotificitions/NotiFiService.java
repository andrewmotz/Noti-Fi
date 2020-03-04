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
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class NotiFiService extends Service {

    //Variable decelerations
    public static final String CHANNEL_ID = "ONE";
    WifiChangeReceiver wifiChangeReceiver = new WifiChangeReceiver();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //OnCreate, register the receiver
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("NOTIFI", "NotiFiService onCreate() ran.");

        //Wifi stuff
        registerReceiver(wifiChangeReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    //Runs when service is killed
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true); //true will remove notification
        unregisterReceiver(wifiChangeReceiver);
        Log.d("NOTIFI","NotiFiService Stopped");
    }

    //Runs on service start
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String input = intent.getStringExtra("inputExtra");
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText(input)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);

        Log.d("NOTIFI", "NotiFiService onStartCommand() ran.");
        return START_NOT_STICKY;
    }

    //For creating a notification the service is running
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

    private class WifiChangeReceiver extends BroadcastReceiver {
        String SSID = "";
        @Override
        public void onReceive(Context context, Intent intent) {

                NetworkInfo netInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                if (ConnectivityManager.TYPE_WIFI == netInfo.getType()) {
                    WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                    WifiInfo info = wifiManager.getConnectionInfo();
                    SSID = info.getSSID();
                }

                Toast.makeText(getApplicationContext(), "WIFI CHANGED to: " + SSID, Toast.LENGTH_LONG).show();
            }
        }
    }

