package com.R4RSS.requests;

import android.content.SharedPreferences;
import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Son on 5/3/2016.
 */
public class AddRoomRequest extends StringRequest {

    private static final String MyPREFERENCES = "MyPrefs";
    private static final String Name = "name";
    private static final String Phone = "phone";
    private static final String Email = "email";
    private static final String Status = "status";
    private static final String CreatedDay = "created_at";
    private static final String Id = "id";
    SharedPreferences sharedPreferences;

    private static final String ADD_ROOM_REQUEST_URL = "http://52.36.12.106/api/v1/room";
    private Map<String,String> params;

    public AddRoomRequest(String city, String ward, String district, String street,int price, double area, String description, Response.Listener<String> listener){
        super(Method.POST,ADD_ROOM_REQUEST_URL,listener,null);

        params = new HashMap<>();

//        params.put("id",id +"");
        params.put("city",city);
        params.put("ward",ward);
        params.put("district",district);
        params.put("street",street);
        params.put("price",price + "");
        params.put("area",area + "");
        params.put("description",description);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

        @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> params = new HashMap<String, String>();
        String creds = String.format("%s:%s","samhv.ict@gmail.com","w8c8aaff");
        String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
        params.put("Authorization", auth);
        return params;
    }
}
