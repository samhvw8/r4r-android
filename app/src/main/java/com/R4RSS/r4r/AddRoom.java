package com.R4RSS.r4r;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.R4RSS.requests.AddRoomRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Son on 5/3/2016.
 */
public class AddRoom extends AppCompatActivity {

    private static final String MyPREFERENCES = "MyPrefs";
    private static final String Name = "name";
    private static final String Phone = "phone";
    private static final String Email = "email";
    private static final String Status = "status";
    private static final String CreatedDay = "created_at";
    private static final String Id = "id";
    SharedPreferences sharedPreferences;

    EditText etAddStreet;
    EditText etAddWard;
    EditText etAddDistrict;
    EditText etAddCity;
    EditText etAddPrice;
    EditText etAddArea;
    EditText etAddRoomDes;
    Button btnAdd;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_room_layout);

        etAddStreet = (EditText) findViewById(R.id.etAddStreet);
        etAddWard = (EditText) findViewById(R.id.etAddWard);
        etAddDistrict = (EditText) findViewById(R.id.etAddDistrict);
        etAddCity = (EditText) findViewById(R.id.etAddCity);
        etAddPrice = (EditText) findViewById(R.id.etAddPrice);
        etAddArea = (EditText) findViewById(R.id.etAddArea);
        etAddRoomDes = (EditText) findViewById(R.id.etAddRoomDes);
        btnAdd = (Button) findViewById(R.id.btnAdd);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city;
                String ward;
                String district;
                String street;
                String description;
                int price;
                double area;
                int id;

                city = etAddCity.getText().toString();
                ward = etAddWard.getText().toString();
                district = etAddDistrict.getText().toString();
                street = etAddStreet.getText().toString();
                description = etAddRoomDes.getText().toString();
                price = Integer.parseInt(etAddPrice.getText().toString());
                area = Double.parseDouble(etAddArea.getText().toString());
//                SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
//                String userId = sharedPreferences.getString(Id, null);
//                id = Integer.parseInt(userId);

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String result = response;
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean status = Boolean.parseBoolean(jsonResponse.opt("status").toString());

                            if (status) {
                                Intent intent = new Intent(AddRoom.this, MainActivity.class);
//                                intent.putExtra("response", result);
                                startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(AddRoom.this);
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

                AddRoomRequest addRoomRequest = new AddRoomRequest(city, ward, district, street, price, area, description, responseListener);
                RequestQueue queue = Volley.newRequestQueue(AddRoom.this);
                queue.add(addRoomRequest);
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "AddRoom Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.R4RSS.r4r/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "AddRoom Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.R4RSS.r4r/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
