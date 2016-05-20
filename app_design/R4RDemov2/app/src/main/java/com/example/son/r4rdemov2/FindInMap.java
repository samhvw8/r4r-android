package com.example.son.r4rdemov2;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.son.adapters.RoomAdapter;
import com.example.son.models.RoomModel;
import com.example.son.r4rdemov2.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Son on 5/19/2016.
 */
public class FindInMap extends AppCompatActivity {

    private GoogleMap googleMap;
    List<RoomModel> roomModelList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_in_map_layout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent = getIntent();

        String response = intent.getStringExtra("response");
        JSONObject parentObject = null;
        try {
            parentObject = new JSONObject(response);
            JSONObject dataObject = parentObject.getJSONObject("data");
            JSONArray roomArray = dataObject.getJSONArray("room");



            for (int i = 0; i < roomArray.length(); i++) {
                JSONObject finalObject = roomArray.getJSONObject(i);
                RoomModel roomModel = new RoomModel();
                roomModel.setImage_album_url(finalObject.getString("image_album_url"));
                roomModel.setPrice(Integer.parseInt(finalObject.optString("price").toString()));
                roomModel.setCity(finalObject.getString("city"));
                roomModel.setDistrict(finalObject.getString("district"));
                roomModel.setWard(finalObject.getString("ward"));
                roomModel.setStreet(finalObject.getString("street"));
                roomModel.setDescription(finalObject.getString("decripstion"));
                roomModel.setArea(Double.parseDouble(finalObject.optString("area").toString()));
                //get create day
                String createDay = finalObject.getString("created_at");
                StringTokenizer tokenDay = new StringTokenizer(createDay, " ");
                roomModel.setCreated_day(tokenDay.nextToken().toString());


                //add kinh vi do
                //append dia chi
                //getkinh do vi do version 2
                roomModel.setLatitude(Double.parseDouble(finalObject.optString("latitude").toString()));
                roomModel.setLongtitude(Double.parseDouble(finalObject.optString("longitude").toString()));

                // adding the final object in the list
                roomModelList.add(roomModel);
            }
            initilizeMap(roomModelList);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    private void initilizeMap(List<RoomModel> roomModelList) {


        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.findInMap)).getMap();

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
        for(RoomModel room : roomModelList) {
            double lat;
            double lng;

            lat = room.getLatitude();
            lng = room.getLongtitude();

            LatLng roomLocation = new LatLng(lat, lng);
            Marker TP = googleMap.addMarker(new MarkerOptions().position(roomLocation));
        }
        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                new LatLng(21.0278, 105.8342)).zoom(12).build();

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onResume() {
        super.onResume();
        initilizeMap(roomModelList);
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
