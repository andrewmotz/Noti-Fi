package com.example.noti_fiwifinotificitions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class DeleteNotification extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner delSpinner;
    private TextView ssidText;
    private TextView descText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_notification);

        ssidText = findViewById(R.id.savedSSIDTextView);
        descText = findViewById(R.id.savedDescTextView);
        delSpinner = findViewById(R.id.deleteSpinner);
        delSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getNotiFiSSIDs());
        spinnerAdapter.setDropDownViewResource(R.layout.my_spinner);
        delSpinner.setAdapter(spinnerAdapter);
    }

    //Returns a list of all the saved ssids that have a notification tied to them
    private ArrayList<String> getNotiFiSSIDs(){
        SavedNetworks savedNetworks = new SavedNetworks(getSharedPreferences(MainActivity.NOTI_FI_PREF, MODE_PRIVATE));
        ArrayList<NotiFiObject> notiFiObjects = savedNetworks.getNotiFiObjects();
        ArrayList<String> notifissids = new ArrayList<>();

        for (NotiFiObject notiFiObject: notiFiObjects) {
            notifissids.add(notiFiObject.getSSID());
        }
        return notifissids;
    }

    public void deleteNotifi(View view){
        Object selectedObject = delSpinner.getSelectedItem();
        if(selectedObject != null) {
            String ssid = selectedObject.toString();
            SavedNetworks savedNetworks = new SavedNetworks(getSharedPreferences(MainActivity.NOTI_FI_PREF, MODE_PRIVATE));
            savedNetworks.removeNotiFi(ssid);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String ssid = delSpinner.getItemAtPosition(position).toString();
        ssidText.setText(ssid);
        SavedNetworks savedNetworks = new SavedNetworks(getSharedPreferences(MainActivity.NOTI_FI_PREF, MODE_PRIVATE));
        descText.setText(savedNetworks.getNotiFiDesc(ssid));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //Does nothing, have to override
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.default_menu, menu);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Delete A Notification");
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
            case R.id.service_start :
                startNotiFiService();
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

    private void startNotiFiService(){
        Intent intentStarter = new Intent(this, NotiFiService.class);
        intentStarter.putExtra("inputExtra", "NotiFi is waiting for network changes");
        ContextCompat.startForegroundService(this, intentStarter);
    }
    //onBackPressed is the bottom back arrow. Control for the top back button is done in mainActivity
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
