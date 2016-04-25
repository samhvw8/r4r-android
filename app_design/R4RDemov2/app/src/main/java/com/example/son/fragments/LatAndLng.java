package com.example.son.fragments;

import com.example.son.models.RoomModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Son on 4/20/2016.
 */
public class LatAndLng {
    private  RoomModel roomModellat;
    private String urlAddress;
    private   String URL_GOOGLE_API_MAP = "http://maps.google.com/maps/api/geocode/json?address=";
    private double lat;
    private double lng;

    public LatAndLng(RoomModel roomModel,String urlAddress){
            this.roomModellat = roomModel;
            this.urlAddress = urlAddress;
    }

    public void getLatAndLng(){
        //connect to URL
        try {
            URL_GOOGLE_API_MAP = URL_GOOGLE_API_MAP + urlAddress;
            URL urlGoogle = new URL(URL_GOOGLE_API_MAP);
            HttpURLConnection httpURLGoogle = (HttpURLConnection) urlGoogle.openConnection();
            httpURLGoogle.connect();

            InputStream inputStreamGoogle = httpURLGoogle.getInputStream();
            BufferedReader bufferedReaderGoogle = new BufferedReader(new InputStreamReader(inputStreamGoogle));

            StringBuffer bufferGoogle = new StringBuffer();

            String lineGoogle = "";
            while ((lineGoogle = bufferedReaderGoogle.readLine()) != null) {
                bufferGoogle.append(lineGoogle);
            }
            //get JSON
            String finalJsonGoogle = bufferGoogle.toString();
            JSONObject parentObjectGoogle = new JSONObject(finalJsonGoogle);
            JSONArray result = parentObjectGoogle.getJSONArray("results");
            JSONObject finalResult = result.getJSONObject(0);
            JSONObject geometry = finalResult.getJSONObject("geometry");
            JSONObject location = geometry.getJSONObject("location");
            roomModellat.setLatitude(Double.parseDouble(location.optString("lat").toString()));
            roomModellat.setLongtitude(Double.parseDouble(location.optString("lng").toString()));
            this.lat = roomModellat.getLatitude();
            this.lng = roomModellat.getLongtitude();
        }
         catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public double getLat(){
        return lat;
    }

    public double getLng(){
        return lng;
    }

}
