package com.example.noti_fiwifinotificitions;
/* Class is used to access Saved networks and descriptions through
* the sharedPreferences.
 */

import android.content.SharedPreferences;
import android.util.Log;
import android.util.Pair;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class SavedNetworks extends AppCompatActivity {

    //Constants
    public static final String SSID_LIST = "SSID_LIST";
    public static final String DESC_LIST = "DESC_LIST";

    //Variables
    private SharedPreferences sharedPreferences;
    private ArrayList<String> descList;
    private ArrayList<String> ssidList;
    private static int count = 0;

    //Constructor
    public SavedNetworks(SharedPreferences sharedIn){
        sharedPreferences = sharedIn;

        Set<String> SSID_StringSet = sharedPreferences.getStringSet(SSID_LIST, null);
        Set<String> descStringSet = sharedPreferences.getStringSet(DESC_LIST, null);

        if(SSID_StringSet != null) {
            ssidList = new ArrayList<String>(SSID_StringSet);
            descList = new ArrayList<String>(descStringSet);
        }else{
            Log.d("NOTIFI","EMPTY Shared Preference");
            ssidList = new ArrayList<>();
            descList = new ArrayList<>();
            ssidList.add(count + "This is where the SSID will go");
            descList.add(count + "This is where the description will go");
            count++;
        }
    }

    //Returns a boolean dependant on if a NotiFi has been saved with the matching SSID
    public boolean isSaved(String checkSSID){
       for(String saveSSID : ssidList){
           if(saveSSID.substring(1).equals(checkSSID)){
               return true;
           }
       }
       return false;
    }

    //Returns the description to a matching SSID
    public String getDesc(String checkSSID){
        for(int i = 0; i < ssidList.size(); i++){
            if(ssidList.get(i).substring(1).equals(checkSSID)){
                return descList.get(i).substring(1);
            }
        }
        return "ERROR: NO DESC SET";
    }

    //Removes a NotiFi from SharedPreferences
    public void removeNetwork(String ssid){
        for(int i = 0; i < ssidList.size(); i++){
            if(ssidList.get(i).substring(1).equals(ssid)){
                for(int y = 0; y < descList.size(); y++){
                    if(ssidList.get(i).charAt(0) == descList.get(y).charAt(0)){
                        ssidList.remove(i);
                        descList.remove(y);
                        break;
                    }
                }
            }
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> ssidSet = new HashSet<>();
        Set<String> descSet = new HashSet<>();
        ssidSet.addAll(ssidList);
        descSet.addAll(descList);
        editor.putStringSet(SSID_LIST, ssidSet);
        editor.putStringSet(DESC_LIST, descSet);
        editor.apply();
        Log.d("NOTIFI", "Remove method called");
    }

    //Adds a new NotiFi
    public void addNetwork(String ssid, String desc){
        ssidList.add(count + ssid);
        descList.add(count + desc);
        count++;

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> ssidSet = new HashSet<>();
        Set<String> descSet = new HashSet<>();
        ssidSet.addAll(ssidList);
        descSet.addAll(descList);
        editor.putStringSet(SSID_LIST, ssidSet);
        editor.putStringSet(DESC_LIST, descSet);
        editor.apply();
        Log.d("NOTIFI", "ADD method called");
    }

    //ssidList getter
    public List<String> getSSIDList(){
        return ssidList;
    }

    //Returns a list of strings with the SSID and description in one string
    public List<String> getCombinedList(){
        String[] combined = new String[ssidList.size()];

        for(int i = 0; i < ssidList.size(); i++){
            for(int y = 0; y < descList.size(); y++) {
                if(ssidList.get(i).charAt(0) == descList.get(y).charAt(0)) {
                    combined[i] = "SSID: " + ssidList.get(i).substring(1) + "\nDescription: " + descList.get(y).substring(1) + ".";
                    break;
                }
            }
        }
        return Arrays.asList(combined);
    }
 }
