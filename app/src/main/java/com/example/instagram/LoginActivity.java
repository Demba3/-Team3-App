package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";

    private EditText usernameET;
    private EditText passwordET;
    private Button login;
    private Button newUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(ParseUser.getCurrentUser() != null){
            goMainActivity();
        }

        usernameET = findViewById(R.id.et_User);
        passwordET = findViewById(R.id.et_Password);
        login = findViewById(R.id.btn_create);
        newUser = findViewById(R.id.btn_newUser);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Login button clicked");
                String username = usernameET.getText().toString();
                String password = passwordET.getText().toString();
                //Toast.makeText(LoginActivity.this,"CLICKED", Toast.LENGTH_LONG).show();
                loginUser(username , password);

            }
        });

        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"NewUser button clicked");
                //Toast.makeText(LoginActivity.this,"CLICKED", Toast.LENGTH_LONG).show();
                createUserActivity();

            }
        });

    }

    private void createUserActivity() {
        //activity (this) is an instance of a context
        Intent i =  new Intent(this, NewUserActivity.class);
        startActivity(i);
        finish();
    }


    private void loginUser(String username, String password){
        Log.i(TAG , "Attempting to login user " + username);
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e != null){
                    e.getCode();
                    Log.e(TAG, "Issue with login", e);
                    Toast.makeText(LoginActivity.this, "Wrong username/password", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "User "+  username + ", " + "Password " + password , e);
                    Log.e(TAG, "User weird "+  user , e);
                    return;
                }
                //Navigate to mainActivity
                goMainActivity();
                Toast.makeText(LoginActivity.this, "Welcome back", Toast.LENGTH_SHORT).show();
            }
        });

    }
    
    

    private void goMainActivity() {
        //activity (this) is an instance of a context
        Intent i =  new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}