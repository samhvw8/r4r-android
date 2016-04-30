package com.example.son.r4rdemov2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Son on 4/30/2016.
 */

// this class is only for check the result
public class LoginResult extends AppCompatActivity{

    private TextView tvNameUser;
    private TextView tvEmailUser;
    private TextView tvPhoneUser;
    private TextView tvDayUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.user_layout);

        tvNameUser = (TextView) findViewById(R.id.tvNameUser);
        tvEmailUser = (TextView) findViewById(R.id.tvEmailUser);
        tvPhoneUser = (TextView) findViewById(R.id.tvPhoneUser);
        tvDayUser = (TextView) findViewById(R.id.tvDayUser);

    }
}
