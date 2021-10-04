package com.example.testloginregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DeleteData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_data);
    }
    //Page called
    public void deleteBtn (View View){
        startActivity(new Intent(getApplicationContext(),Login.class));
    }
}