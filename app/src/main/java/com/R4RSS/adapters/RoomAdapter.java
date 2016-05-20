package com.R4RSS.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.R4RSS.models.RoomModel;
import com.R4RSS.r4r.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Son on 4/14/2016.
 */
public class RoomAdapter extends ArrayAdapter {

    private List<RoomModel> roomAdapterList;
    private int resource;

    public RoomAdapter(Context context, int resource, List<RoomModel> objects) {
        super(context, resource, objects);
        roomAdapterList = objects;
        this.resource = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            convertView = vi.inflate(R.layout.room_feed, null);
        }

        ImageView imgRoom;
        String tvStreet;
        String tvWard;
        String tvDistrict;
        String tvCity;
        String description;
        //TextView tvDescriptionRoom;
        TextView tvPrice;
        TextView Address;
        TextView tvTimestamp;

        imgRoom = (ImageView) convertView.findViewById(R.id.imgRoom);
        Address = (TextView) convertView.findViewById(R.id.tvAddress);
        tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
       tvTimestamp = (TextView) convertView.findViewById(R.id.tvTimestamp);
        //tvDescriptionRoom = (TextView) convertView.findViewById(R.id.tvDescriptionRoom);

        ImageLoader.getInstance().displayImage(roomAdapterList.get(position).getImage_album_url(), imgRoom); // Default options will be used

        tvStreet = roomAdapterList.get(position).getStreet();
        tvCity = roomAdapterList.get(position).getCity();
        tvDistrict = roomAdapterList.get(position).getDistrict();
        tvWard = roomAdapterList.get(position).getWard();
        tvTimestamp.setText( roomAdapterList.get(position).getCreated_day());
        tvPrice.setText("Price: " + Integer.toString(roomAdapterList.get(position).getPrice()) + "VND");
        Address.setText("Address:\n" + tvStreet + "-" + tvWard + "-" + tvDistrict + "-" + tvCity);
//        description = roomAdapterList.get(position).getDescription();
//        tvDescriptionRoom.setText(description);
        return convertView;
    }
}
