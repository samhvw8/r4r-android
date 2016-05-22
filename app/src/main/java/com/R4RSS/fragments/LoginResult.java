package com.R4RSS.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.R4RSS.GlobalValues;
import com.R4RSS.r4r.AddRoom;
import com.R4RSS.r4r.MainActivity;
import com.R4RSS.r4r.R;
import com.R4RSS.r4r.UserRooms;
import com.R4RSS.requests.DeleteUserRequest;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;



/**
 * Created by Son on 4/30/2016.
 */

// this class is only for check the result
public class LoginResult extends Fragment {


    public TextView tvNameUser;

    public TextView tvEmailUser;

    public TextView tvPhoneUser;

    public ImageButton imageAction;

    public Button btnAddRoom;

    public Button btnListAddRoom;

    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_layout, container, false);
        tvNameUser = (TextView) rootView.findViewById(R.id.tvNameUser);
        tvEmailUser = (TextView) rootView.findViewById(R.id.tvEmailUser);
        tvPhoneUser = (TextView) rootView.findViewById(R.id.tvPhoneUser);
        //tvDayUser = (TextView) rootView.findViewById(R.id.tvDayUser);
        imageAction = (ImageButton) rootView.findViewById(R.id.imageAction);

        btnAddRoom = (Button) rootView.findViewById(R.id.btnAddRoom);
        btnListAddRoom = (Button) rootView.findViewById(R.id.btnListAddRoom);

        tvNameUser.setText(GlobalValues.getName());
        tvEmailUser.setText(GlobalValues.getEmail());
        tvPhoneUser.setText(GlobalValues.getPhone());
        context = this.getActivity();
        imageAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(getActivity().getApplicationContext(), imageAction);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.pop_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        int id = item.getItemId();
                        if (id == R.id.action_logout) {
                            GlobalValues.logout();

                            // Go Back MainActivity
                            Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }

                        if (id == R.id.action_delete_account) {

                            com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonResponse = new JSONObject(response);
                                        boolean status = Boolean.parseBoolean(jsonResponse.opt("status").toString());

                                        if (status) {

                                            Intent intent = new Intent(GlobalValues.getContex(), MainActivity.class);

                                            GlobalValues.logout();

                                            context.startActivity(intent);
                                        } else {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                            builder.setMessage("Delete Account Failed")
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
                            DeleteUserRequest addRoomRequest = new DeleteUserRequest(GlobalValues.getId(), responseListener);
                            RequestQueue queue = Volley.newRequestQueue(context);
                            queue.add(addRoomRequest);

                        }
                        return true;
                    }
                });

                popup.show();

            }
        });

        btnAddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), AddRoom.class);
                startActivity(intent);
            }
        });

        btnListAddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), UserRooms.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

}
