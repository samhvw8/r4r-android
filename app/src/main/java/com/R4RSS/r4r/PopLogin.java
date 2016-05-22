package com.R4RSS.r4r;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import com.R4RSS.GlobalValues;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Son on 4/21/2016.
 */
public class PopLogin extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpopup);

        //create pop up window
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .6), (int) (height * .4));

        //consider input vaule to login

        final EditText etUserName = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button btnPopLogin = (Button) findViewById(R.id.btnPopLogin);

        btnPopLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = etUserName.getText().toString();
                final String password = etPassword.getText().toString();
                final String base64Source = email + ":" + password;
                final String base64String = "Basic " + Base64.encodeToString(base64Source.getBytes(), Base64.DEFAULT);


                RequestQueue queue = Volley.newRequestQueue(PopLogin.this);
                JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, GlobalValues.LOGIN_REQUEST_URL, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // display response
                                Log.d("Response", response.toString());
                                //save info into share preference to later use
                                boolean status = Boolean.parseBoolean(response.opt("status").toString());
                                if (status) {

                                    String day;
                                    int id;
                                    try {
                                        JSONObject data = response.getJSONObject("data");
                                        JSONObject user = data.getJSONObject("user");

                                        String name = user.getString("name");
                                        String phone = user.getString("phone");
                                        day = user.getString("created_at");
                                        id = Integer.parseInt(user.optString("id").toString());


                                        String statusPass = Boolean.toString(status);

                                        GlobalValues.login(base64String, password, name, phone, email, statusPass, day, id);

                                        Intent intent = new Intent(PopLogin.this, MainActivity.class);
                                        startActivity(intent);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Error.Response", error.toString());
                            }
                        }
                ) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Authorization", base64String);
                        return headers;
                    }
                };

                // add it to the RequestQueue
                queue.add(getRequest);
            }

        });


    }
}
