package com.example.son.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import android.support.v7.widget.LinearLayoutManager;

import com.example.son.adapters.RoomAdapter;

import com.example.son.models.RoomModel;
import com.example.son.r4rdemov2.MainActivity;
import com.example.son.r4rdemov2.R;
import com.example.son.r4rdemov2.RoomDetail;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.support.v7.widget.RecyclerView.OnScrollListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Son on 4/11/2016.
 */
public class HomeFragment extends Fragment {


    ListView lvRoom;
    //Waiting Dialog
    ProgressDialog mProgressDialog;
    //set limit to show
    private int limit = 7;


    private static final String URL_SELECT = "http://52.36.12.106//api/v1/rooms";


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home, container, false);

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity().getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config); // Do it on Application start
        lvRoom = (ListView) rootView.findViewById(R.id.lvRoom);

        //set all to address when click the item
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
                String address = "Address: " + street + "-" + ward + "-" + district + "-" + city + "\n";
                String price = "Price:\n" + Integer.toString(room.getPrice()) + "VND\n";
                String area = "Area:\n " + Double.toString(room.getArea()) + "m2\n";
                String description = "Description:\n" + room.getDescription() + "\n";
                //get toa do vao activity RoomDetail
//                String lat = Double.toString(room.getLatitude());
//                String lng = Double.toString(room.getLongtitude());
                double lat = room.getLatitude();
                double lng  = room.getLongtitude();
                Intent intent = new Intent(getActivity().getApplicationContext(), RoomDetail.class);
                intent.putExtra("price", price);
                intent.putExtra("address", address);
                intent.putExtra("area", area);
                intent.putExtra("description", description);
                intent.putExtra("lat",lat);
                intent.putExtra("lng", lng);
                startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadEvents().execute();
    }

    private void getJSON(View view) {

    }

    private class LoadEvents extends AsyncTask<String, String, List<RoomModel>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(getActivity());
            // Set progressdialog title
            mProgressDialog.setTitle("R4R Load More Room");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected List<RoomModel> doInBackground(String... params) {

            try {
                URL url = new URL(URL_SELECT);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                String newUser = "samhv.ict@gmail.com" +":"+"w8c8aaff";
//                String encode = Base64.encodeToString(newUser.getBytes(), Base64.DEFAULT);
//                httpURLConnection.setRequestProperty("Authorization","Basic "+encode);
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                JSONObject dataObject = parentObject.getJSONObject("data");
                JSONArray roomArray = dataObject.getJSONArray("rooms");
//               JSONArray subParentArray = parentObject.getJSONArray("rooms");
//                bufferedReader.close();
//                inputStream.close();
//                httpURLConnection.disconnect();
                List<RoomModel> roomModelList = new ArrayList<>();
                Log.d("Response", roomArray.toString());
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
                    StringTokenizer tokenDay = new StringTokenizer( createDay , " " );
                    roomModel.setCreated_day(tokenDay.nextToken().toString());


                    //add kinh vi do
                    //getkinh do vi do version 2
                    roomModel.setLatitude(Double.parseDouble(finalObject.optString("latitude").toString()));
                    roomModel.setLongtitude(Double.parseDouble(finalObject.optString("longitude").toString()));

                    // adding the final object in the list
                    roomModelList.add(roomModel);


                }
                httpURLConnection.disconnect();
                return roomModelList;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(List<RoomModel> result) {

            super.onPostExecute(result);

            RoomAdapter adapter = new RoomAdapter(getActivity().getApplicationContext(), R.layout.room_feed, result);

            lvRoom.setAdapter(adapter);
            //close the loading dialog
            mProgressDialog.dismiss();
        }
    }
}
