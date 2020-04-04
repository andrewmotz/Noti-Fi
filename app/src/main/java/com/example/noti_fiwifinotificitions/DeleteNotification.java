package com.example.noti_fiwifinotificitions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class DeleteNotification extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner delSpinner;
    TextView ssidText, descText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_notification);

        ssidText = findViewById(R.id.savedSSIDTextView);
        descText = findViewById(R.id.savedDescTextView);
        delSpinner = findViewById(R.id.deleteSpinner);
        delSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getNotiFiSSIDs());
        spinnerAdapter.setDropDownViewResource(R.layout.my_spinner);
        delSpinner.setAdapter(spinnerAdapter);
    }

    //Returns a list of all the saved ssids that have a notification tied to them
    private ArrayList<String> getNotiFiSSIDs(){
        SavedNetworks savedNetworks = new SavedNetworks(getSharedPreferences(MainActivity.NOTI_FI_PREF, MODE_PRIVATE));
        ArrayList<NotiFiObject> notiFiObjects = savedNetworks.getNotiFiObjects();
        ArrayList<String> notifissids = new ArrayList<String>();

        for (NotiFiObject notiFiObject: notiFiObjects) {
            notifissids.add(notiFiObject.getSSID());
        }
        return notifissids;
    }

    public void deleteNotifi(View view){
        String ssid = delSpinner.getSelectedItem().toString();
        SavedNetworks savedNetworks = new SavedNetworks(getSharedPreferences(MainActivity.NOTI_FI_PREF, MODE_PRIVATE));
        savedNetworks.removeNotiFi(ssid);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
