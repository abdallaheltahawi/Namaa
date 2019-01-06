/*
 * Copyright (c) 2017. Truiton (http://www.truiton.com/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 * Mohit Gupt (https://github.com/mohitgupt)
 *
 */

package com.example.abdallaheltahawi.namaaapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends Fragment implements View.OnClickListener{
    public static Register newInstance() {
        Register fragment = new Register();
        return fragment;
    }
    private EditText editTextUserName,editTextPassword,editTextEmail;
    private Button buttonRegister;
    private ProgressDialog progressDialog;
    private TextView textViewLogin;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {

        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                Constants.URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // progressDialog.dismiss();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    Toast.makeText(BottomTabBar.context,jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(BottomTabBar.context,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }){

        };
         RequestQueue requestQueue=Volley.newRequestQueue(getContext());
         requestQueue.add(stringRequest);

        RequestHandler.getInstance(getContext()).addToRequestQueue(stringRequest);

        progressDialog=new ProgressDialog(getContext());

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        View v= inflater.inflate(R.layout.activity_main, container, false);
          editTextEmail=(EditText)v.findViewById(R.id.editTextEmail);
          editTextPassword=(EditText)v.findViewById(R.id.editTextPassword);
          editTextUserName=(EditText)v.findViewById(R.id.editTextUserName);
          buttonRegister=(Button)v.findViewById(R.id.buttonRegister);
          textViewLogin=(TextView)v.findViewById(R.id.textViewLogin);
          final String username=editTextUserName.getText().toString().trim();
          final String password=editTextPassword.getText().toString().trim();
          final String email=editTextEmail.getText().toString().trim();

          buttonRegister.setOnClickListener(this);
         textViewLogin.setOnClickListener(this);
        return v;

    }
    private boolean registerUser()
    {

       final String username=editTextUserName.getText().toString().trim();
        final String password=editTextPassword.getText().toString().trim();
        final String email=editTextEmail.getText().toString().trim();

        progressDialog.setMessage("جاري التسجيل ...");
        progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                Constants.URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    Toast.makeText(BottomTabBar.context,jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(BottomTabBar.context,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("username",username);
                params.put("password",password);
                params.put("email",email);

                return params;
            }
        };
         RequestQueue requestQueue=Volley.newRequestQueue(getContext());
         requestQueue.add(stringRequest);

        RequestHandler.getInstance(getContext()).addToRequestQueue(stringRequest);
        return false;
    }
    @Override
    public void onClick(View v) {
           if (v==buttonRegister)
         {
              registerUser();
          }
           if(v==textViewLogin)
          {
               //startActivity(new Intent(getContext(),Recyclerview.class));
               startActivity(new Intent(getContext(),Register.class));
         }

    }
}
