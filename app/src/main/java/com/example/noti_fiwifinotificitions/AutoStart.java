package com.example.noti_fiwifinotificitions;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.core.content.ContextCompat;

public class AutoStart extends BroadcastReceiver {

    //This runs when android.intent.action.BOOT_COMPLETED is received.
    //Declared in android manifest.
    @Override
    public void onReceive(Context context, Intent intent) {

        //Create intent in order to start service
        Intent intentStarter = new Intent(context, NotiFiService.class);
        intentStarter.putExtra("inputExtra", "Foreground Service Example in Android");

        //Starts the service to run into the background.
        ContextCompat.startForegroundService(context, intentStarter);

        //Log for debug purposes
        Log.d("NOTIFI", "AutoStart received");
    }

}
