package com.example.noti_fiwifinotificitions;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.content.ContextCompat;

public class AutoStart extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        Intent intentStarter = new Intent(context, NotiFiService.class);
        intentStarter.putExtra("inputExtra", "Foreground Service Example in Android");

        ContextCompat.startForegroundService(context, intentStarter);


        Log.d("NOTIFI", "AutoStart received");
    }

}
