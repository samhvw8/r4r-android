package com.example.son.requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Son on 5/3/2016.
 */
public class AddRoomRequest extends StringRequest {

    private static final String ADD_ROOM_REQUEST_URL = "http://52.36.12.106/api/v1/room";
    private Map<String,String> params;

    public AddRoomRequest(int id,String city, String ward, String district, String street,int price, double area, String description, Response.Listener<String> listener){
        super(Method.POST,ADD_ROOM_REQUEST_URL,listener,null);

        params = new HashMap<>();

        params.put("id",id +"");
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
}
