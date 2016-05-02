package com.example.son.r4rdemov2;

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
import com.example.son.fragments.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Son on 4/21/2016.
 */
public class PopLogin extends Activity {


    private static final String LOGIN_REQUEST_URL = "http://52.36.12.106/api/v1/login";
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Name = "name";
    public static final String Phone = "phone";
    public static final String Email = "email";
    public static final String Status = "status";
    public static final String CreatedDay = "created_at";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpopup);

        //create pop up window
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.6),(int) (height*.4));

        //consider input vaule to login

        final EditText etUserName = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button btnPopLogin = (Button) findViewById(R.id.btnPopLogin);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        btnPopLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etUserName.getText().toString();
                final String password = etPassword.getText().toString();
                final String base64Source = username + ":" + password;
                final String base64String = "Basic " + Base64.encodeToString(base64Source.getBytes(), Base64.DEFAULT);


                RequestQueue queue = Volley.newRequestQueue(PopLogin.this);
                JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, LOGIN_REQUEST_URL, null,
                        new Response.Listener<JSONObject>()
                        {
                            @Override
                            public void onResponse(JSONObject response) {
                                // display response
                                Log.d("Response", response.toString());
                                //save info into share preference to later use
                                boolean status = Boolean.parseBoolean(response.opt("status").toString());
                                if(status){
                                    String name;
                                    String phone;
                                    String email;
                                    String day;
                                    try {
                                        JSONObject data = response.getJSONObject("data");
                                        JSONObject user = data.getJSONObject("user");

                                        name = user.getString("name");
                                        phone = user.getString("phone");
                                        email = user.getString("email");
                                        day = user.getString("created_at");

                                        SharedPreferences.Editor editor = sharedPreferences.edit();

                                        String statusPass = Boolean.toString(status);

                                        editor.putString(Name, name);
                                        editor.putString(Phone, phone);
                                        editor.putString(Email, email);
                                        editor.putString(Status,statusPass);
                                        editor.putString(CreatedDay,day);
                                        editor.commit();

//                                        Intent intent = new Intent(PopLogin.this,LoginResult.class);
////                                        intent.putExtra(Name,name);
////                                        intent.putExtra(Phone,phone);
////                                        intent.putExtra(Email,email);
////                                        intent.putExtra(CreatedDay,day);
//                                        startActivity(intent);

                                        Intent intent = new Intent(PopLogin.this, MainActivity.class);
                                        startActivity(intent);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Error.Response", error.toString());
                            }
                        }


                ){
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
