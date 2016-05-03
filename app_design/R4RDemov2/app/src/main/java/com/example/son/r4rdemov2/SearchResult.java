package com.example.son.r4rdemov2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.son.adapters.RoomAdapter;
import com.example.son.models.RoomModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Son on 4/22/2016.
 */
public class SearchResult extends AppCompatActivity {

    private ListView lvRoom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.search_result_layout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent = getIntent();

        //load image to items
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(SearchResult.this)
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);

        //load JSON to ListView
        lvRoom = (ListView) findViewById(R.id.lvSearchResult);

        String response = intent.getStringExtra("response");
        JSONObject parentObject = null;
        try {
            parentObject = new JSONObject(response);
            JSONObject dataObject = parentObject.getJSONObject("data");
            JSONArray roomArray = dataObject.getJSONArray("room");

            List<RoomModel> roomModelList = new ArrayList<>();

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

            RoomAdapter adapter = new RoomAdapter(SearchResult.this, R.layout.room_feed, roomModelList);

            lvRoom.setAdapter(adapter);
            lvRoom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    RoomModel room = (RoomModel) lvRoom.getItemAtPosition(position);
                    //get dia chi vao activity RoomDetail
                    String street = room.getStreet();
                    String ward = room.getWard();
                    String district = room.getDistrict();
                    String city = room.getCity();
                    String address = "Address:\n" + street + "-" + ward + "-" + district + "-" + city + "\n";
                    String price = "Price:\n" + Integer.toString(room.getPrice()) + "VND\n";
                    String area = "Area:\n " + Double.toString(room.getArea()) + "m2\n";
                    String description = "Description:\n" + room.getDescription() + "\n";
                    //get toa do vao activity RoomDetail
                    double lat = room.getLatitude();
                    double lng = room.getLongtitude();
                    Intent intent = new Intent(SearchResult.this, RoomDetail.class);
                    intent.putExtra("price", price);
                    intent.putExtra("address", address);
                    intent.putExtra("area", area);
                    intent.putExtra("description", description);
                    intent.putExtra("lat", lat);
                    intent.putExtra("lng", lng);
                    startActivity(intent);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
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
