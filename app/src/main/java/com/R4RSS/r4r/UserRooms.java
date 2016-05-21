package com.R4RSS.r4r;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by sinzi on 5/22/2016.
 */
public class UserRooms extends AppCompatActivity {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpopup);

        //create pop up window
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .6), (int) (height * .4));

        //consider input vaule to login

        final EditText etUserName = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button btnPopLogin = (Button) findViewById(R.id.btnPopLogin);

        btnPopLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = etUserName.getText().toString();
                final String password = etPassword.getText().toString();
                final String base64Source = email + ":" + password;
                final String base64String = "Basic " + Base64.encodeToString(base64Source.getBytes(), Base64.DEFAULT);


                RequestQueue queue = Volley.newRequestQueue(PopLogin.this);
                JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, GlobalValues.LOGIN_REQUEST_URL, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // display response
                                Log.d("Response", response.toString());
                                //save info into share preference to later use
                                boolean status = Boolean.parseBoolean(response.opt("status").toString());
                                if (status) {

                                    String day;
                                    int id;
                                    try {
                                        JSONObject data = response.getJSONObject("data");
                                        JSONObject user = data.getJSONObject("user");

                                        String name = user.getString("name");
                                        String phone = user.getString("phone");
                                        day = user.getString("created_at");
                                        id = Integer.parseInt(user.optString("id").toString());


                                        String statusPass = Boolean.toString(status);

                                        GlobalValues.login(base64String, name, phone, email, statusPass, day, id);

                                        Intent intent = new Intent(PopLogin.this, MainActivity.class);
                                        startActivity(intent);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Error.Response", error.toString());
                            }
                        }
                ) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Authorization", base64String);
                        return headers;
                    }
                };

                // add it to the RequestQueue
                queue.add(getRequest);
            }

        });


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
