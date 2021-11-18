package com.example.instagram.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.bumptech.glide.Glide;

import com.example.instagram.Post;
import com.example.instagram.R;
import com.example.instagram.User;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;


public class UserProfileFragment extends Fragment {
    public static final int GALLERY_REQUEST_CODE = 123;
    public static final String TAG = "NewProfileFragment";
    private TextView tv_UserName;
    private ImageView iv_ProfilePicture;
    private Button btn_setProfilePic;
    private File galleryFile;
    public String photoFileName = "photo.jpg";


    public UserProfileFragment() {    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //ParseFile ProfilePic = user.getParseFile(User.KEY_PROFILE_PICTURE);
        tv_UserName = getView().findViewById(R.id.tv_userName);
        tv_UserName.setText(ParseUser.getCurrentUser().getUsername());
        //Log.d(TAG, ParseUser.getCurrentUser().getUsername());

        iv_ProfilePicture = getView().findViewById(R.id.iv_profileUserPicture);
        if(ParseUser.getCurrentUser().getParseFile(User.KEY_PROFILE_PICTURE).getUrl() != null) {
            Glide.with(this).load(ParseUser.getCurrentUser().getParseFile(User.KEY_PROFILE_PICTURE).getUrl()).into(iv_ProfilePicture);
        }
        btn_setProfilePic = getView().findViewById(R.id.btn_setProfilePic);
        btn_setProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Pick an image"), GALLERY_REQUEST_CODE);

            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            Uri imageData = data.getData();
            galleryFile = getPhotoFileUri(imageData.getPath());
            Glide.with(this).load(imageData).into(iv_ProfilePicture);
            Log.d(TAG, "onActivityResult: " + imageData);
            //saveProfilePicture(galleryFile);
            //iv_ProfilePicture.setImageURI(imageData);
            ParseUser user = ParseUser.getCurrentUser();
            user.put(User.KEY_PROFILE_PICTURE, new ParseFile(new File(galleryFile.getAbsolutePath())));
            user.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e != null) {
                        Log.e(TAG, "Error while saving", e);
                        Toast.makeText(getContext(), "Error while saving", Toast.LENGTH_SHORT).show();
                    }
                    Log.i(TAG, "User Profile updated successfully");
                    Toast.makeText(getContext(), "Posted", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri (String fileName){
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        return new File(mediaStorageDir.getPath() + File.separator + fileName);
    }


    private void saveProfilePicture(File galleryFile) {
        User user = new User();
        user.setImage(new ParseFile(galleryFile));
        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(getContext(), "Error while saving", Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "Post saved successfully");
                iv_ProfilePicture.setImageResource(0);
                Toast.makeText(getContext(), "Posted", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
