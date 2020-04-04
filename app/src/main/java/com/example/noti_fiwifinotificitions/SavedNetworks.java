package com.example.noti_fiwifinotificitions;
/* Class is used to access Saved networks and descriptions through
* the sharedPreferences.
 */

import android.content.SharedPreferences;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SavedNetworks extends AppCompatActivity {

    //Constants
    public static final String NOTIFIOBJECT_LIST = "NOTIFIOBJECT_LIST";

    //Variables
    private SharedPreferences sharedPreferences;
    private ArrayList<NotiFiObject> notiFiObjects;

    //Constructor
    public SavedNetworks(SharedPreferences sharedIn){
        sharedPreferences = sharedIn;

        String json = sharedPreferences.getString(NOTIFIOBJECT_LIST, null);

        if(json != null) {
            Gson gson = new Gson();
            notiFiObjects = gson.fromJson(json,new TypeToken<List<NotiFiObject>>(){}.getType());
        }else{
            Log.d("NOTIFI","EMPTY Shared Preference NOTIFIS");
            notiFiObjects = new ArrayList<>();
            notiFiObjects.add(new NotiFiObject("EMTPY SSID", "EMPTY DESC"));
        }
    }

    //Returns a boolean dependant on if a NotiFi has been saved with the matching SSID
    public boolean notifiIsSaved(String checkSSID){
       for(NotiFiObject currentObject  : notiFiObjects){
           if(currentObject.getSSID().equals(checkSSID)){
               return true;
           }
       }
       return false;
    }

    //Returns the description to a matching SSID
    public String getNotiFiDesc(String checkSSID){
        for(int i = 0; i < notiFiObjects.size(); i++){
            if(notiFiObjects.get(i).getSSID().equals(checkSSID)){
                return notiFiObjects.get(i).getDESC();
            }
        }
        return "ERROR: NO DESC SET";
    }

    //Removes a NotiFi from SharedPreferences
    public void removeNotiFi(String checkSSID){
        for(int i = 0; i < notiFiObjects.size(); i++){
            if(notiFiObjects.get(i).getSSID().equals(checkSSID)){
                notiFiObjects.remove(i);
                break;
            }
        }
        //Convert into json string and put into sharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(notiFiObjects);
        editor.putString(NOTIFIOBJECT_LIST,json);
        editor.apply();
        Log.d("NOTIFI", "Remove method called");
    }

    //Adds a new NotiFi
    public void addNotiFi(String ssid, String desc){
        if(!notifiIsSaved(ssid)) {
            notiFiObjects.add(new NotiFiObject(ssid,desc));

            //Convert into json string and put into sharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(notiFiObjects);
            editor.putString(NOTIFIOBJECT_LIST,json);
            editor.apply();
            Log.d("NOTIFI", "SavedNetwork ADD method called");
        }
    }

    //Returns a list of strings with the SSID and description in one string
    public List<String> getCombinedList(){
        String[] combined = new String[notiFiObjects.size()];

        for(int i = 0; i < notiFiObjects.size(); i++){
            combined[i] = notiFiObjects.get(i).getSSID() + "\n" + notiFiObjects.get(i).getDESC() +".";
        }
        return Arrays.asList(combined);
    }

    public ArrayList<NotiFiObject> getNotiFiObjects() {
        return notiFiObjects;
    }
}
