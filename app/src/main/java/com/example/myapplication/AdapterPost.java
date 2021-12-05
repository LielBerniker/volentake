package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.volentake.DetailsPostVol;
import com.example.volentake.R;

import java.util.ArrayList;

public class AdapterPost extends RecyclerView.Adapter<AdapterPost.ViewHolder> {
    Context context;
    String vol_user_id;
    ArrayList<Pair<Assoc_post,String>> listPosts= new ArrayList<>();

    public AdapterPost(Context context,String vol_user_id){
        this.context = context;
        this.vol_user_id = vol_user_id;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_post_shape, parent, false);
        ViewHolder holder= new ViewHolder(view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtNameAssociation.setText(listPosts.get(position).first.getName());
        holder.txtNumVol.setText(String.valueOf(listPosts.get(position).first.getNum_of_participants()));
        holder.txtType.setText(listPosts.get(position).first.getType());
        holder.txtCity.setText(listPosts.get(position).first.getLocation().getCity());
        holder.btnSeeMoreDetails.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailsPostVol.class);
            intent.putExtra("vol_id",vol_user_id);
            intent.putExtra("post_id",listPosts.get(position).second);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return listPosts.size();
    }

    public void setPosts(ArrayList<Pair<Assoc_post,String>> posts){
        this.listPosts = posts;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtNameAssociation, txtNumVol,txtType,txtCity;
        private Button btnSeeMoreDetails;
        private CardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameAssociation = itemView.findViewById(R.id.nameMessage);
            txtNumVol = itemView.findViewById(R.id.locationPost);
            txtType = itemView.findViewById(R.id.numVolOfPost);
            txtCity = itemView.findViewById(R.id.mailVolunteer);
            parent = itemView.findViewById(R.id.parent);
            btnSeeMoreDetails = itemView.findViewById(R.id.btnseedetails);



        }
    }
}
