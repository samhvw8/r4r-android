package com.R4RSS.r4r;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.R4RSS.helpers.DocumentHelper;
import com.R4RSS.helpers.IntentHelper;
import com.R4RSS.imgurmodel.ImageResponse;
import com.R4RSS.imgurmodel.Upload;
import com.R4RSS.requests.AddRoomRequest;
import com.R4RSS.requests.EditRoomRequest;
import com.R4RSS.services.UploadService;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * Created by sinzi on 5/22/2016.
 */
public class EditRoom extends AppCompatActivity {

    EditText etStreet;
    EditText etDistrict;
    EditText etWard;
    EditText etCity;
    EditText etPrice;
    EditText etArea;
    EditText etDescription;
    Button btnEdit;
    ImageView imgEditUpload;

    int id;


    private GoogleApiClient client;
    private Upload upload; // Upload object containging image and meta data
    private File chosenFile; //chosen file from intent

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_room_layout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent i = getIntent();
        String idUser = i.getStringExtra("id");
        Log.d("idEdit:", idUser);
        id = Integer.parseInt(idUser);
        Log.d("idEdit:", id + "");

        String street = i.getStringExtra("street");
        String district = i.getStringExtra("district");
        String ward = i.getStringExtra("ward");
        String city = i.getStringExtra("city");

        String price = i.getStringExtra("price");
        String area = i.getStringExtra("area");
        String description = i.getStringExtra("description");

        etStreet = (EditText) findViewById(R.id.etEditStreet);
        etDistrict = (EditText) findViewById(R.id.etEditDistrict);
        etWard = (EditText) findViewById(R.id.etEditWard);
        etCity = (EditText) findViewById(R.id.etEditCity);
        etPrice = (EditText) findViewById(R.id.etEditPrice);
        etArea = (EditText) findViewById(R.id.etEditArea);
        etDescription = (EditText) findViewById(R.id.etEditRoomDes);
        imgEditUpload = (ImageView) findViewById(R.id.imgEditUpload);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        chosenFile = null;
        etStreet.setText(street);
        etDistrict.setText(district);
        etWard.setText(ward);
        etCity.setText(city);
        etPrice.setText(price);
        etArea.setText(area);
        etDescription.setText(description);

        imgEditUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChooseImage();
//                clearInput();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (id != 0) {
                    String city;
                    String ward;
                    String district;
                    String street;
                    String description;
                    String imgUrl;
                    int price;
                    double area;


                    city = etCity.getText().toString();
                    ward = etWard.getText().toString();
                    district = etDistrict.getText().toString();
                    street = etStreet.getText().toString();
                    description = etDescription.getText().toString();
                    price = Integer.parseInt(etPrice.getText().toString());
                    area = Double.parseDouble(etArea.getText().toString());


                    uploadImage(id, EditRoom.this, city, ward, district, street, price, area, description);


                    if (chosenFile == null) {
                        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean status = Boolean.parseBoolean(jsonResponse.opt("status").toString());

                                    if (status) {

                                        Intent intent = new Intent(EditRoom.this, MainActivity.class);

                                        EditRoom.this.startActivity(intent);
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(EditRoom.this);
                                        builder.setMessage("Search Failed")
                                                .setNegativeButton("Retry", null)
                                                .create()
                                                .show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        EditRoomRequest editRoomRequest = new EditRoomRequest(id, city, ward, district, street, price, area, description, null, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(EditRoom.this);
                        queue.add(editRoomRequest);
                    }
                }
            }
        });

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri returnUri;

        if (requestCode != IntentHelper.FILE_PICK) {
            return;
        }

        if (resultCode != RESULT_OK) {
            return;
        }

        returnUri = data.getData();
        String filePath = DocumentHelper.getPath(this, returnUri);
        //Safety check to prevent null pointer exception
        if (filePath == null || filePath.isEmpty()) return;
        chosenFile = new File(filePath);

                /*
                    Picasso is a wonderful image loading tool from square inc.
                    https://github.com/square/picasso
                 */
        Picasso.with(getBaseContext())
                .load(chosenFile)
                .placeholder(R.drawable.ic_menu_gallery)
                .fit()
                .into(imgEditUpload);

    }


    public void onChooseImage() {
        IntentHelper.chooseFileIntent(this);
    }

    private void clearInput() {
        imgEditUpload.setImageResource(R.drawable.ic_menu_gallery);
    }


    public void uploadImage(int id, Context context, String city, String ward, String district, String street, int price, double area, String description) {
    /*
      Create the @Upload object
     */
        if (chosenFile == null) return;
        createUpload(chosenFile);

    /*
      Start upload
     */
        new UploadService(this).Execute(id, context, city, ward, district, street, price, area, description, upload, new UiCallback());
    }

    private void createUpload(File image) {
        upload = new Upload();

        upload.image = image;
    }

    private class UiCallback implements Callback<ImageResponse> {

        @Override
        public void success(ImageResponse imageResponse, retrofit.client.Response response) {
            clearInput();
        }

        @Override
        public void failure(RetrofitError error) {
            //Assume we have no connection, since error is null
            if (error == null) {
                Snackbar.make(findViewById(R.id.rootView), "No internet connection", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "AddRoom Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.R4RSS.r4r/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "AddRoom Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.R4RSS.r4r/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                onBackPressed();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
