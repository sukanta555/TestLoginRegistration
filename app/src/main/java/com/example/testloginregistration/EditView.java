package com.example.testloginregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class EditView extends AppCompatActivity {
    EditText zoneName,dateBtn,eid;
    Button editBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//hidden title bar
        setContentView(R.layout.activity_edit_view);
        //id called
        zoneName=findViewById(R.id.zoneName);
        dateBtn=findViewById(R.id.dateBtn);
        editBtn=findViewById(R.id.editBtn);
        eid=findViewById(R.id.eid);
        //Edit value called
        String s1=getIntent().getStringExtra("zone_name");
        String s2=getIntent().getStringExtra("date");
        String s3=getIntent().getStringExtra("id");
        //Toast.makeText(this,s3,Toast.LENGTH_SHORT).show();
        zoneName.setText(s1);
        dateBtn.setText(s2);
        eid.setText(s3);

        //End edit value called
        //start edit
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upd("http://wishnetkolkata.com/helpdesk/test1/upd.php");
            }
        });
        //edit
    }

    private void upd(String s) {
        StringRequest updlog=new StringRequest(Request.Method.POST,s,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //Log.w("response",response);//warning print
                    Log.i("response",response);//information print
                    //Log.d("response",response);//debug print
                    //Log.v("response",response);//verbose print
                    Log.e("response",response);//error print
                    if (response.trim().equals("dataupd")){
                        Intent j=new Intent(getApplicationContext(),Login.class);
                        startActivity(j);
                        Toast.makeText(EditView.this,"Update successfully",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(EditView.this,"Data not update",Toast.LENGTH_SHORT).show();
                    }

                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditView.this,"Server error",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String>editupd=new HashMap<>();
                editupd.put("zone_name",zoneName.getText().toString());
                editupd.put("dt_tm",dateBtn.getText().toString());
                editupd.put("id",eid.getText().toString());
                return editupd;
            }
        };
        Volley.newRequestQueue ( getApplicationContext () ).add (updlog);
    }

    //Back page called
    public void backBtn(View view) {
        startActivity(new Intent(getApplicationContext(),Login.class));
    }
    //End code

}