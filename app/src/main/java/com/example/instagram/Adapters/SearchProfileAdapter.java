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
import com.example.instagram.R;
import com.example.instagram.User;
import com.parse.ParseFile;

import java.util.List;

public class SearchProfileAdapter extends RecyclerView.Adapter<SearchProfileAdapter.ViewHolder> {
    private Context context;
    private List<User> users;

    public SearchProfileAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public SearchProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_item, parent, false);
        return new SearchProfileAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchProfileAdapter.ViewHolder holder, int position) {
        User user = users.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_Username;
        private TextView tv_Description;
        private ImageView iv_ProfilePicture;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_Username = itemView.findViewById(R.id.searchItem_Username);
            tv_Description = itemView.findViewById(R.id.searchItem_Description);
            iv_ProfilePicture = itemView.findViewById(R.id.searchItem_profilePic);

        }

        public void bind(User user) {
            ParseFile image= user.getProfilePic();

            if(user.getType() == true){
                if(image != null){
                    Glide.with(context).load(user.getProfilePic().getUrl()).into(iv_ProfilePicture);
                    tv_Username.setText(user.getUser().getUsername());
                    tv_Description.setText(user.getDescription());

                }

            }
        }
    }
}
