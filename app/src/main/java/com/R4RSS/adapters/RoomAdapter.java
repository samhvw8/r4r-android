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
import android.widget.TextView;

import com.R4RSS.GlobalValues;
import com.R4RSS.models.RoomModel;
import com.R4RSS.r4r.MainActivity;
import com.R4RSS.r4r.R;
import com.R4RSS.r4r.UserRooms;
import com.R4RSS.requests.DeleteRoomRequest;
import com.android.volley.RequestQueue;
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
        String tvStreet;
        String tvWard;
        String tvDistrict;
        String tvCity;
        String description;
        //TextView tvDescriptionRoom;
        TextView tvPrice;
        TextView Address;
        TextView tvTimestamp;

        ImageView delete, edit;

        imgRoom = (ImageView) convertView.findViewById(R.id.imgRoom);
        Address = (TextView) convertView.findViewById(R.id.tvAddress);
        tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
        tvTimestamp = (TextView) convertView.findViewById(R.id.tvTimestamp);
        delete = (ImageView) convertView.findViewById(R.id.delete_room);
        edit = (ImageView) convertView.findViewById(R.id.edit_room);
        //tvDescriptionRoom = (TextView) convertView.findViewById(R.id.tvDescriptionRoom);

        ImageLoader.getInstance().displayImage(roomAdapterList.get(position).getImage_album_url(), imgRoom); // Default options will be used

        tvStreet = roomAdapterList.get(position).getStreet();
        tvCity = roomAdapterList.get(position).getCity();
        tvDistrict = roomAdapterList.get(position).getDistrict();
        tvWard = roomAdapterList.get(position).getWard();
        tvTimestamp.setText(roomAdapterList.get(position).getCreated_day());
        tvPrice.setText("Price: " + Integer.toString(roomAdapterList.get(position).getPrice()) + "VND");
        Address.setText("Address:\n" + tvStreet + "-" + tvWard + "-" + tvDistrict + "-" + tvCity);
//        description = roomAdapterList.get(position).getDescription();
//        tvDescriptionRoom.setText(description);

        final int userId = roomAdapterList.get(position).getUserId();
        final int id = roomAdapterList.get(position).getId();



        if (GlobalValues.getId() == userId) {/////
            delete.setVisibility(View.VISIBLE);
            edit.setVisibility(View.VISIBLE);
        } else {
            delete.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.INVISIBLE);
        }

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

                                Intent intent = new Intent(GlobalValues.getContex(), UserRooms.class);

                                GlobalValues.logout();

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


//        edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonResponse = new JSONObject(response);
//                            boolean status = Boolean.parseBoolean(jsonResponse.opt("status").toString());
//
//                            if (status) {
//
//                                Intent intent = new Intent(GlobalValues.getContex(), UserRooms.class);
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
//            }
//        });

        return convertView;
    }
}
