package com.example.noti_fiwifinotificitions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Objects;

public class FavoritesActivity extends AppCompatActivity {

    private static final int LOCATION = 1;
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
        spinnerAdapter.setDropDownViewResource(R.layout.my_spinner);
        favSpinner.setAdapter(spinnerAdapter);

        //Fill Listview
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, favList);
        favListView.setAdapter(listViewAdapter);
    }

    public void addCurrentNetwork(View view) {
        FavSSIDS favSSIDS = new FavSSIDS(getSharedPreferences(MainActivity.NOTI_FI_PREF, MODE_PRIVATE));
        favSSIDS.addFavSSID(tryToReadSSID());
        updateLists();
    }

    public void manualEntry(View view){
        String enteredSSID = manualEntryEditText.getText().toString();
        if(enteredSSID.length() > 0) {
            FavSSIDS favSSIDS = new FavSSIDS(getSharedPreferences(MainActivity.NOTI_FI_PREF, MODE_PRIVATE));
            favSSIDS.addFavSSID("\"" + enteredSSID +"\"");
            manualEntryEditText.setText("");
            updateLists();
            hideKeyboard();
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

    public void DeleteSelected(View view){
        String selected = favSpinner.getSelectedItem().toString();
        FavSSIDS favSSIDS = new FavSSIDS(getSharedPreferences(MainActivity.NOTI_FI_PREF, MODE_PRIVATE));
        Log.i("NOTIFI", "Removing " + selected);
        favSSIDS.removeFavSSID(selected);
        updateLists();
    }

    private void updateLists(){
        //Fill spinner
        FavSSIDS favSSIDS = new FavSSIDS(getSharedPreferences(MainActivity.NOTI_FI_PREF, MODE_PRIVATE));
        ArrayList<String> favList = favSSIDS.getFavSSIDList();
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, favList);
        spinnerAdapter.setDropDownViewResource(R.layout.my_spinner);
        favSpinner.setAdapter(spinnerAdapter);

        //Fill Listview
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, favList);
        favListView.setAdapter(listViewAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.default_menu, menu);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Manage Networks");
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

    //Closes keyboard
    private void hideKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
