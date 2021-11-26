package com.example.instagram;


import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("User")

public class User extends ParseObject {
    public static final String KEY_USER_NAME = "username";
    public static final String KEY_PROFILE_PICTURE = "profilePicture";
    public static final String KEY_IS_BUSINESS = "isBusiness";
    public static final String KEY_DESCRIPTION = "description";


    public ParseFile getProfilePic(){ return getParseFile(KEY_PROFILE_PICTURE);}

    public void setProfilePic(ParseFile parseFile){
        put(KEY_PROFILE_PICTURE, parseFile);
    }


    public ParseUser getUser(){
        return getParseUser(KEY_USER_NAME);
    }

    public void setUser(ParseUser user){
        put(KEY_USER_NAME, user);
    }

    public String getDescription(){
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description){
        put(KEY_DESCRIPTION, description);
    }

    public boolean getType() { return getBoolean(KEY_IS_BUSINESS);}
}
