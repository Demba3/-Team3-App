package com.example.instagram.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.instagram.Post;
import com.example.instagram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ProfileFragment extends PostsFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_business_profile, container, false);
    }


    @Override
    protected void queryPosts() {
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
                postsAdapter.notifyDataSetChanged();
            }
        });
    }
}
