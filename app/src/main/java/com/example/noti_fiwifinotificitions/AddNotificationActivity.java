package com.example.noti_fiwifinotificitions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class AddNotificationActivity extends AppCompatActivity {

    Spinner networkSpinner;
    EditText contentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notification);
        contentText = findViewById(R.id.ContentEditText);

        networkSpinner = findViewById(R.id.spinner_networks);

        //Fill spinner
        FavSSIDS favSSIDS = new FavSSIDS(getSharedPreferences(MainActivity.NOTI_FI_PREF, MODE_PRIVATE));
        ArrayList<String> favList = favSSIDS.getFavSSIDList();
        favList.add(0,"Select a network");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, favList);
        networkSpinner.setAdapter(spinnerAdapter);
    }

    public void saveButton(View view){
        String ssid = networkSpinner.getSelectedItem().toString();
        if(!ssid.equals("Select a network")) {
            SavedNetworks savedNetworks = new SavedNetworks(getSharedPreferences(MainActivity.NOTI_FI_PREF, MODE_PRIVATE));
            String content = contentText.getText().toString();
            savedNetworks.addNotiFi(ssid, content);

            goToMainActivity();
        }
    }

    public  void cancelButton(View view){
        goToMainActivity();
    }

    private void goToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
