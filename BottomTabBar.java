package com.example.abdallaheltahawi.namaaapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class BottomTabBar extends AppCompatActivity {
    public static Context context ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_tab_bar);
        context=this;
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_item1:
                                selectedFragment = new Projects();
                                break;
                            case R.id.action_item2:
                                selectedFragment = new Register();
                                break;
                            case R.id.action_item3:
                                selectedFragment = new Login();
                                break;
                        }
                     FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                       transaction.replace(R.id.frame_layout, selectedFragment);
                       transaction.commit();
                        return true;
                    }
                });

        //Manually displaying the first fragment - one time only
       FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
       transaction.replace(R.id.frame_layout, Projects.newInstance());
        transaction.commit();
        //Used to select an item programmatically
        bottomNavigationView.getMenu().getItem(2).setChecked(true);
    }
    }

