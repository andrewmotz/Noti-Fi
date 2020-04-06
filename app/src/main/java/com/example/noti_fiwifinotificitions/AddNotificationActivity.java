package com.example.noti_fiwifinotificitions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Objects;

public class AddNotificationActivity extends AppCompatActivity {

    private  final String select_A_Network = "Select a network:";
    private Spinner networkSpinner;
    private EditText contentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notification);
        contentText = findViewById(R.id.ContentEditText);

        networkSpinner = findViewById(R.id.spinner_networks);

        //Fill spinner
        FavSSIDS favSSIDS = new FavSSIDS(getSharedPreferences(MainActivity.NOTI_FI_PREF, MODE_PRIVATE));
        ArrayList<String> favList = favSSIDS.getFavSSIDList();
        favList.add(0, select_A_Network);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, favList);
        spinnerAdapter.setDropDownViewResource(R.layout.my_spinner);
        networkSpinner.setAdapter(spinnerAdapter);
    }

    public void saveButton(View view){
        String ssid = networkSpinner.getSelectedItem().toString();
        if(!ssid.equals(select_A_Network)) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.default_menu, menu);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Create A Notification");
        return true;
    }

    //For overflow menu
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
