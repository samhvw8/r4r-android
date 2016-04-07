package com.example.son.navigationfrag;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Son on 3/30/2016.
 */
public class FragmentTwo extends Fragment implements View.OnClickListener{
    private Button btnLogin;
    private Button btnRegister;
    public FragmentTwo() {
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

        final View rootView = inflater.inflate(R.layout.fragment_two, container, false);
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
                startActivity(new Intent(getActivity().getApplicationContext(), Pop.class));
                break;
            case R.id.btnRegister:
                startActivity(new Intent(getActivity().getApplicationContext(), PopRegister.class));
                break;
        }
    }
}
