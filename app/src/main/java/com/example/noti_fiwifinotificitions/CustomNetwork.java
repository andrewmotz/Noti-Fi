package com.example.noti_fiwifinotificitions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CustomNetwork extends AppCompatActivity {

    public static final String SAVED_CUSTOM_SSID = "saved_custom_ssid";
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_network);

        editText = findViewById(R.id.manual_network_edit);
    }

    public void saveNetwork(View view) {


        Intent intent = new Intent(this, AddNotificationActivity.class);
        intent.putExtra(SAVED_CUSTOM_SSID, editText.getText().toString());
        startActivity(intent);

    }

    public void cancel(View view) { goToAddNotificationActivity(); }

    public void goToAddNotificationActivity() {
        Intent intent = new Intent (this, AddNotificationActivity.class);
        startActivity(intent);
    }
}
