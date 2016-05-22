package com.R4RSS.requests;

import com.R4RSS.GlobalValues;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sinzi on 5/21/2016.
 */
public class EditRoomRequest extends StringRequest {

    private static final String EDIT_REQUEST_URL = "http://52.36.12.106/api/v1/room/";
    private Map<String, String> params;

    public EditRoomRequest(int id,String city, String ward, String district, String street, int price, double area, String description, String imgUrl, Response.Listener<String> listener) {
        super(Method.PUT, EDIT_REQUEST_URL + Integer.toString(id), listener, null);

        params = new HashMap<>();

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


