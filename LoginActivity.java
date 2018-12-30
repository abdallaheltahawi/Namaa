package com.example.abdallaheltahawi.namaaapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextUserName,editTextPassword;
    private Button buttonLogin;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(SharedPrefManager.getInstance(this).isLoggedIn())
        {
            finish();
            startActivity(new Intent(this,Recyclerview.class));
            return;
        }

        editTextUserName=(EditText)findViewById(R.id.editTextUserName);
        editTextPassword=(EditText)findViewById(R.id.editTextPassword);
        buttonLogin=(Button)findViewById(R.id.buttonLogin);

        progressDialog =new ProgressDialog(this);
        progressDialog.setMessage("يرجى الانتظار . . .");

        buttonLogin.setOnClickListener(this);

    }
    private void userLogin()
    {
        final String username=editTextUserName.getText().toString().trim();
        final String password=editTextPassword.getText().toString().trim();

        progressDialog.show();

        StringRequest stringRequest=new StringRequest(
                Request.Method.POST,
                Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj=new JSONObject(response);
                            if(!obj.getBoolean("error"))
                            {
                                SharedPrefManager.getInstance(getApplicationContext())
                                        .userLogin(
                                         obj.getInt("id"),
                                         obj.getString("username"),
                                         obj.getString("email")
                                        );
                             startActivity(new Intent(getApplicationContext(),Recyclerview.class));
                             finish();
                            }else {
                                Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_LONG).show();
                            }
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
                }

        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                params.put("username",username);
                params.put("password",password);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onClick(View view) {
        if(view==buttonLogin){
            userLogin();
        }
    }
}
