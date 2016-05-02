package com.example.son.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.son.fragments.HomeFragment;
import com.example.son.fragments.SearchFragment;
import com.example.son.fragments.UserFragment;
import com.example.son.r4rdemov2.MainActivity;
import com.example.son.r4rdemov2.R;

/**
 * Created by Son on 4/30/2016.
 */

// this class is only for check the result
public class LoginResult extends Fragment {

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Name = "name";
    public static final String Phone = "phone";
    public static final String Email = "email";
    public static final String Status = "status";
    public static final String CreatedDay = "created_at";

    private TextView tvNameUser;
    private TextView tvEmailUser;
    private TextView tvPhoneUser;
    private TextView tvDayUser;
    private Button btnLogout;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_layout,container,false);
        tvNameUser = (TextView) rootView.findViewById(R.id.tvNameUser);
        tvEmailUser = (TextView) rootView.findViewById(R.id.tvEmailUser);
        tvPhoneUser = (TextView) rootView.findViewById(R.id.tvPhoneUser);
        tvDayUser = (TextView) rootView.findViewById(R.id.tvDayUser);
        btnLogout = (Button) rootView.findViewById(R.id.btnLogout);


        sharedPreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String name = sharedPreferences.getString(Name,null);
        String phone = sharedPreferences.getString(Phone,null);
        String email = sharedPreferences.getString(Email,null);
        String day = sharedPreferences.getString(CreatedDay,null);

        tvNameUser.setText(name);
        tvEmailUser.setText(email);
        tvPhoneUser.setText(phone);
        tvDayUser.setText(day);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences myPrefs = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = myPrefs.edit();
                editor.clear();
                editor.putString(Status, "false");
                editor.commit();

                Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
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
