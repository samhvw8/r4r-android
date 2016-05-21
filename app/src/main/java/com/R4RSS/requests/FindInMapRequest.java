package com.R4RSS.requests;

import com.R4RSS.GlobalValues;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sinzi on 5/21/2016.
 */
public class FindInMapRequest extends StringRequest {

    private static final String FIND_IN_MAP_URL = "http://52.36.12.106/api/v1/room/search/near";
    private Map<String,String> params;

    public FindInMapRequest(double lat,double lng,double radius, Response.Listener<String> listener){
        super(Method.POST, FIND_IN_MAP_URL,listener,null);

        params = new HashMap<>();

        params.put("radius",radius + "");
        params.put("latitude",lat +"");
        params.put("longitude",lng + "");

//        params.put("name",name);
//        params.put("password",password);
//        params.put("email",email);
//        params.put("phone",phone);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
