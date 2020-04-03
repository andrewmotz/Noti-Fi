package com.example.noti_fiwifinotificitions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    Spinner favSpinner;
    ListView favListView;
    EditText manualEntryEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        favSpinner = findViewById(R.id.favNetworkSpinner);
        favListView = findViewById(R.id.favListView);
        manualEntryEditText = findViewById(R.id.manualSSIDEditText);

        //Fill spinner
        FavSSIDS favSSIDS = new FavSSIDS(getSharedPreferences(MainActivity.NOTI_FI_PREF, MODE_PRIVATE));
        ArrayList<String> favList = favSSIDS.getFavSSIDList();
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, favList);
        favSpinner.setAdapter(spinnerAdapter);

        //Fill Listview
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, favList);
        favListView.setAdapter(listViewAdapter);
    }

    public void backButton(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void addCurrentNetwork(View view) {
        FavSSIDS favSSIDS = new FavSSIDS(getSharedPreferences(MainActivity.NOTI_FI_PREF, MODE_PRIVATE));
        favSSIDS.addFavSSID(tryToReadSSID());
    }

    public void manualEntry(View view){
        FavSSIDS favSSIDS = new FavSSIDS(getSharedPreferences(MainActivity.NOTI_FI_PREF, MODE_PRIVATE));
        favSSIDS.addFavSSID("\"" + manualEntryEditText.getText().toString() + "\"");
        manualEntryEditText.setText("");
    }

    //Get SSID
    private String tryToReadSSID() {
        String ssid = "";

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo.getSupplicantState() == SupplicantState.COMPLETED) {
            ssid = wifiInfo.getSSID();//Here you can access your SSID

        }
        return ssid;
    }

    public void DeleteSelected(View view){
        String selected = favSpinner.getSelectedItem().toString();
        FavSSIDS favSSIDS = new FavSSIDS(getSharedPreferences(MainActivity.NOTI_FI_PREF, MODE_PRIVATE));
        Log.i("NOTIFI", "Removing " + selected);
        favSSIDS.removeFavSSID(selected);
    }
}
