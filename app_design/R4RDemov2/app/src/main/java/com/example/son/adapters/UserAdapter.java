package com.example.son.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.son.models.RoomModel;
import com.example.son.models.UserModel;
import com.example.son.r4rdemov2.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Son on 4/30/2016.
 */
public class UserAdapter extends ArrayAdapter {

    private List<UserModel> userAdapter;
    private int resource;

    public UserAdapter(Context context, int resource, List<UserModel> objects) {
        super(context, resource, objects);
        userAdapter = objects;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            convertView = vi.inflate(R.layout.user_layout,null);
        }

        //ImageView imgRoom;
        String name;
        String email;
        String phone;
        String createdDay;
        TextView tvNameUser;
        TextView tvPhoneUser;
        TextView tvEmailUser;
        TextView tvDayUser;

        //imgRoom = (ImageView) convertView.findViewById(R.id.imgRoom);
        tvNameUser = (TextView) convertView.findViewById(R.id.tvNameUser);
        tvPhoneUser = (TextView) convertView.findViewById(R.id.tvPhoneUser);
        tvEmailUser = (TextView) convertView.findViewById(R.id.tvEmailUser);
        //tvDayUser = (TextView) convertView.findViewById(R.id.tvDayUser);

        //ImageLoader.getInstance().displayImage(roomAdapterList.get(position).getImage_album_url(), imgRoom);
        // Default options will be used

        name = userAdapter.get(position).getName();
        email = userAdapter.get(position).getEmail();
        phone = userAdapter.get(position).getPhone();
        //tvDayUser.setText("Create Day: "+ userAdapter.get(position).getCreatedDay());

        tvNameUser.setText(name);
        tvPhoneUser.setText(phone);
        tvEmailUser.setText(email);

        return convertView;

    }
}
