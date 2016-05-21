package com.R4RSS;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by samhv on 20/05/2016.
 */
public class GlobalValues {
    public static final boolean LOGGING = false;

    /*
      Your imgur client id. You need this to upload to imgur.

      More here: https://api.imgur.com/
     */
    public static final String MY_IMGUR_CLIENT_ID = "7a36a635dea83be";
    public static final String MY_IMGUR_CLIENT_SECRET = "dc87beadd4f8497a1e201ee03798298c8d4b5ce9";

    /*
      Client Auth
     */
    public static String getClientAuth() {
        return "Client-ID " + MY_IMGUR_CLIENT_ID;
    }

    public static final String LOGIN_REQUEST_URL = "http://52.36.12.106/api/v1/login";
    public static final String ADD_ROOM_REQUEST_URL = "http://52.36.12.106/api/v1/room";
    public static final String FIND_IN_MAP_URL = "";


    public static SharedPreferences sharedPreferences;

    private static Context preCon;

    public static void init(Context context){
        preCon = context;
    }

    public static void logout() {
        sharedPreferences = preCon.getSharedPreferences("r4r", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putString("status", "false");
        editor.commit();
    }

    public static void login(String auth, String name, String phone, String email, String status, String createdDay, int id) {
        sharedPreferences = preCon.getSharedPreferences("r4r", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("auth", auth);
        editor.putString("name", name);
        editor.putString("phone", phone);
        editor.putString("email", email);
        editor.putString("status", status);
        editor.putString("created_day", createdDay);
        editor.putInt("id", id);
        editor.commit();
    }

    public static String getName() {
        sharedPreferences = preCon.getSharedPreferences("r4r", Context.MODE_PRIVATE);
        return sharedPreferences.getString("name", null);
    }

    public static String getPhone() {
        sharedPreferences = preCon.getSharedPreferences("r4r", Context.MODE_PRIVATE);
        return sharedPreferences.getString("phone", null);
    }

    public static String getEmail() {
        sharedPreferences = preCon.getSharedPreferences("r4r", Context.MODE_PRIVATE);
        return sharedPreferences.getString("email", null);
    }

    public static String getStatus() {

        sharedPreferences = preCon.getSharedPreferences("r4r", Context.MODE_PRIVATE);
        return sharedPreferences.getString("status", null);
    }

    public static String getCreatedDay() {
        sharedPreferences = preCon.getSharedPreferences("r4r", Context.MODE_PRIVATE);
        return sharedPreferences.getString("created_day", null);
    }

    public static int getId() {

        sharedPreferences = preCon.getSharedPreferences("r4r", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("id", -1);
    }

    public static String getAuth() {
        sharedPreferences = preCon.getSharedPreferences("r4r", Context.MODE_PRIVATE);
        return sharedPreferences.getString("auth", null);
    }
}
