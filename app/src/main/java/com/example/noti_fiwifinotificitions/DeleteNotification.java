package com.example.noti_fiwifinotificitions;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class DeleteNotification extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner delSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_notification);

        delSpinner = findViewById(R.id.deleteSpinner);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getNotiFiSSIDs());
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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.i("NOTIFI","SELECTED:" + position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
