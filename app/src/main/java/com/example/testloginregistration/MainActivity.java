package com.example.testloginregistration;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

public class MainActivity extends AppCompatActivity {
    EditText email,password;
    Button login,reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();//title bar hide
        //Type custing OR Hook
        email=(EditText)findViewById(R.id.MailId);
        password=(EditText)findViewById(R.id.Pass);

        login=(Button)findViewById(R.id.LoginBtn);
        reg=(Button)findViewById(R.id.RegBtn);

        //Call the url when submit
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().equals("")){
                    AlertDialog.Builder aks=new AlertDialog.Builder(MainActivity.this);
                    aks.setTitle("Please enter User Name");
                    aks.setCancelable(false).setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface,int which) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog ref=aks.create();
                    ref.show();
                }
                else if(password.getText().toString().equals("")){
                    AlertDialog.Builder passO=new AlertDialog.Builder(MainActivity.this);
                    passO.setTitle("Please Enter Password");
                    passO.setCancelable(false).setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface,int which) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog passw=passO.create();
                    passO.show();
                }
                else {
                    Checklog ( "http://wishnetkolkata.com/helpdesk/test1/login.php" );
                }

            }
        });
        //Register page called
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Registration.class));
            }
        });

        //end register page called
    }

    private void Checklog(String url) {
        StringRequest log=new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("response",response);// echo prient
                    if (response.trim().equals("generated")){
                       Intent i=new Intent(getApplicationContext(),Dashboard.class); //Login page
                        i.putExtra("username",email.getText().toString());//view next page uname
                        startActivity(i);
                        Toast.makeText(MainActivity.this,response,Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(MainActivity.this,"Please Enter valid User Name & Password",Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Server Error",Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String,String>getParams()throws AuthFailureError{
                HashMap<String,String> malyasia=new HashMap<>( );
                malyasia.put("username",email.getText().toString());
                malyasia.put("passcode",password.getText().toString());
                return malyasia;
            }
        };
        Volley.newRequestQueue ( getApplicationContext () ).add (log);
    }
    public void onBackPressed(){
        //super.onBackPressed();
    }
}