package com.example.abdallaheltahawi.namaaapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

public class Recyclerview extends AppCompatActivity {

    private static final String URL_DATA="https://graveless-latches.000webhostapp.com/android/v1/test.php";
    private RecyclerView recyclerview;
    private RecyclerView.Adapter adapter;
    private List<Listitem> listitems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        recyclerview=(RecyclerView) findViewById(R.id.recyclerView);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(adapter);
        listitems=new ArrayList<>();
        loadRecylerViewData();

    }
    private void loadRecylerViewData()
    {

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject jsonObject=new JSONObject(s);
                            JSONArray array=jsonObject.getJSONArray("heroes");


                            for (int i=0;i<array.length();i++)
                            {
                              JSONObject o=array.getJSONObject(i);
                              Listitem item=new Listitem(
                                      o.getString("name"),
                                      //o.getString("about"),
                                      o.getString("image")
                              );
                              listitems.add(item);
                            }

                            adapter=new MyAdapter(listitems,getApplicationContext());
                            recyclerview.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }
    }

