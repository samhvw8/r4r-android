package com.R4RSS.requests;

import com.R4RSS.GlobalValues;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sinzi on 5/22/2016.
 */
public class EditUserRequest extends StringRequest {

    private static final String EDIT_USER_REQUEST_URL = "http://52.36.12.106/api/v1/user/";
    private Map<String, String> params;

    public EditUserRequest(int id, String username,String email,String phone, Response.Listener<String> listener) {
        super(Method.PUT, EDIT_USER_REQUEST_URL +Integer.toString(id), listener, null);

        params = new HashMap<>();

//        params.put("id",id +"");
        params.put("name", username);
        params.put("email", email);
        params.put("phone", phone);



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
