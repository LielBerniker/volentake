package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.volentake.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterPost extends RecyclerView.Adapter<AdapterPost.ViewHolder> {
    ArrayList<Assoc_post> listPosts= new ArrayList<>();

    public AdapterPost(Context context, ArrayList<Assoc_post> listPosts){
        this.listPosts = listPosts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_post_shape, parent, false);
        ViewHolder holder= new ViewHolder(view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        final String uid = modelPosts.get(position).getUid();
//        String nameh = modelPosts.get(position).getUname();
//        final String titlee = modelPosts.get(position).getTitle();
//        final String descri = modelPosts.get(position).getDescription();
//        final String ptime = modelPosts.get(position).getPtime();
//        String dp = modelPosts.get(position).getUdp();
//        String plike = modelPosts.get(position).getPlike();
//        final String image = modelPosts.get(position).getUimage();
//        String email = modelPosts.get(position).getUemail();
//        String comm = modelPosts.get(position).getPcomments();
//        final String pid = modelPosts.get(position).getPtime();

    }

    @Override
    public int getItemCount() {
        return listPosts.size();
    }

    public void setPosts(ArrayList<Assoc_post> posts){
        this.listPosts = posts;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtNameAssociation, txtNumVol;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
