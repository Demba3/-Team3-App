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


    public ParseFile getProfilePic(){ return getParseFile(KEY_PROFILE_PICTURE);}

    public void setProfilePic(ParseFile parseFile){
        put(KEY_PROFILE_PICTURE, parseFile);
    }


    public ParseUser getUserName(){
        return getParseUser(KEY_USER_NAME);
    }

    public void setUserName(ParseUser user){
        put(KEY_USER_NAME, user);
    }

}
