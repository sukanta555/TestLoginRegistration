package com.example.testloginregistration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Dashboard extends AppCompatActivity {
    CardView view,upload,payment;
    TextView login;
    ImageView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().hide();//title bar hide
        //type casting
        view=(CardView)findViewById(R.id.ViewBtn);
        upload=(CardView) findViewById(R.id.Upload);
        payment=(CardView)findViewById(R.id.payment);
        login=(TextView)findViewById(R.id.logIn);
        logout=(ImageView)findViewById(R.id.dashLogOut);

        //Logout start
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lout=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(lout);
                Toast.makeText(Dashboard.this,"Logout Sucessfully",Toast.LENGTH_SHORT).show();
            }
        });
        //logout end

        //get value uname called by Intent//
        String s1=getIntent().getStringExtra("username");
        login.setText(s1);
        //End Intent//

        //login page called
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Login.class);
                startActivity(i);
            }
        });
        //end login page
        //payment method page called
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pay=new Intent(getApplicationContext(),RazorPay.class);
                startActivity(pay);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent u=new Intent(getApplicationContext(),UploadImage.class);
                startActivity(u);
            }
        });
    }
    public void onBackPressed(){
        //super.onBackPressed();
    }
}