package com.R4RSS.r4r;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.R4RSS.GlobalValues;
import com.R4RSS.models.RoomModel;
import com.R4RSS.requests.FindInMapRequest;
import com.R4RSS.requests.RegisterRequest;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Son on 5/19/2016.
 */
public class FindInMap extends AppCompatActivity {

    int flag = 0;
    EditText etRadius;
    double radius;
    Button btnFindInMap;
    LatLng[] latLng = new LatLng[1];
    GoogleMap googleMap;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_in_map_layout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        etRadius = (EditText) findViewById(R.id.etRadius);
        btnFindInMap = (Button) findViewById(R.id.btnFindInMap);


        googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.findInMap)).getMap();
        googleMap.getUiSettings().setZoomGesturesEnabled(true);

        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                new LatLng(21.0278, 105.8342)).zoom(13).build();

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        latLng[0] = new LatLng(21.0278, 105.8342);


//        radius = Double.parseDouble(etRadius.getText().toString());
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                flag = 1;
                googleMap.clear();
                //tao marker cho diem dc click
                googleMap.addMarker(new MarkerOptions().position(point));

                latLng[0] = new LatLng(point.latitude, point.longitude);
            }
        });


        btnFindInMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double radius = Double.parseDouble(etRadius.getText().toString());


                double latitude = latLng[0].latitude;
                double longitude = latLng[0].longitude;

                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().position(latLng[0]));
                Circle circle = googleMap.addCircle(new CircleOptions()
                        .center(new LatLng(latitude, longitude))
                        .radius(radius * 1000)
                        .strokeColor(Color.RED)
                        .strokeWidth(2)
                        .fillColor(Color.TRANSPARENT));


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("response 2: ", response);
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean status = Boolean.parseBoolean(jsonResponse.opt("status").toString());

                            if (status) {
                                JSONObject dataObject = jsonResponse.getJSONObject("data");
                                JSONArray roomArray = dataObject.getJSONArray("room");

                                for (int i = 0; i < roomArray.length(); i++) {
                                    JSONObject finalObject = roomArray.getJSONObject(i);
                                    RoomModel roomModel = new RoomModel();

                                    String street = finalObject.getString("street");
                                    String district = finalObject.getString("district");
                                    String ward = finalObject.getString("ward");
                                    String city = finalObject.getString("city");

                                    String address = street + "-" + district + "-" + ward + "-" + city;

                                    roomModel.setLatitude(Double.parseDouble(finalObject.optString("latitude").toString()));
                                    roomModel.setLongtitude(Double.parseDouble(finalObject.optString("longitude").toString()));

                                    double lat;
                                    double lng;

                                    lat = roomModel.getLatitude();
                                    lng = roomModel.getLongtitude();

                                    LatLng roomLocation = new LatLng(lat, lng);
                                    Marker TP = googleMap.addMarker(new MarkerOptions().position(roomLocation).title(address).icon(BitmapDescriptorFactory
                                            .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

                                }


                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(FindInMap.this);
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


                FindInMapRequest findInMapRequest = new FindInMapRequest(latitude, longitude, radius, responseListener);
                RequestQueue queue = Volley.newRequestQueue(FindInMap.this);
                queue.add(findInMapRequest);

            }
        });


    }


    public LatLng getLatLng(final GoogleMap googleMap) {
        final LatLng[] latLng = new LatLng[1];
        //latLng[0] = new LatLng(21.0278,105.8342);
        if (googleMap != null) {
            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng point) {
                    googleMap.clear();
                    //tao marker cho diem dc click
                    googleMap.addMarker(new MarkerOptions().position(point));
                    //lay toa do diem dc click

                    latLng[0] = new LatLng(point.latitude, point.longitude);
                }
            });

            return latLng[0];
        }

        return null;
    }


    @Override
    public void onResume() {
        super.onResume();
//        initilizeMap();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
