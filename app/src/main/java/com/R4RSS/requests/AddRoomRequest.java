package com.R4RSS.requests;

import android.util.Log;

import com.R4RSS.GlobalValues;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class AddRoomRequest extends StringRequest {


    private Map<String, String> params;

    public AddRoomRequest(String city, String ward, String district, String street, int price, double area, String description,String imgUrl, Response.Listener<String> listener) {
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
        if(imgUrl != null)
            params.put("image_album_url",imgUrl);



    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> header = new HashMap<String, String>();

        String auth = GlobalValues.getAuth();
        header.put("Content-Type","application/x-www-form-urlencoded");
        header.put("Authorization", auth);
        return header;
    }
}
