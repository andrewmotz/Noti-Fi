package com.example.noti_fiwifinotificitions;
/* Description
 */

import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;


public class SavedNetworks extends AppCompatActivity {

    public final String KEY_LIST_PREF = "KEY_LIST_PREF";

    private SharedPreferences sharedPreferences;

    public SavedNetworks(){
        sharedPreferences = getSharedPreferences(KEY_LIST_PREF, MODE_PRIVATE);
    }

    public boolean isSaved(String ssid){

        String[] ssids = sharedPreferences.getString("SSIDS", "").split("⌇");
        return  false;
    }

    public String getDesc(String SSD){
        return "";
    }

    public void removeNetwork(String ssid){

    }

    public void addNetwork(String ssid, String desc){

    }

    public String[] getSSIDArray(){
        return new String[0];
    }

    public String[] getDescArray(){
        return new String[0];
    }
 }
