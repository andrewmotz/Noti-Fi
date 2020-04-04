package com.example.noti_fiwifinotificitions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class AddNotificationActivity extends AppCompatActivity {

    Spinner networkSpinner;
    EditText contentText;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notification);
        contentText = findViewById(R.id.ContentEditText);

        networkSpinner = findViewById(R.id.spinner_networks);
        textView = findViewById(R.id.show_custom_ssid);

        //Fill spinner
        FavSSIDS favSSIDS = new FavSSIDS(getSharedPreferences(MainActivity.NOTI_FI_PREF, MODE_PRIVATE));
        ArrayList<String> favList = favSSIDS.getFavSSIDList();
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, favList);
        networkSpinner.setAdapter(spinnerAdapter);
    }

    public void saveButton(View view){
        SavedNetworks savedNetworks = new SavedNetworks(getSharedPreferences(MainActivity.NOTI_FI_PREF, MODE_PRIVATE));
        String content = contentText.getText().toString();

        String ssid;

        //if ssid not manually entered, ssid is from spinner
        if (textView.getText().toString().equals("Empty")) { //make sure this matches the strings.xml
            ssid = networkSpinner.getSelectedItem().toString();
        }
        else { //if ssid was manually entered, extract ssid from below the spinner
            ssid = textView.getText().toString();
        }

        savedNetworks.addNotiFi(ssid, content);

        goToMainActivity();
    }

    public  void cancelButton(View view){
        goToMainActivity();
    }

    private void goToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() { //to receive the string if manually entered
        super.onResume();

        Intent intent = getIntent();
        String ssid = intent.getStringExtra(CustomNetwork.SAVED_CUSTOM_SSID);
        textView.setText(ssid);

    }
}
