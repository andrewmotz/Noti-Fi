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
