package com.example.abdallaheltahawi.namaaapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextUserName,editTextPassword,editTextEmail;
    private Button buttonRegister;
    private ProgressDialog progressDialog;
    private TextView textViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(SharedPrefManager.getInstance(this).isLoggedIn())
        {
            finish();
            startActivity(new Intent(this,LoginActivity.class));
            return;
        }

        editTextEmail=(EditText)findViewById(R.id.editTextEmail);
        editTextPassword=(EditText)findViewById(R.id.editTextPassword);
        editTextUserName=(EditText)findViewById(R.id.editTextUserName);
        buttonRegister=(Button)findViewById(R.id.buttonRegister);
        textViewLogin=(TextView)findViewById(R.id.textViewLogin);
        progressDialog=new ProgressDialog(this);

        buttonRegister.setOnClickListener(this);
        textViewLogin.setOnClickListener(this);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
            StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
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
                    Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
            new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            progressDialog.hide();
            Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
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
       // RequestQueue requestQueue=Volley.newRequestQueue(this);
       // requestQueue.add(stringRequest);

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        return false;
    }

    @Override
    public void onClick(View view) {

        if (view==buttonRegister)
        {
            registerUser();
        }
        if(view==textViewLogin)
        {
            //startActivity(new Intent(this,Recyclerview.class));
            startActivity(new Intent(this,LoginActivity.class));
        }

    }
}
