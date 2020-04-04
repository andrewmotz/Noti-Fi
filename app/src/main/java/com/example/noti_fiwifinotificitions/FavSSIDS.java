package com.example.noti_fiwifinotificitions;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class FavSSIDS extends AppCompatActivity {

    public static final String FAV_SSIDS = "FAV_SSIDS";
    private SharedPreferences sharedPreferences;
    private ArrayList<String> favSSIDList;

    public FavSSIDS(SharedPreferences sharedIn){
        sharedPreferences = sharedIn;

        String json = sharedPreferences.getString(FAV_SSIDS, null);

        if(json != null) {
            Gson gson = new Gson();
            favSSIDList = gson.fromJson(json,new TypeToken<List<String>>(){}.getType());
        }else{
            Log.d("NOTIFI","EMPTY Shared Preference FAVSSIDS");
            favSSIDList = new ArrayList<>();
        }
    }

    public boolean favSSIDIsSaved(String checkSSID){
        for(int i = 0; i < favSSIDList.size(); i++){
            if(favSSIDList.get(i).equals(checkSSID)){
                return true;
            }
        }
        return false;
    }

    public void removeFavSSID(String removeSSID){
        int initialSize = favSSIDList.size();
        for(int i = 0; i < favSSIDList.size() && initialSize == favSSIDList.size(); i++){
            Log.i("NOTIFI", "Checking " + favSSIDList.get(i));
            if(favSSIDList.get(i).equals(removeSSID)){
                favSSIDList.remove(i);
            }
        }
        //Save to shared preferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(favSSIDList);
        editor.putString(FAV_SSIDS, json);
        editor.apply();
        Log.d("NOTIFI", "FavSSID ADD method called");
    }

    public void addFavSSID(String newSSID){
        if(!favSSIDIsSaved(newSSID)) {
            favSSIDList.add(newSSID);

            //Save to shared preferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(favSSIDList);
            editor.putString(FAV_SSIDS, json);
            editor.apply();
            Log.d("NOTIFI", "FavSSID ADD method called");
        }
    }

    public ArrayList<String> getFavSSIDList(){
        return favSSIDList;
    }

    public int getSize(){
        return favSSIDList.size();
    }
}
