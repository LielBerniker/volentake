package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.volentake.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterPost extends RecyclerView.Adapter<AdapterPost.MyViewHolder> {
    Context context;
    ArrayList<Assoc_post> listPosts= new ArrayList<>();
    private DatabaseReference postref;

    public AdapterPost(Context context, ArrayList<Assoc_post> listPosts){
        this.context = context;
        this.listPosts = listPosts;
        postref = FirebaseDatabase.getInstance().getReference().child("Posts");
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_search_posts, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
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
        return 0;
    }

    public void setPosts(ArrayList<Assoc_post> posts){
        this.listPosts = posts;
        notifyDataSetChanged();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
