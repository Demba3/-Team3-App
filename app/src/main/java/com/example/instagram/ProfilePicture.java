package com.example.instagram;


import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseFileUtils;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("ProfilePicture")

public class ProfilePicture extends ParseObject {
    public static final String KEY_USER= "userPointer";
    public static final String KEY_PROFILE_PICTURE = "profilePicture";


    public ParseFile getProfilePicture(){ return getParseFile(KEY_PROFILE_PICTURE);}

    public void setProfilePic(ParseFile parseFile){

        put(KEY_PROFILE_PICTURE, parseFile);
    }

    public ParseUser getUserPointer(){
        return getParseUser(KEY_USER);
    }

    public void setUserPointer(ParseUser user){
        put(KEY_USER, user);
    }

}
