package com.example.son.navigationfrag;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.son.navigationfrag.adapter.RoomAdapter;
import com.example.son.navigationfrag.model.RoomModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Son on 3/30/2016.
 */
public class FragmentOne extends Fragment {

    public FragmentOne() {
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
        View rootView = inflater.inflate(R.layout.fragment_one, container, false);
        ListView lvRoom = (ListView) rootView.findViewById(R.id.lvRoom);
//        new JSONTask().execute("http://api.androidhive.info/feed/feed.json");
        //btnFeed = (Button) rootView.findViewById(R.id.btnFeed);
//
//        JSONTask newJson = new JSONTask();
//        newJson.execute("http://api.androidhive.info/feed/feed.json");
//        btnFeed.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new JSONTask().execute("http://api.androidhive.info/feed/feed.json");
//            }
//        });

        return rootView;
    }

//    public class JSONTask extends AsyncTask<String, String, List<RoomModel>> {
//        private ListView lvRoom;
//        @Override
//        protected List<RoomModel> doInBackground(String... params) {
//            HttpURLConnection connection = null;
//            BufferedReader reader = null;
//            try {
//                URL url = new URL(params[0]);
//                connection = (HttpURLConnection) url.openConnection();
//                connection.connect();
//
//                InputStream stream = connection.getInputStream();
//
//                reader = new BufferedReader(new InputStreamReader(stream));
//
//                StringBuffer buffer = new StringBuffer();
//
//                String line = "";
//                while ((line = reader.readLine()) != null) {
//                    buffer.append(line);
//                }
//
//                String finalJson = buffer.toString();
//                JSONObject parentObject = new JSONObject(finalJson);
//                JSONArray parentArray = parentObject.getJSONArray("feed");
//
//                //Load data to models
//                List<RoomModel> feedModelList = new ArrayList<>();
//                for (int i = 0; i < parentArray.length(); i++) {
//                    JSONObject finalObject = parentArray.getJSONObject(i);
//                    RoomModel feedModel = new RoomModel();
//                    feedModel.setId(finalObject.getInt("id"));
//                    feedModel.setName(finalObject.getString("name"));
//                    feedModel.setProfilePic(finalObject.getString("profilePic"));
//                    feedModel.setStatus(finalObject.getString("status"));
//                    feedModel.setUrl(finalObject.getString("url"));
//                    // adding the final object in the list
//                    feedModelList.add(feedModel);
//                }
//                return feedModelList;
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            } finally {
//                if (connection != null) {
//                    connection.disconnect();
//                }
//                try {
//                    if (reader != null) {
//                        reader.close();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(List<RoomModel> result){
//            super.onPostExecute(result);
//            RoomAdapter adapter = new RoomAdapter(getActivity().getApplicationContext(),R.layout.roomfeed,result);
////            if(adapter == null){
////                Log.d(TAG, "onPostExecute: FAIL");
////            }
//            lvRoom.setAdapter(adapter);
//            //TO DO need to set data on the list
//        }
//    }
//
}
