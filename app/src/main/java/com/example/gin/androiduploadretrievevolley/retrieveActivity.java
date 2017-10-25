package com.example.gin.androiduploadretrievevolley;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.gin.androiduploadretrievevolley.ADAPTERS.retrieveImageAdapter;
import com.example.gin.androiduploadretrievevolley.ADAPTERS.retrieveTextAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class retrieveActivity extends AppCompatActivity {

    private Button btnRetrieveText, btnRetrieveImage;

    private ListView listViewRetrieveText;
    private ArrayList<String> name;
    private ArrayList<String> age;

    private GridView listViewRetrieveImage;
    private ArrayList<String> image;

    private retrieveTextAdapter retrieveTextAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve);

        btnRetrieveText = (Button) findViewById(R.id.btnRetrieveText);
        btnRetrieveImage = (Button) findViewById(R.id.btnRetrieveImage);

        listViewRetrieveText = (ListView) findViewById(R.id.listViewRetrieveText);
        listViewRetrieveImage = (GridView) findViewById(R.id.listViewRetrieveImage);

        name = new ArrayList<>();
        age = new ArrayList<>();
        image = new ArrayList<>();

        retrieveTextAdapter = new retrieveTextAdapter(retrieveActivity.this, name, age);
        listViewRetrieveText.setAdapter(retrieveTextAdapter);

        btnRetrieveText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveText();

                name.clear();
                age.clear();

                listViewRetrieveText.setVisibility(View.VISIBLE);
                listViewRetrieveImage.setVisibility(View.GONE);
            }
        });
        btnRetrieveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveImage();

                image.clear();

                listViewRetrieveText.setVisibility(View.GONE);
                listViewRetrieveImage.setVisibility(View.VISIBLE);
            }
        });
    }


    public void retrieveText(){
        final ProgressDialog loading = ProgressDialog.show(this, "Retrieve Text", "Retrieving data...", false, false);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Constants.URL_RETRIEVETEXT,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        showLIST(response);
                                loading.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof NetworkError) {
                            loading.dismiss();
                            Toast.makeText(retrieveActivity.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof TimeoutError) {
                            loading.dismiss();
                            Toast.makeText(retrieveActivity.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof NoConnectionError) {
                            loading.dismiss();
                            Toast.makeText(retrieveActivity.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
    private void showLIST(JSONArray jsonArray) {
        JSONObject obj = null;

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                obj = jsonArray.getJSONObject(i);
                name.add(obj.getString(Constants.RETRIEVENAME));
                age.add(obj.getString(Constants.RETRIEVEAGE));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        retrieveTextAdapter adapter = new retrieveTextAdapter(this,name, age);
        listViewRetrieveText.setAdapter(adapter);
    }


    public void retrieveImage(){
            final ProgressDialog loading = ProgressDialog.show(this, "Retrieve Image", "Retrieving data...", false, false);

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Constants.URL_RETRIEVEIMAGE,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            showGrid(response);
                            loading.dismiss();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error instanceof NetworkError) {
                                loading.dismiss();
                                Toast.makeText(retrieveActivity.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof TimeoutError) {
                                loading.dismiss();
                                Toast.makeText(retrieveActivity.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof NoConnectionError) {
                                loading.dismiss();
                                Toast.makeText(retrieveActivity.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(jsonArrayRequest);
        }
        private void showGrid(JSONArray jsonArray) {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = null;
                try {
                    obj = jsonArray.getJSONObject(i);
                    image.add(obj.getString(Constants.RETRIEVEIMAGE));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            retrieveImageAdapter adapter = new retrieveImageAdapter(this, image);
            listViewRetrieveImage.setAdapter(adapter);
        }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
