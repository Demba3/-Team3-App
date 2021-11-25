package com.example.instagram.Adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instagram.Post;
import com.example.instagram.R;
import com.example.instagram.User;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.List;


public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder>{

    private Context context;
    private List<Post> posts;


    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);


        holder.bind(post);


    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_User;
        private ImageView iv_Photo;
        private TextView tv_Description;
        private ImageView iv_ProfilePicture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_User = itemView.findViewById(R.id.tv_userPost);
            iv_Photo = itemView.findViewById(R.id.iv_photo);
            tv_Description = itemView.findViewById(R.id.tv_Description);
            iv_ProfilePicture = itemView.findViewById(R.id.iv_postProfilePicture);

            //set click listener on username so other accounts can check their profile
            tv_User.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        }

        public void bind(Post post) {
            //Bind the post data into the view element
            ParseFile image = post.getImage();
            ParseFile profilePic = post.getUser().getParseFile("profilePicture");

            if(image != null){
                Glide.with(context).load(post.getImage().getUrl()).into(iv_Photo);


            }
            if(profilePic != null){
                Glide.with(context).load(post.getUser().getParseFile("profilePicture").getUrl()).into(iv_ProfilePicture);
                //Glide.with(context).load(post.getProfilePic().getParseFile(ProfilePicture.KEY_PROFILE_PICTURE).getUrl()).into(iv_Photo);
            }


            tv_User.setText(post.getUser().getUsername());
            tv_Description.setText(post.getDescription());



        }
    }
}
