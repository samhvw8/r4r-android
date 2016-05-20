package com.R4RSS.r4r;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Son on 4/19/2016.
 */
public class RoomDetail extends AppCompatActivity {
    TextView tvAddressDetail;
    TextView tvDescription;
    TextView tvArea;
    TextView price;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.room_detail);

//        Toolbar toolbar2 = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar2);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        GoogleMap googleMap;
        googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        Intent i = getIntent();

        double lat;
        double lng;

        lat = i.getDoubleExtra("lat",21);
        lng = i.getDoubleExtra("lng",57);
        //make marker
        String address = i.getStringExtra("address");

        LatLng roomLocation = new LatLng(lat, lng);
        Marker TP = googleMap.addMarker(new MarkerOptions().position(roomLocation).title(address));
//        googleMap.animateCamera( CameraUpdateFactory.zoomTo(3.0f) );
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(TP.getPosition(), 14));
//        CameraPosition cameraPosition = new CameraPosition.Builder()
//                .target(roomLocation).zoom(12).build();

        //show detail info

        String itemPrice = i.getStringExtra("price");
        String itemAddress = i.getStringExtra("address");
        String itemDescription = i.getStringExtra("description");
        String itemArea = i.getStringExtra("area");

        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvAddressDetail = (TextView) findViewById(R.id.tvAddressDetail);
        tvArea = (TextView) findViewById(R.id.tvArea);
        price = (TextView) findViewById(R.id.tvPriceDetail);

        tvAddressDetail.setText(itemAddress);
        tvDescription.setText(itemDescription);
        tvArea.setText(itemArea);
        price.setText(itemPrice);
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
