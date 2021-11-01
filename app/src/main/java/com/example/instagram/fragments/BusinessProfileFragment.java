package com.example.instagram.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.instagram.R;
import com.example.instagram.User;
import com.parse.ParseFile;

public class BusinessProfileFragment extends Fragment {

    ImageView iv_profilePicture;
    TextView tv_userName;
    RecyclerView rv_userPosts;


    public BusinessProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_business_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        iv_profilePicture = view.findViewById(R.id.iv_profilePicture);
        tv_userName = view.findViewById(R.id.tv_userName);
        rv_userPosts = view.findViewById(R.id.rv_profilePosts);



    }
}