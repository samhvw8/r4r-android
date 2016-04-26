package com.example.son.r4rdemov2;

import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Son on 4/24/2016.
 */
public class SearchRequest extends StringRequest {

    private static final String SEARCH_REQUEST_URL = "http://52.36.12.106/api/v1/room/search/realestate";
    private Map<String, String> params;

    public SearchRequest(String street,String district,String ward, String city,int minPrice, int maxPrice, double minArea, double maxArea, Response.Listener<String> listener){
        super(Method.POST,SEARCH_REQUEST_URL, listener, null);
        params = new HashMap<>();
//        String creds = String.format("%s:%s","samhv.ict@gmail.com","w8c8aaff");
//        String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
//        params.put("Authorization", auth);

        params.put("minPrice",minPrice +"");
        params.put("maxPrice",maxPrice + "");
        params.put("minArea",minArea + "");
        params.put("maxArea",maxArea + "");
        params.put("street" , street);
        params.put("district", district);
        params.put("ward", ward);
        params.put("city", city);
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
