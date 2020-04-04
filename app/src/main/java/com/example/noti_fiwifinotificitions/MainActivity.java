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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private static final int LOCATION = 1;
    public static final String NOTI_FI_PREF = "NOTI_FI_PREF";
    ListView savedNotiFisList;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("NOTIFI", "Main activity made.");

        Intent intentStarter = new Intent(this, NotiFiService.class);
        intentStarter.putExtra("inputExtra", "NotiFi is waiting for network changes");
        ContextCompat.startForegroundService(this, intentStarter);

        textView = findViewById(R.id.textView);
        SavedNetworks savedNetworks = new SavedNetworks(getSharedPreferences(NOTI_FI_PREF, MODE_PRIVATE));

        //Fill list
        savedNotiFisList = findViewById(R.id.savedNotiFisList);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, savedNetworks.getCombinedList());
        savedNotiFisList.setAdapter(arrayAdapter);
    }

    //Get wifi on button press...testing
    public void click(View view){
        Toast.makeText(getApplicationContext(), "WIFI Currently is: " + tryToReadSSID(), Toast.LENGTH_LONG).show();
        SavedNetworks savedNetworks = new SavedNetworks(getSharedPreferences(NOTI_FI_PREF, MODE_PRIVATE));
        savedNetworks.addNotiFi(tryToReadSSID(),"TEST DESC for" + tryToReadSSID());
        textView.setText(savedNetworks.getCombinedList().toString()); //why are we changing title??
        updateList();
    }

    public void remove(View view){
        SavedNetworks savedNetworks = new SavedNetworks(getSharedPreferences(NOTI_FI_PREF, MODE_PRIVATE));
        textView.setText("removed" + tryToReadSSID());
        savedNetworks.removeNotiFi(tryToReadSSID());
        updateList();
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

    public void createNotifi(View view) {
        Intent intent = new Intent(this, AddNotificationActivity.class);
        startActivity(intent);
    }

    public void manageFavourites(View view){
        Intent intent = new Intent(this, FavoritesActivity.class);
        startActivity(intent);
    }

    public void deleteNotiFi(View view){
        Intent intent = new Intent(this, DeleteNotification.class);
        startActivity(intent);
    }

    private void updateList() {
        SavedNetworks savedNetworks = new SavedNetworks(getSharedPreferences(NOTI_FI_PREF, MODE_PRIVATE));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, savedNetworks.getCombinedList());
        savedNotiFisList.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.default_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.help:
                showHelp();
                return true;
            case R.id.faq:
                showfaq();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showHelp(){
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }

    private void showfaq(){
        Intent intent = new Intent(this, FAQActivity.class);
        startActivity(intent);
    }
}
