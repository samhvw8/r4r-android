package com.R4RSS.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.R4RSS.GlobalValues;
import com.R4RSS.models.RoomModel;
import com.R4RSS.r4r.EditRoom;
import com.R4RSS.r4r.MainActivity;
import com.R4RSS.r4r.R;
import com.R4RSS.r4r.RoomDetail;
import com.R4RSS.r4r.UserRooms;
import com.R4RSS.requests.DeleteRoomRequest;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Son on 4/14/2016.
 */
public class RoomAdapter extends ArrayAdapter {

    private List<RoomModel> roomAdapterList;
    private int resource;
    private static final String USER_INFO_URL = "http://52.36.12.106/api/v1/user/";

    public RoomAdapter(Context context, int resource, List<RoomModel> objects) {
        super(context, resource, objects);
        roomAdapterList = objects;
        this.resource = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            convertView = vi.inflate(R.layout.room_feed, null);
        }

        ImageView imgRoom;
        final String tvStreet;
        final String tvWard;
        final String tvDistrict;
        final String tvCity;
        final String description;
        final String price;
        final String area;
        //TextView tvDescriptionRoom;
        final TextView tvPrice;
        TextView Address;
        TextView tvTimestamp;
        LinearLayout layout;
        ImageView delete, edit;

        imgRoom = (ImageView) convertView.findViewById(R.id.imgRoom);
        Address = (TextView) convertView.findViewById(R.id.tvAddress);
        tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
        tvTimestamp = (TextView) convertView.findViewById(R.id.tvTimestamp);
        delete = (ImageView) convertView.findViewById(R.id.delete_room);
        edit = (ImageView) convertView.findViewById(R.id.edit_room);
        layout = (LinearLayout) convertView.findViewById(R.id.layout);
        //tvDescriptionRoom = (TextView) convertView.findViewById(R.id.tvDescriptionRoom);

        ImageLoader.getInstance().displayImage(roomAdapterList.get(position).getImage_album_url(), imgRoom); // Default options will be used

        tvStreet = roomAdapterList.get(position).getStreet();
        tvCity = roomAdapterList.get(position).getCity();
        tvDistrict = roomAdapterList.get(position).getDistrict();
        tvWard = roomAdapterList.get(position).getWard();
        description = roomAdapterList.get(position).getDescription();
        price = Integer.toString(roomAdapterList.get(position).getPrice());
        area = Double.toString(roomAdapterList.get(position).getArea());

        tvTimestamp.setText(roomAdapterList.get(position).getCreated_day());
        tvPrice.setText("Price: " + Integer.toString(roomAdapterList.get(position).getPrice()) + "VND");
        Address.setText("Address:\n" + tvStreet + "-" + tvWard + "-" + tvDistrict + "-" + tvCity);
//        description = roomAdapterList.get(position).getDescription();
//        tvDescriptionRoom.setText(description);

        final int userId = roomAdapterList.get(position).getUserId();
        final int id = roomAdapterList.get(position).getId();

        final int p = position;

        if (GlobalValues.getId() == userId) {/////
            delete.setVisibility(View.VISIBLE);
            edit.setVisibility(View.VISIBLE);
        } else {
            delete.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.INVISIBLE);
        }
        layout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //get dia chi vao activity RoomDetail
                String street = roomAdapterList.get(p).getStreet();
                String ward = roomAdapterList.get(p).getWard();
                String district = roomAdapterList.get(p).getDistrict();
                String city = roomAdapterList.get(p).getCity();
                final String address = street + "-" + ward + "-" + district + "-" + city + "\n";
                final String price = Integer.toString(roomAdapterList.get(p).getPrice()) + "VND\n";
                final String area = Double.toString(roomAdapterList.get(p).getArea()) + "m2\n";
                final String description = roomAdapterList.get(p).getDescription() + "\n";
                final String userId = Integer.toString(roomAdapterList.get(p).getUserId());
                //get toa do vao activity RoomDetail
//                String lat = Double.toString(room.getLatitude());
//                String lng = Double.toString(room.getLongtitude());
                final double lat = roomAdapterList.get(p).getLatitude();
                final double lng = roomAdapterList.get(p).getLongtitude();


                final RequestQueue queue = Volley.newRequestQueue(getContext());
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

                                        Intent intent = new Intent(getContext(), RoomDetail.class);
                                        intent.putExtra("price", price);
                                        intent.putExtra("address", address);
                                        intent.putExtra("area", area);
                                        intent.putExtra("description", description);
                                        intent.putExtra("lat", lat);
                                        intent.putExtra("lng", lng);
                                        intent.putExtra("userId", userId);
                                        intent.putExtra("contact", contact);

                                        getContext().startActivity(intent);

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


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean status = Boolean.parseBoolean(jsonResponse.opt("status").toString());

                            if (status) {

                                Intent intent = new Intent(getContext(), getContext().getClass());

                                getContext().startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setMessage("Delete Room Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
//
                DeleteRoomRequest request = new DeleteRoomRequest(id, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getContext());
                queue.add(request);
            }
        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), EditRoom.class);
                String idUser = Integer.toString(id);
                intent.putExtra("id",idUser);
                intent.putExtra("street",tvStreet);
                intent.putExtra("district",tvDistrict);
                intent.putExtra("ward",tvWard);
                intent.putExtra("city",tvCity);
                intent.putExtra("price",price);
                intent.putExtra("area",area);
                intent.putExtra("description",description);

                Log.d("idAdapter",id+"");
                getContext().startActivity(intent);
//                com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonResponse = new JSONObject(response);
//                            boolean status = Boolean.parseBoolean(jsonResponse.opt("status").toString());
//
//                            if (status) {
//
//                                Intent intent = new Intent(GlobalValues.getContext(), UserRooms.class);
//
//                                GlobalValues.logout();
//
//                                getContext().startActivity(intent);
//                            } else {
//                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                                builder.setMessage("Delete Room Failed")
//                                        .setNegativeButton("Retry", null)
//                                        .create()
//                                        .show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                };
////
//                DeleteRoomRequest request = new DeleteRoomRequest(userId, responseListener);
//                RequestQueue queue = Volley.newRequestQueue(getContext());
//                queue.add(request);
            }
        });



        return convertView;
    }
}
