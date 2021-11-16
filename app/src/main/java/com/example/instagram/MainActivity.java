package com.example.instagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.instagram.fragments.ComposeFragment;
import com.example.instagram.fragments.NewProfileFragment;
import com.example.instagram.fragments.PostsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    final FragmentManager fragmentManager = getSupportFragmentManager();


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_logout:
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
                goToLogin();
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToLogin() {
        //activity (this) is an instance of a context
        Intent i =  new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParseUser currentUser = ParseUser.getCurrentUser();

        if(currentUser.getBoolean(User.KEY_IS_BUSINESS) == true){
            setContentView(R.layout.activity_main_business);
            BottomNavigationView bottomNavigationViewBusiness = findViewById(R.id.bottom_navigation_business_main);
            Log.i(TAG, "User is a business");
            bottomNavigationViewBusiness.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment fragment;
                    switch (item.getItemId()) {
                        case R.id.action_home:
                            fragment = new PostsFragment();
                            break;
                        case R.id.action_new:
                            fragment = new ComposeFragment();
                            break;
                        case R.id.action_search:
                            fragment = new ComposeFragment();
                            break;
                        case R.id.action_profile:
                        default:
                            fragment = new NewProfileFragment();
                            break;

                    }
                    fragmentManager.beginTransaction().replace(R.id.frameLayoutContainer, fragment).commit();
                    return true;
                }
            });
            // Set default selection
            bottomNavigationViewBusiness.setSelectedItemId(R.id.action_home);
        }else{
            setContentView(R.layout.activity_main_user);
            BottomNavigationView bottomNavigationViewUser = findViewById(R.id.bottom_navigation_user_main);
            Log.i(TAG, "User is NOT a business");bottomNavigationViewUser.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment fragment;
                    switch (item.getItemId()) {
                        case R.id.action_home_user:
                            fragment = new PostsFragment();
                            break;
                        case R.id.action_search_user:
                            fragment = new ComposeFragment();
                            break;
                        case R.id.action_profile_user:
                        default:
                            fragment = new NewProfileFragment();
                            break;

                    }
                    fragmentManager.beginTransaction().replace(R.id.frameLayoutContainer, fragment).commit();
                    return true;
                }
            });
            // Set default selection
            bottomNavigationViewUser.setSelectedItemId(R.id.action_home);

        }



    }





}