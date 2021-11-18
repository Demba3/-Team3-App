package com.example.instagram.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instagram.Adapters.ProfileAdapter;
import com.example.instagram.Post;
import com.example.instagram.R;
import com.example.instagram.User;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class BusinessProfileFragment extends Fragment{
    public static final String TAG = "NewProfileFragment";
    private TextView tv_UserName;
    private ImageView iv_ProfilePicture;
    private TextView tv_Description;
    private RecyclerView rv_profilePosts;

    protected ProfileAdapter profileAdapter;
    protected List<Post> allPosts;

    public BusinessProfileFragment() {    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_business_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        allPosts = new ArrayList<>();

        //ParseFile ProfilePic = user.getParseFile(User.KEY_PROFILE_PICTURE);
        tv_UserName = getView().findViewById(R.id.tv_CompanyName);
        tv_UserName.setText(ParseUser.getCurrentUser().getUsername());
        //Log.d(TAG, ParseUser.getCurrentUser().getUsername());

        iv_ProfilePicture = getView().findViewById(R.id.iv_profilePicture);
        if(ParseUser.getCurrentUser().getParseFile(User.KEY_PROFILE_PICTURE) != null) {
            Glide.with(this).load(ParseUser.getCurrentUser().getParseFile(User.KEY_PROFILE_PICTURE).getUrl()).into(iv_ProfilePicture);
        }

        tv_Description = getView().findViewById(R.id.tv_ProfileDescription);

        profileAdapter = new ProfileAdapter(getContext(), allPosts);
        rv_profilePosts = getView().findViewById(R.id.rv_ProfilePosts);
        rv_profilePosts.setAdapter(profileAdapter);
        rv_profilePosts.setLayoutManager(new LinearLayoutManager(getContext()));
        queryPosts();
    }

    protected void queryPosts () {
        // Specify which class to query
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER , ParseUser.getCurrentUser());
        query.addDescendingOrder(Post.KEY_CREATE_KEY);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                for (Post post : posts) {
                    Log.i(TAG, "Post: " + post.getDescription() + " Username: " + post.getUser().getUsername());
                }
                allPosts.addAll(posts);
                profileAdapter.notifyDataSetChanged();
            }
        });


    }
}
