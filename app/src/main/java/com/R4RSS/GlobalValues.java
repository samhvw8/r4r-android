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
    private static final String _USER_ROOM_REQUEST_URL = "http://52.36.12.106/api/v1/user/";
    public static final String FIND_IN_MAP_URL = "";

    private static String USER_ROOM_REQUEST_URL;


    public static SharedPreferences sharedPreferences;

    private static Context initCon;



    public static void init(Context context) {

        initCon = context;

        if(getStatus().equals("true")) {
            USER_ROOM_REQUEST_URL = _USER_ROOM_REQUEST_URL + Integer.toString(getId()) + "/rooms";
        }

    }

    public static void logout() {
        sharedPreferences = initCon.getSharedPreferences("r4r", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putString("status", "false");
        editor.commit();

        USER_ROOM_REQUEST_URL = null;
    }

    public static void login(String auth, String pass, String name, String phone, String email, String status, String createdDay, int id) {
        sharedPreferences = initCon.getSharedPreferences("r4r", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("pass", pass);
        editor.putString("auth", auth);
        editor.putString("name", name);
        editor.putString("phone", phone);
        editor.putString("email", email);
        editor.putString("status", status);
        editor.putString("created_day", createdDay);
        editor.putInt("id", id);
        editor.commit();

        USER_ROOM_REQUEST_URL = _USER_ROOM_REQUEST_URL + Integer.toString(getId()) + "/rooms";
    }

    public static Context getContext() {
        return initCon;
    }

    public static String getName() {
        sharedPreferences = initCon.getSharedPreferences("r4r", Context.MODE_PRIVATE);
        return sharedPreferences.getString("name", null);
    }

    public static String getPhone() {
        sharedPreferences = initCon.getSharedPreferences("r4r", Context.MODE_PRIVATE);
        return sharedPreferences.getString("phone", null);
    }

    public static String getEmail() {
        sharedPreferences = initCon.getSharedPreferences("r4r", Context.MODE_PRIVATE);
        return sharedPreferences.getString("email", null);
    }

    public static String getStatus() {

        sharedPreferences = initCon.getSharedPreferences("r4r", Context.MODE_PRIVATE);
        return sharedPreferences.getString("status", null);
    }

    public static String getCreatedDay() {
        sharedPreferences = initCon.getSharedPreferences("r4r", Context.MODE_PRIVATE);
        return sharedPreferences.getString("created_day", null);
    }

    public static int getId() {

        sharedPreferences = initCon.getSharedPreferences("r4r", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("id", -1);
    }

    public static String getAuth() {
        sharedPreferences = initCon.getSharedPreferences("r4r", Context.MODE_PRIVATE);
        return sharedPreferences.getString("auth", null);
    }

    public static String getPass() {
        sharedPreferences = initCon.getSharedPreferences("r4r", Context.MODE_PRIVATE);
        return sharedPreferences.getString("pass", null);
    }

    public static String getUserRoomRequestUrl() {
        return USER_ROOM_REQUEST_URL;
    }
}
