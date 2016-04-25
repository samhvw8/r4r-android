package com.example.son.r4rdemov2;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.example.son.r4rdemov2.R;

/**
 * Created by Son on 4/21/2016.
 */
public class PopRegister extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerpopup);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));
    }
}

