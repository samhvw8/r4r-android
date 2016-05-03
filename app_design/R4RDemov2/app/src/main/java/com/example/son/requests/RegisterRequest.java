package com.example.son.requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Son on 5/3/2016.
 */
public class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://52.36.12.106/api/v1/user";
    private Map<String,String> params;

    public RegisterRequest(String name, String password,String email, String phone, Response.Listener<String> listener){
        super(Method.POST,REGISTER_REQUEST_URL,listener,null);

        params = new HashMap<>();

        params.put("name",name);
        params.put("password",password);
        params.put("email",email);
        params.put("phone",phone);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
