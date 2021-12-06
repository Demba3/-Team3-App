package com.example.instagram.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagram.Adapters.SearchProfileAdapter;
import com.example.instagram.Post;
import com.example.instagram.R;
import com.example.instagram.User;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    public static final String TAG = "SearchFragment";
    private RecyclerView rvUsers;
    protected SearchProfileAdapter searchProfileAdapter;
    protected List<ParseUser> allUsers;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search , container ,false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvUsers = getView().findViewById(R.id.rv_SearchUsers);
        allUsers = new ArrayList<>();

        searchProfileAdapter = new SearchProfileAdapter(getContext(), allUsers);


        rvUsers.setAdapter(searchProfileAdapter);
        rvUsers.setLayoutManager(new LinearLayoutManager(getContext()));
        queryUsers();

    }

    protected void queryUsers () {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo(User.KEY_IS_BUSINESS, true);
            query.findInBackground(new FindCallback<ParseUser>() {
                public void done(List<ParseUser> objects, ParseException e) {
                    if (e != null) {
                        Log.e(TAG, "Issue getting users", e);
                    }
                    for (ParseUser user : objects) {
                        Log.i(TAG, "excelente");
                    }
                    allUsers.addAll(objects);
                    searchProfileAdapter.notifyDataSetChanged();
                }
            });



    }
}
