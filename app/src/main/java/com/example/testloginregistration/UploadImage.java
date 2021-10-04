package com.example.testloginregistration;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class UploadImage extends AppCompatActivity {
    Button btnSelectImage,btnUploadImage;
    ImageView imageView,loguot,home;
    Bitmap bitmap;
    TextView un1;
    String encodedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        getSupportActionBar().hide();//title bar hide
        //Type casting
        btnSelectImage=(Button)findViewById(R.id.btnSelectImage);
        btnUploadImage=(Button)findViewById(R.id.btnUploadImage);
        loguot=(ImageView)findViewById(R.id.logOutId);
        home=(ImageView)findViewById(R.id.idH);
        imageView=(ImageView)findViewById(R.id.imView);
        un1=(TextView)findViewById(R.id.un1);

        //Home page called
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent h=new Intent(getApplicationContext(),Dashboard.class);
                startActivity(h);
                Toast.makeText(UploadImage.this,"Welcome to Dashboard",Toast.LENGTH_SHORT).show();
            }
        });
        //Logout
        loguot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lu=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(lu);
                Toast.makeText(UploadImage.this,"Logout Successfully",Toast.LENGTH_SHORT).show();
            }
        });

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(UploadImage.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener(){
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response){
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Select Image"), 1);
                            }
                            public void onPermissionDenied(PermissionDeniedResponse response){

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest,PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }

                        }).check();
            }
        });

        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest request = new StringRequest(Request.Method.POST,"http://wishnetkolkata.com/helpdesk/test1/uploadImage.php",new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(UploadImage.this,response,Toast.LENGTH_SHORT).show();
                    }
                },new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UploadImage.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }){
                    protected Map<String,String> getParams() throws AuthFailureError{
                        Map<String, String> params = new HashMap<>();
                        params.put("image", encodedImage);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(UploadImage.this);
                requestQueue.add(request);
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode ==1 && resultCode == RESULT_OK && data!=null){
            Uri filePath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
                imageStore(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode,resultCode,data);
    }

    private void imageStore(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] imageBytes = stream.toByteArray();
        encodedImage = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }
    public void btnShowImages(View view){
        startActivity(new Intent(getApplicationContext(),ShowImages.class));
    }
}