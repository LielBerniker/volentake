package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.volentake.MainActivity;
import com.example.volentake.R;
import com.example.volentake.RequestAssociation;
import com.example.volentake.VolunteerLogIn;
import com.example.volentake.VolunteerPage;
import com.example.volentake.picture_edit_vol;
import com.example.volentake.postShape;

import java.util.ArrayList;

public class AdapterPost extends RecyclerView.Adapter<AdapterPost.ViewHolder> {
    Context context;
    String vol_user_id;
    ArrayList<Assoc_post> listPosts= new ArrayList<>();

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
        holder.txtNameAssociation.setText(listPosts.get(position).getName());
        holder.txtNumVol.setText(String.valueOf(listPosts.get(position).getNum_of_participants()));
        holder.txtType.setText(listPosts.get(position).getType());
        holder.txtCity.setText(listPosts.get(position).getLocation().getCity());
        holder.btnSeeMoreDetails.setOnClickListener(view -> {
            Intent intent = new Intent(context, RequestAssociation.class);
            intent.putExtra("vol_id",vol_user_id);
            intent.putExtra("post_id","dfdf");
            context.startActivity(intent);
        });
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
