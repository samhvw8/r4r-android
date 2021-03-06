package com.R4RSS.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.R4RSS.GlobalValues;
import com.R4RSS.r4r.AddRoom;

import com.R4RSS.r4r.MainActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.R4RSS.adapters.RoomAdapter;
import com.R4RSS.models.RoomModel;
import com.R4RSS.r4r.R;
import com.R4RSS.r4r.RoomDetail;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import butterknife.OnClick;

/**
 * Created by Son on 4/11/2016.
 */
public class HomeFragment extends Fragment {


    ListView lvRoom;
    //Waiting Dialog
    ProgressDialog mProgressDialog;
    //set limit to show
    private int limit = 7;


    private static final String URL_SELECT = "http://52.36.12.106/api/v1/rooms";
    private static final String USER_INFO_URL = "http://52.36.12.106/api/v1/user/";


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

        // fab
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);


        boolean status = Boolean.parseBoolean(GlobalValues.getStatus());

        if (status) {
            fab.setVisibility(View.VISIBLE);
        } else {
            fab.setVisibility(View.INVISIBLE);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), AddRoom.class);
                startActivity(intent);
            }
        });


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
                final String address = street + "-" + ward + "-" + district + "-" + city + "\n";
                final String price = Integer.toString(room.getPrice()) + "VND\n";
                final String area = Double.toString(room.getArea()) + "m2\n";
                final String description = room.getDescription() + "\n";
                final String userId = Integer.toString(room.getUserId());
                //get toa do vao activity RoomDetail
//                String lat = Double.toString(room.getLatitude());
//                String lng = Double.toString(room.getLongtitude());
                final double lat = room.getLatitude();
                final double lng = room.getLongtitude();


                final RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, USER_INFO_URL + userId, null,
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

                                        String phone = user.getString("phone");
                                        String email = user.getString("email");
                                        String contact = "Email : "+ email +"\n"+ "Phone : " + phone;

                                        Intent intent = new Intent(getActivity().getApplicationContext(), RoomDetail.class);
                                        intent.putExtra("price", price);
                                        intent.putExtra("address", address);
                                        intent.putExtra("area", area);
                                        intent.putExtra("description", description);
                                        intent.putExtra("lat", lat);
                                        intent.putExtra("lng", lng);
                                        intent.putExtra("userId", userId);
                                        intent.putExtra("contact", contact);

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
                };

                // add it to the RequestQueue
                queue.add(getRequest);

            }
        });
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        new LoadEvents().execute();
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
//                Log.d("Response", roomArray.toString());
                for (int i = 0; i < roomArray.length(); i++) {
                    JSONObject finalObject = roomArray.getJSONObject(i);
                    RoomModel roomModel = new RoomModel();
                    if (!finalObject.getString("image_album_url").equals("null"))
                        roomModel.setImage_album_url(finalObject.getString("image_album_url"));

                    roomModel.setId(Integer.parseInt(finalObject.optString("id").toString()));
                    roomModel.setUserId(Integer.parseInt(finalObject.optString("user_id").toString()));
                    roomModel.setPrice(Integer.parseInt(finalObject.optString("price").toString()));
                    roomModel.setCity(finalObject.getString("city"));
                    roomModel.setDistrict(finalObject.getString("district"));
                    roomModel.setWard(finalObject.getString("ward"));
                    roomModel.setStreet(finalObject.getString("street"));
                    roomModel.setDescription(finalObject.getString("description"));
                    roomModel.setArea(Double.parseDouble(finalObject.optString("area").toString()));
                    //get create day
                    String createDay = finalObject.getString("created_at");
                    StringTokenizer tokenDay = new StringTokenizer(createDay, " ");
                    roomModel.setCreated_day(tokenDay.nextToken().toString());


                    //add kinh vi do
                    //getkinh do vi do version 2
                    if (!finalObject.optString("latitude").toString().equals("null"))
                        roomModel.setLatitude(Double.parseDouble(finalObject.optString("latitude").toString()));
                    if (!finalObject.optString("longitude").toString().equals("null"))
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


            RoomAdapter adapter = new RoomAdapter(getActivity(), R.layout.room_feed, result);

            lvRoom.setAdapter(adapter);
            //close the loading dialog
            mProgressDialog.dismiss();
        }
    }
}
