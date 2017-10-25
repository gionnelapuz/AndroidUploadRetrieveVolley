package com.example.gin.androiduploadretrievevolley;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;


public class uploadActivity extends AppCompatActivity {

    private EditText editName;
    private EditText editAge;
    private Button btnUploadText, btnUploadImage, btnChooseImage;

    private ImageView imageUpload;
    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_acitivity);

        editName = (EditText) findViewById(R.id.editName);
        editAge = (EditText) findViewById(R.id.editAge);

        btnUploadText = (Button) findViewById(R.id.btnUploadText);
        btnUploadImage = (Button) findViewById(R.id.btnUploadImage);
        btnChooseImage = (Button) findViewById(R.id.btnChooseImage);

        imageUpload = (ImageView) findViewById(R.id.imageUpload);

        btnUploadText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertTEXT();
            }
        });
        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUpload.getDrawable() == null) {
                    Toast.makeText(uploadActivity.this, "PLEASE SELECT AN IMAGE FIRST!", Toast.LENGTH_SHORT).show();
                } else {
                    insertImage();
                }
            }
        });
        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
            }
        });
    }


    private void insertTEXT() {
        final ProgressDialog inserting = ProgressDialog.show(this, "INSERT TEXT", "Inserting...", false, false);
        inserting.show();

        final String name = editName.getText().toString();
        final String age = editAge.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_INSERTTEXT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        inserting.dismiss();
                        Toast.makeText(uploadActivity.this, "DATA INSERT SUCCESSFUL", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof NetworkError) {
                            inserting.dismiss();
                            Toast.makeText(uploadActivity.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof TimeoutError) {
                            inserting.dismiss();
                            Toast.makeText(uploadActivity.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof NoConnectionError) {
                            inserting.dismiss();
                            Toast.makeText(uploadActivity.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Constants.INSERTNAME, name);
                params.put(Constants.INSERTAGE, age);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public void insertImage() {
        final ProgressDialog inserting = ProgressDialog.show(this, "INSERT IMAGE", "Inserting...", false, false);
        inserting.show();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        StringRequest request = new StringRequest(Request.Method.POST, Constants.URL_INSERTIMAGE, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                     inserting.dismiss();
                     Toast.makeText(uploadActivity.this, "Uploaded Successful", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    inserting.dismiss();
                    Toast.makeText(uploadActivity.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {
                    inserting.dismiss();
                    Toast.makeText(uploadActivity.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    inserting.dismiss();
                    Toast.makeText(uploadActivity.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
             @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put(Constants.INSERTIMAGE, imageString);
                return parameters;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue rQueue = Volley.newRequestQueue(uploadActivity.this);
        rQueue.add(request);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageUpload.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
