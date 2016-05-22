package com.R4RSS.r4r;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.R4RSS.GlobalValues;
import com.R4RSS.requests.EditUserRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sinzi on 5/22/2016.
 */
public class EditUser extends AppCompatActivity {

    EditText etEditName;
    EditText etEditEmail;
    EditText etEditPhone;
    Button btnEditAcc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_user_layout);


        etEditName = (EditText) findViewById(R.id.etEditName);
        etEditEmail = (EditText) findViewById(R.id.etEditEmail);
        etEditPhone = (EditText) findViewById(R.id.etEditPhone);

        btnEditAcc = (Button) findViewById(R.id.btnEditAcc);

        etEditName.setText(GlobalValues.getName());
        etEditEmail.setText(GlobalValues.getEmail());
        etEditPhone.setText(GlobalValues.getPhone());

        btnEditAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = etEditName.getText().toString();
                final String email = etEditEmail.getText().toString();
                final String phone = etEditPhone.getText().toString();

                int id = GlobalValues.getId();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean status = Boolean.parseBoolean(jsonResponse.opt("status").toString());
                            if (status) {


                                final String base64Source = email + ":" + GlobalValues.getPass();
                                final String base64String = "Basic " + Base64.encodeToString(base64Source.getBytes(), Base64.DEFAULT);

                                GlobalValues.login(base64String, GlobalValues.getPass(), name, phone, email, "true", GlobalValues.getCreatedDay(), GlobalValues.getId());

                                Intent intent = new Intent(EditUser.this,MainActivity.class);
                                startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(EditUser.this);
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

                EditUserRequest editUserRequest = new EditUserRequest(id, name, email, phone, responseListener);
                RequestQueue queue = Volley.newRequestQueue(EditUser.this);
                queue.add(editUserRequest);
            }
        });

    }
}
