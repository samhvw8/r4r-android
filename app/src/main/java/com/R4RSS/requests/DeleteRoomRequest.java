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
public class DeleteRoomRequest extends StringRequest {

    private static final String DELETE_REQUEST_URL = "http://52.36.12.106/api/v1/room/";
    private Map<String, String> params;

    public DeleteRoomRequest(int id, Response.Listener<String> listener) {
        super(Method.DELETE, DELETE_REQUEST_URL + Integer.toString(id), listener, null);
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


