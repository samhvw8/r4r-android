package com.example.son.navigationfrag.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.son.navigationfrag.R;
import com.example.son.navigationfrag.model.RoomModel;

import java.util.List;

/**
 * Created by Son on 4/7/2016.
 */
public class RoomAdapter extends ArrayAdapter {

    private List<RoomModel> feedModelList;
    private int resource;
    //private LayoutInflater inflater;
    public RoomAdapter(Context context,int resource,List<RoomModel> objects){
        super(context,resource,objects);
        feedModelList = objects;
        this.resource = resource;
        //inflater = (LayoutInflater) getSystemService();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            convertView = vi.inflate(R.layout.roomfeed,null);
        }

        ImageView imgFeed;
        TextView tvName;
        TextView tvStatus;

        imgFeed = (ImageView) convertView.findViewById(R.id.imgFeed);
        tvName = (TextView) convertView.findViewById(R.id.tvAddress);
        tvStatus = (TextView) convertView.findViewById(R.id.tvPrice);

        tvName.setText(feedModelList.get(position).getName());
        tvStatus.setText(feedModelList.get(position).getStatus());

        return convertView;
    }
}
