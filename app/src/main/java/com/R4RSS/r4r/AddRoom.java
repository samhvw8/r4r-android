package com.R4RSS.r4r;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.R4RSS.helpers.DocumentHelper;
import com.R4RSS.helpers.IntentHelper;
import com.R4RSS.helpers.NotificationHelper;
import com.R4RSS.imgurmodel.ImageResponse;
import com.R4RSS.imgurmodel.Upload;
import com.R4RSS.services.UploadService;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.R4RSS.requests.AddRoomRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * Created by Son on 5/3/2016.
 */
public class AddRoom extends AppCompatActivity {

    private static final String MyPREFERENCES = "MyPrefs";
    private static final String Name = "name";
    private static final String Phone = "phone";
    private static final String Email = "email";
    private static final String Status = "status";
    private static final String CreatedDay = "created_at";
    private static final String Id = "id";
    SharedPreferences sharedPreferences;

    EditText etAddStreet;
    EditText etAddWard;
    EditText etAddDistrict;
    EditText etAddCity;
    EditText etAddPrice;
    EditText etAddArea;
    EditText etAddRoomDes;
    ImageView uploadImage;
    Button btnAdd;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private Upload upload; // Upload object containging image and meta data
    private File chosenFile; //chosen file from intent

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_room_layout);

        etAddStreet = (EditText) findViewById(R.id.etAddStreet);
        etAddWard = (EditText) findViewById(R.id.etAddWard);
        etAddDistrict = (EditText) findViewById(R.id.etAddDistrict);
        etAddCity = (EditText) findViewById(R.id.etAddCity);
        etAddPrice = (EditText) findViewById(R.id.etAddPrice);
        etAddArea = (EditText) findViewById(R.id.etAddArea);
        etAddRoomDes = (EditText) findViewById(R.id.etAddRoomDes);
        uploadImage = (ImageView) findViewById(R.id.imgUpload);
        btnAdd = (Button) findViewById(R.id.btnAdd);

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChooseImage();
//                clearInput();
            }
        });

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city;
                String ward;
                String district;
                String street;
                String description;
                String imgUrl;
                int price;
                double area;
                int id;

                city = etAddCity.getText().toString();
                ward = etAddWard.getText().toString();
                district = etAddDistrict.getText().toString();
                street = etAddStreet.getText().toString();
                description = etAddRoomDes.getText().toString();
                price = Integer.parseInt(etAddPrice.getText().toString());
                area = Double.parseDouble(etAddArea.getText().toString());
//                SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
//                String userId = sharedPreferences.getString(Id, null);
//                id = Integer.parseInt(userId);



                uploadImage(AddRoom.this, city, ward, district, street, price, area, description);

//                AddRoomRequest addRoomRequest = new AddRoomRequest(,imgUrl, responseListener);

            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
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
                .into(uploadImage);

    }


    public void onChooseImage() {
        IntentHelper.chooseFileIntent(this);
    }

    private void clearInput() {
        uploadImage.setImageResource(R.drawable.ic_menu_gallery);
    }


    public void uploadImage(Context context, String city,String ward,String district,String street,int price,double area,String description) {
    /*
      Create the @Upload object
     */
        if (chosenFile == null) return;
        createUpload(chosenFile);

    /*
      Start upload
     */
        new UploadService(this).Execute(context, city, ward, district, street, price, area, description, upload, new UiCallback());
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
}
