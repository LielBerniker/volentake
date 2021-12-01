package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.volentake.R;

import java.util.ArrayList;

public class AdapterPost extends RecyclerView.Adapter<AdapterPost.ViewHolder> {
    Context context;
    ArrayList<Assoc_post> listPosts= new ArrayList<>();

    public AdapterPost(Context context){
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_post_shape, parent, false);
        ViewHolder holder= new ViewHolder(view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtNameAssociation.setText(listPosts.get(position).getName());
        holder.txtNumVol.setText(String.valueOf(listPosts.get(position).getNum_of_participants()));
        holder.txtType.setText(listPosts.get(position).getType());
        holder.txtCity.setText(listPosts.get(position).getLocation().getCity());
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
        private TextView txtNameAssociation, txtNumVol,txtType,txtCity;
        private CardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameAssociation = itemView.findViewById(R.id.nameAss);
            txtNumVol = itemView.findViewById(R.id.numVolpostinsert);
            txtType = itemView.findViewById(R.id.typepostinsert);
            txtCity = itemView.findViewById(R.id.citypostinsert);
            parent = itemView.findViewById(R.id.parent);

        }
    }
}
