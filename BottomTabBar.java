package com.example.abdallaheltahawi.namaaapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class BottomTabBar extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private BottomNavigationView bottomNavigation;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_tab_bar);
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);


        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override

                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.action_item1:
                                fragment = new ItemOneFragment();
                                Intent intent = new Intent(BottomTabBar.this, ProfileActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.action_item2:
                               fragment = new ItemTwoFragment();
                                Intent intent1 = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent1);
                                break;
                            case R.id.action_item3:
                                fragment = new ItemThreeFragment();
                                Intent intent2 = new Intent(getApplicationContext(), Recyclerview.class);
                                startActivity(intent2);
                                break;
                        }
                        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, ItemThreeFragment.newInstance());
                        transaction.commit();
                        return true;
                    }

                });

        //Manually displaying the first fragment - one time only
       // FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      //  transaction.replace(R.id.frame_layout, ItemThreeFragment.newInstance());
      //  transaction.commit();

        //Used to select an item programmatically
        //bottomNavigationView.getMenu().getItem(2).setChecked(true);
    }
    }

