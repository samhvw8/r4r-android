package com.R4RSS.requests;

import android.content.SharedPreferences;
import android.util.Base64;

import com.R4RSS.GlobalValues;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddRoomRequest extends StringRequest {


    private Map<String, String> params;

    public AddRoomRequest(String city, String ward, String district, String street, int price, double area, String description, Response.Listener<String> listener) {
        super(Method.POST, GlobalValues.ADD_ROOM_REQUEST_URL, listener, null);

        params = new HashMap<>();

//        params.put("id",id +"");
        params.put("city", city);
        params.put("ward", ward);
        params.put("district", district);
        params.put("street", street);
        params.put("price", price + "");
        params.put("area", area + "");
        params.put("description", description);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> params = new HashMap<String, String>();

        String auth = GlobalValues.getAuth();
        params.put("Authorization", auth);
        return params;
    }
}
