package com.example.instagram.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;
import android.provider.MediaStore;
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
import androidx.core.content.FileProvider;
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
    public static final String TAG = "UserProfileFragment";
    private TextView tv_UserName;
    private ImageView iv_ProfilePicture;
    private Button btn_setProfilePic;
    private Button btn_submit;
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
        if(ParseUser.getCurrentUser().getParseFile(User.KEY_PROFILE_PICTURE) != null) {
            Glide.with(this).load(ParseUser.getCurrentUser().getParseFile(User.KEY_PROFILE_PICTURE).getUrl()).into(iv_ProfilePicture);
        }

        btn_setProfilePic = getView().findViewById(R.id.btn_setProfilePic);
        btn_setProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchGallery();
            }
        });
        btn_submit = getView().findViewById(R.id.btn_save);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (galleryFile == null) {
                    Toast.makeText(getContext(), "Profile Picture didn't change", Toast.LENGTH_SHORT).show();
                    return;
                }
                ParseUser currentUser = ParseUser.getCurrentUser();
                saveProfilePicture(currentUser ,galleryFile);
            }
        });

    }

    private void launchGallery() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        //intent.setType("image/*");
        galleryFile = getPhotoFileUri(photoFileName);


        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.codepath.fileprovider", galleryFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(Intent.createChooser(intent, "Select Picture"),GALLERY_REQUEST_CODE);

        }



    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            Uri imageData = data.getData();
            //galleryFile = getPhotoFileUri("profilePic.jpg");
            //Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.codepath.fileprovider", galleryFile);
            //intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
            Glide.with(this).load(imageData).into(iv_ProfilePicture);



            Log.d(TAG, "onActivityResult: " + imageData);


            if (requestCode == GALLERY_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                    // by this point we have the camera photo on disk
                    Bitmap takenImage = BitmapFactory.decodeFile(galleryFile.getAbsolutePath());
                    // RESIZE BITMAP, see section below
                    // Load the taken image into a preview
                    //iv_ProfilePicture.setImageBitmap(takenImage);
                } else { // Result was a failure
                    Toast.makeText(getContext(), "Picture wasn't chosen", Toast.LENGTH_SHORT).show();
                }
            }


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


    private void saveProfilePicture(ParseUser currentUser,File galleryFile) {
        User user = new User();
        user.setProfilePic(new ParseFile(galleryFile));
        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving profilepic", e);
                    Toast.makeText(getContext(), "Error while changing profilepic", Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "changes saved successfully");
                //iv_ProfilePicture.setImageResource(0);
                Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
