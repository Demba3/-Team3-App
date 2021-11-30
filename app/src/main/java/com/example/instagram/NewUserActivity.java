package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class NewUserActivity extends AppCompatActivity {
    public static final String TAG = "NewUserActivity";
    EditText et_userName;
    EditText et_password;
    Button btn_create;
    CheckBox cb_isBusiness;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        et_userName = findViewById(R.id.et_NewUser);
        et_password = findViewById(R.id.et_NewPassword);
        btn_create = findViewById(R.id.btn_create);
        cb_isBusiness = findViewById(R.id.cb_isBusiness);



        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"create button clicked");
                String username = et_userName.getText().toString();
                String password = et_password.getText().toString();
                boolean businessType = cb_isBusiness.isChecked();

                //Toast.makeText(LoginActivity.this,"CLICKED", Toast.LENGTH_LONG).show();
                createAndLogin(username , password, businessType);

            }
        });


    }

    private void createAndLogin(String username, String password, boolean businessType) {
        // Create the ParseUser
        ParseUser user = new ParseUser();
        // Set core properties
        user.setUsername(username);
        user.setPassword(password);
        user.put(User.KEY_IS_BUSINESS, businessType);

        // Invoke signUpInBackground
        Log.i(TAG , "Attempting to create and login user " + username);
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                    Toast.makeText(NewUserActivity.this, "Welcome!", Toast.LENGTH_SHORT).show();
                    goMainActivity();
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                    e.getCode();
                    Log.e(TAG, "Issue with login", e);
                    Toast.makeText(NewUserActivity.this, "Error", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "User "+  username + ", " + "Password " + password , e);
                    Log.e(TAG, "User weird "+  user , e);
                    return;
                }
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