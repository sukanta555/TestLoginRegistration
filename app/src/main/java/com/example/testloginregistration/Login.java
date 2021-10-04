package com.example.testloginregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Login extends AppCompatActivity {
    TextView v1;
    ArrayList<DepartmentSetGet> ar=new ArrayList<DepartmentSetGet>();
    ListView mylist;
    ImageView homeid,logout;
    String url= "http://wishnetkolkata.com/helpdesk/test1/call.php";
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); //title hide bar
        setContentView(R.layout.activity_login);
        //Type casting
        v1=(TextView)findViewById(R.id.v1);
        mylist=(ListView)findViewById(R.id.myListView);
        pb=(ProgressBar) findViewById(R.id.Prob);
        homeid=(ImageView)findViewById(R.id.honeId);
        logout=(ImageView)findViewById(R.id.logOut);
        GetDptList(url);

        //get value uname called by Intent//
        String s1=getIntent().getStringExtra("username");
        v1.setText(s1);
        //End Intent//
        //Logout//
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ion= new Intent(getApplicationContext(),MainActivity.class);
                startActivity(ion);
                Toast.makeText(Login.this,"Logout Successfully",Toast.LENGTH_SHORT).show();
            }
        });
        //end logout code//
        //Home Page called
        homeid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent h=new Intent(getApplicationContext(),Dashboard.class);
                startActivity(h);
                Toast.makeText(Login.this,"Welcome to Home page",Toast.LENGTH_SHORT).show();
            }
        });
    }
    //Edit page called//
    public void btnEdit(View view){
        startActivity(new Intent(getApplicationContext(),EditView.class));
    }
    //Edit page code end//
    //Delete page called//
    public void btnDelete(View view){
        startActivity(new Intent(getApplicationContext(),DeleteData.class));
    }
    //End page called delete//

    private void GetDptList(String url) {
        StringRequest DeptList=new StringRequest(Request.Method.GET,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("response",response);
                    pb.setVisibility(View.GONE);// Loder off
                    JSONArray arr =new JSONArray(response);
                    for (int i=0; i<arr.length();i++){
                        JSONObject oh = arr.getJSONObject(i);
                        DepartmentSetGet sg=new DepartmentSetGet();
                        sg.setZone_name(oh.getString("zone_name"));
                        sg.setDt_tm(oh.getString("dt_tm"));
                        sg.setId(oh.optString("id")); // use optString, this string not sent null value
                        ar.add(sg);
                    }
                    if(ar.size()>0){
                        mylist.setVisibility(View.VISIBLE);//Loder
                        mylist.setAdapter(new Myadapter());

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this,"Server Error",Toast.LENGTH_SHORT).show();

            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(DeptList);
    }

    //public void onBackPressed(){
        //super.onBackPressed();
    //}

    public class Myadapter extends BaseAdapter {
        @Override
        public int getCount() {
            return ar.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position,View convertView,ViewGroup parent) {
            LayoutInflater inf=getLayoutInflater();
            View row = inf.inflate(R.layout.child,null); //create child.xml file

            TextView Department=row.findViewById(R.id.child); // first value called
            Department.setText(ar.get(position).getZone_name()); //Edit value get
            Button btnEdit=row.findViewById(R.id.editBtn);//edit value called by editBtn

            TextView Department2 = row.findViewById(R.id.dt1); //second value called
            Department2.setText(ar.get(position).getDt_tm()); //Edit value get

            //TextView DepartmentId=row.findViewById(R.id.id);
            //DepartmentId.setText(ar.get(position).getid());

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(getApplicationContext(),EditView.class);
                    i.putExtra("zone_name",ar.get(position).getZone_name());
                    i.putExtra("date",ar.get(position).getDt_tm());
                    i.putExtra("id",ar.get(position).getId()); //Id called
                    startActivity(i);
                }
            });

            return row;
        }
    }
}