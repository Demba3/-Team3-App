package com.example.instagram;


import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("User")

public class User extends ParseObject {
    public static final String KEY_USER= "user";
    public static final String KEY_PROFILE_PICTURE = "profilePicture";
    public static final String KEY_IS_BUSINESS = "isBusiness";


    public ParseFile getImage(){ return getParseFile(KEY_PROFILE_PICTURE);}

    public void setImage(ParseFile parseFile){
        put(KEY_PROFILE_PICTURE, parseFile);
    }

    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user){
        put(KEY_USER, user);
    }

}
