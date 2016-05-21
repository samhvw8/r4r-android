package com.R4RSS.r4r;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.R4RSS.GlobalValues;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.R4RSS.requests.RegisterRequest;

import org.json.JSONException;
import org.json.JSONObject;

//import butterknife.Bind;


public class PopRegister extends Activity {

    private static final String Name = "name";
    private static final String Phone = "phone";
    private static final String Email = "email";
    private static final String Status = "status";
    private static final String CreatedDay = "created_at";
    private static final String Id = "id";


    EditText etResUsername;
    EditText etResPassword;
    EditText etResEmail;
    EditText etResPhone;
    Button btnResRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerpopup);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));

        etResUsername = (EditText) findViewById(R.id.etResUsername);
        etResPassword = (EditText) findViewById(R.id.etResPassword);
        etResEmail = (EditText) findViewById(R.id.etResEmail);
        etResPhone = (EditText) findViewById(R.id.etResPhone);
        btnResRegister = (Button) findViewById(R.id.btnResRegister);


        btnResRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = etResUsername.getText().toString();
                final String password = etResPassword.getText().toString();
                final String email = etResEmail.getText().toString();
                final String phone = etResPhone.getText().toString();


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
//                            Log.d("Response", response.toString());
                            String result = response;
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean status = Boolean.parseBoolean(jsonResponse.opt("status").toString());

                            if (status) {

                                int id;
                                JSONObject data = jsonResponse.getJSONObject("data");
                                JSONObject user = data.getJSONObject("user");


                                String day = user.getString("created_at");
                                id = Integer.parseInt(user.optString("id").toString());

                                final String base64Source = email + ":" + password;
                                final String base64String = "Basic " + Base64.encodeToString(base64Source.getBytes(), Base64.DEFAULT);
                                String statusPass = Boolean.toString(status);
                                GlobalValues.login(base64String, name, phone, email, statusPass, day, id);

                                Intent intent = new Intent(PopRegister.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(PopRegister.this);
                                builder.setMessage("Search Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(name, password, email, phone, responseListener);
                RequestQueue queue = Volley.newRequestQueue(PopRegister.this);
                queue.add(registerRequest);
            }
        });
    }
}

