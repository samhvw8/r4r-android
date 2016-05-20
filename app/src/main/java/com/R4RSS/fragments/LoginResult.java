package com.R4RSS.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.R4RSS.r4r.AddRoom;
import com.R4RSS.r4r.MainActivity;
import com.R4RSS.r4r.R;

/**
 * Created by Son on 4/30/2016.
 */

// this class is only for check the result
public class LoginResult extends Fragment {

    private static final String MyPREFERENCES = "MyPrefs";
    private static final String Name = "name";
    private static final String Phone = "phone";
    private static final String Email = "email";
    private static final String Status = "status";
    private static final String CreatedDay = "created_at";

    private TextView tvNameUser;
    private TextView tvEmailUser;
    private TextView tvPhoneUser;
    private TextView tvDayUser;
    private ImageButton btnLogout;
    private Button btnAddRoom;
    private Button btnListAddRoom;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_layout,container,false);
        tvNameUser = (TextView) rootView.findViewById(R.id.tvNameUser);
        tvEmailUser = (TextView) rootView.findViewById(R.id.tvEmailUser);
        tvPhoneUser = (TextView) rootView.findViewById(R.id.tvPhoneUser);
        //tvDayUser = (TextView) rootView.findViewById(R.id.tvDayUser);
        btnLogout = (ImageButton) rootView.findViewById(R.id.btnLogout);
        btnAddRoom = (Button)rootView.findViewById(R.id.btnAddRoom);
        btnListAddRoom = (Button) rootView.findViewById(R.id.btnListAddRoom);


        sharedPreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String name = sharedPreferences.getString(Name,null);
        String phone = sharedPreferences.getString(Phone,null);
        String email = sharedPreferences.getString(Email,null);
        String day = sharedPreferences.getString(CreatedDay,null);

        tvNameUser.setText(name);
        tvEmailUser.setText(email);
        tvPhoneUser.setText(phone);
       // tvDayUser.setText(day);

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
                            SharedPreferences myPrefs = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

                            SharedPreferences.Editor editor = myPrefs.edit();
                            editor.clear();
                            editor.putString(Status, "false");
                            editor.commit();

                            Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                            startActivity(intent);
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



//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                onBackPressed();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }


}
