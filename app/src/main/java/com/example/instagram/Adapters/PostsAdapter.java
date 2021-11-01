package com.example.instagram.Adapters;

import android.content.Context;
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
import com.parse.ParseFile;

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_User = itemView.findViewById(R.id.tv_userPost);
            iv_Photo = itemView.findViewById(R.id.iv_photo);
            tv_Description = itemView.findViewById(R.id.tv_Description);

        }

        public void bind(Post post) {
            //Bind the post data into the view element
            ParseFile image = post.getImage();
            if(image != null){
                Glide.with(context).load(post.getImage().getUrl()).into(iv_Photo);
            }
            tv_User.setText(post.getUser().getUsername());
            tv_Description.setText(post.getDescription());


        }
    }
}
