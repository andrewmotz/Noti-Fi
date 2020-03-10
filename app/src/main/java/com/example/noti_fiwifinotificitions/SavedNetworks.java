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

    public boolean isSaved(String checkSSID){

        String[] SSIDArray = sharedPreferences.getString("SSIDS", "").split("⌇");

        for(int i = 0; i < SSIDArray.length;i++){
            if(checkSSID.equals(SSIDArray[i])){
                return true;
            }
        }
        return  false;
    }

    public String getDesc(String checkSSID){

        String[] SSIDArray = sharedPreferences.getString("SSIDS", "").split("⌇");
        String[] notificationDescriptionsArray = sharedPreferences.getString("DESCRIPTIONS", "").split("⌇");

        for(int i = 0; i < SSIDArray.length;i++){
            if(checkSSID.equals(SSIDArray[i])){
                return notificationDescriptionsArray[i];
            }
        }
        return "Network not found: Something went wrong.";
    }

    public void removeNetwork(String ssid){

    }

    public void addNetwork(String ssid, String desc){

    }

    public String[] getSSIDArray(){
        return sharedPreferences.getString("SSIDS", "").split("⌇");
    }

    public String[] getDescArray(){
        return sharedPreferences.getString("DESCRIPTIONS", "").split("⌇");
    }
 }
