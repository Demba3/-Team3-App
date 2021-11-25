package com.example.instagram;
import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    //INITIALIZES PARSE SDK AS SOON AS THE APP IS CREATED
    @Override
    public void onCreate() {
        super.onCreate();

        // Register your parse models
        ParseObject.registerSubclass(Post.class);

        // set applicationId, and server server based on the values in the back4app settings.
        // any network interceptors must be added with the Configuration Builder given this syntax
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("cFSNxYZ5GDugufCirnRA1YlklEdrID0ycgBDmhrj")// should correspond to Application Id env variable
                .clientKey("F5AOICBmIS5Mc2NXw0bpCXTAPjPL3GEfaURrjx46") // should correspond to Client key env variable
                .server("https://parseapi.back4app.com").build()
        );
    }
}
//xd