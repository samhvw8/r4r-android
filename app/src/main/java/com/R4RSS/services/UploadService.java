package com.R4RSS.services;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;

import com.R4RSS.GlobalValues;
import com.R4RSS.helpers.NotificationHelper;
import com.R4RSS.imgurmodel.ImageResponse;
import com.R4RSS.imgurmodel.ImgurAPI;
import com.R4RSS.imgurmodel.Upload;
import com.R4RSS.r4r.MainActivity;
import com.R4RSS.requests.AddRoomRequest;
import com.R4RSS.utils.NetworkUtils;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

/**
 * Created by AKiniyalocts on 1/12/15.
 * <p>
 * Our upload service. This creates our restadapter, uploads our image, and notifies us of the response.
 */
public class UploadService {
    public final static String TAG = UploadService.class.getSimpleName();

    private WeakReference<Context> mContext;

    public UploadService(Context context) {
        this.mContext = new WeakReference<>(context);
    }

    public void Execute(final Context context,final String city,final String ward,final String district,final String street,final int price,final double area,final String description, Upload upload, Callback<ImageResponse> callback) {
        final Callback<ImageResponse> cb = callback;

        if (!NetworkUtils.isConnected(mContext.get())) {
            //Callback will be called, so we prevent a unnecessary notification
            cb.failure(null);
            return;
        }

        final NotificationHelper notificationHelper = new NotificationHelper(mContext.get());
        notificationHelper.createUploadingNotification();

        RestAdapter restAdapter = buildRestAdapter();

        restAdapter.create(ImgurAPI.class).postImage(
                GlobalValues.getClientAuth(),
                upload.title,
                upload.description,
                upload.albumId,
                null,
                new TypedFile("image/*", upload.image),
                new Callback<ImageResponse>() {
                    @Override
                    public void success(ImageResponse imageResponse, Response response) {
                        if (cb != null) cb.success(imageResponse, response);
                        if (response == null) {
                            /*
                             Notify image was NOT uploaded successfully
                            */
                            notificationHelper.createFailedUploadNotification();
                            return;
                        }
                        /*
                        Notify image was uploaded successfully
                        */
                        if (imageResponse.success) {

                            com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        String result = response;
                                        JSONObject jsonResponse = new JSONObject(response);
                                        boolean status = Boolean.parseBoolean(jsonResponse.opt("status").toString());

                                        if (status) {
                                            Intent intent = new Intent(context, MainActivity.class);

                                            context.startActivity(intent);
                                        } else {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
//
                            AddRoomRequest addRoomRequest = new AddRoomRequest(city, ward, district, street, price, area, description, imageResponse.data.link, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(context);
                            queue.add(addRoomRequest);

                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (cb != null) cb.failure(error);
                        notificationHelper.createFailedUploadNotification();
                    }
                });
    }

    private RestAdapter buildRestAdapter() {
        RestAdapter imgurAdapter = new RestAdapter.Builder()
                .setEndpoint(ImgurAPI.server)
                .build();

        /*
        Set rest adapter logging if we're already logging
        */
        if (GlobalValues.LOGGING)
            imgurAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
        return imgurAdapter;
    }
}
