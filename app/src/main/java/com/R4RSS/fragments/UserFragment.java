package com.R4RSS.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.R4RSS.r4r.PopLogin;
import com.R4RSS.r4r.PopRegister;
import com.R4RSS.r4r.R;

/**
 * Created by Son on 4/21/2016.
 */
public class UserFragment extends Fragment implements View.OnClickListener{
    private Button btnLogin;
    private Button btnRegister;
    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //button login

        final View rootView = inflater.inflate(R.layout.fragment_user, container, false);
        btnLogin = (Button) rootView.findViewById(R.id.btnLogin);
        btnRegister = (Button) rootView.findViewById(R.id.btnRegister);
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                startActivity(new Intent(getActivity().getApplicationContext(), PopLogin.class));
                break;
            case R.id.btnRegister:
                startActivity(new Intent(getActivity().getApplicationContext(), PopRegister.class));
                break;
        }
    }
}
