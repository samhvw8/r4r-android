package com.example.son.r4rdemov2;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.example.son.r4rdemov2.R;

/**
 * Created by Son on 4/21/2016.
 */
public class PopLogin extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpopup);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.6),(int) (height*.4));
    }
}
