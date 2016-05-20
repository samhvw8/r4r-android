package com.R4RSS.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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
import com.R4RSS.r4r.R;
import butterknife.Bind;

/**
 * Created by Son on 4/30/2016.
 */

// this class is only for check the result
public class LoginResult extends Fragment {


    public TextView tvNameUser;

    public TextView tvEmailUser;

    public TextView tvPhoneUser;

    public ImageButton btnLogout;

    public Button btnAddRoom;

    public Button btnListAddRoom;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_layout, container, false);
        tvNameUser = (TextView) rootView.findViewById(R.id.tvNameUser);
        tvEmailUser = (TextView) rootView.findViewById(R.id.tvEmailUser);
        tvPhoneUser = (TextView) rootView.findViewById(R.id.tvPhoneUser);
        //tvDayUser = (TextView) rootView.findViewById(R.id.tvDayUser);
        btnLogout = (ImageButton) rootView.findViewById(R.id.btnLogout);
        btnAddRoom = (Button) rootView.findViewById(R.id.btnAddRoom);
        btnListAddRoom = (Button) rootView.findViewById(R.id.btnListAddRoom);

        tvNameUser.setText(GlobalValues.getName());
        tvEmailUser.setText(GlobalValues.getEmail());
        tvPhoneUser.setText(GlobalValues.getPhone());

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(getActivity().getApplicationContext(), btnLogout);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.pop_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.action_logout) {
                            GlobalValues.logout();
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

        return rootView;
    }

}
