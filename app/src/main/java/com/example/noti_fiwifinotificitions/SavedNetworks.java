package com.example.noti_fiwifinotificitions;
/* Description
 */

import android.content.SharedPreferences;
import android.util.Log;

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
            ssidList.add("This is where the SSID will go");
            descList.add("This is where the description will go");
        }
    }

    //Returns a boolean dependant on if a NotiFi has been saved with the matching SSID
    public boolean isSaved(String checkSSID){
       for(String saveSSID : ssidList){
           if(saveSSID.equals(checkSSID)){
               return true;
           }
       }
       return false;
    }

    //Returns the description to a matching SSID
    public String getDesc(String checkSSID){
        for(int i = 0; i < ssidList.size(); i++){
            if(ssidList.get(i).equals(checkSSID)){
                return descList.get(i);
            }
        }
        return "ERROR: NO DESC SET";
    }

    //Removes a NotiFi from SharedPreferences
    public void removeNetwork(String ssid){

    }

    //Adds a new NotiFi
    public void addNetwork(String ssid, String desc){
        ssidList.add(ssid);
        descList.add(desc);

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
            combined[i] = "SSID: " + ssidList.get(i) + "\nDescription: " + descList.get(i);
        }
        return Arrays.asList(combined);
    }
 }

 /*

 //Retrieve the values
Set<String> set = prefs.getStringSet("yourKey", null);
List<String> sample=new ArrayList<String>(set);

SharedPreferences prefs=this.getSharedPreferences("yourPrefsKey",Context.MODE_PRIVATE);
Editor edit=prefs.edit();

Set<String> set = new HashSet<String>();
set.addAll(your Arraylist Name);
edit.putStringSet("yourKey", set);
edit.commit();
  */
