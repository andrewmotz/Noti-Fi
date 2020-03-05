package com.example.noti_fiwifinotificitions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private static final int LOCATION = 1;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("NOTIFI", "Main activity made.");

        Intent intentStarter = new Intent(this, NotiFiService.class);
        intentStarter.putExtra("inputExtra", "Foreground Service Example in Android");
        ContextCompat.startForegroundService(this, intentStarter);
        sharedPreferences = getSharedPreferences(NotiFiService.KEY_LIST_PREF, MODE_PRIVATE);
    }

    //Get wifi on button press
    public void click(View view){
        Toast.makeText(getApplicationContext(), "WIFI CHANGED to: " + tryToReadSSID(), Toast.LENGTH_LONG).show();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("SSIDS",tryToReadSSID());
        editor.putString("DESCRIPTIONS","TEST Description");
        editor.apply();

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == LOCATION){
            //User allowed the location and you can read it now
            tryToReadSSID();
        }
    }

    //Get SSID
    private String tryToReadSSID() {
        String ssid = "";
        //If requested permission isn't Granted yet
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Request permission from user
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION);
        }else{//Permission already granted
            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if(wifiInfo.getSupplicantState() == SupplicantState.COMPLETED){
                ssid = wifiInfo.getSSID();//Here you can access your SSID
            }
        }
        return ssid;
    }
}
