package com.R4RSS.requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Son on 5/19/2016.
 */
public class SearchInMapRequest extends StringRequest {

    private static final String SEARCH_REQUEST_URL = "http://52.36.12.106/api/v1/room/search/realestate";
    private Map<String, String> params;

    public SearchInMapRequest(String city, Response.Listener<String> listener){
        super(Method.POST,SEARCH_REQUEST_URL, listener, null);
        params = new HashMap<>();

        params.put("city", city);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
