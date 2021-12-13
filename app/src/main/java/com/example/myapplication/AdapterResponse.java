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

public class AdapterResponse extends RecyclerView.Adapter<AdapterResponse.ViewHolder> {
    Context context;
    String ass_user_id;
    ArrayList<Pair<Response,String>> listResponse= new ArrayList<>();

    public AdapterResponse(Context context, String ass_user_id){
        this.context = context;
        this.ass_user_id = ass_user_id;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_response_shape, parent, false);
        ViewHolder holder= new ViewHolder(view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ///////for Liel
//        holder.txtNameAssociation.setText(listPosts.get(position).first.getName());
//        holder.txtNumVol.setText(String.valueOf(listPosts.get(position).first.getNum_of_participants()));
//        holder.txtType.setText(listPosts.get(position).first.getType());
//        holder.txtCity.setText(listPosts.get(position).first.getLocation().getCity());
//        holder.btnSeeMoreDetails.setOnClickListener(view -> {
//            Intent intent = new Intent(context, DetailsPostVol.class);
//            intent.putExtra("vol_id",vol_user_id);
//            intent.putExtra("post_id",listPosts.get(position).second);
//            context.startActivity(intent);
//        });

    }

    @Override
    public int getItemCount() {
        return listResponse.size();
    }

    public void setResponses(ArrayList<Pair<Response,String>> listResponse){
        this.listResponse = listResponse;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtNameResponse, txtNameAssociation, txtMailAssociation,txtNamePost;
        private Button btnSeeMoreDetails;
        private CardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameResponse = itemView.findViewById(R.id.nameResponse2Vol);
            txtNameAssociation = itemView.findViewById(R.id.fullNameResponse2Vol);
            txtMailAssociation = itemView.findViewById(R.id.mailVolunteerResponse2Vol);
            txtNamePost = itemView.findViewById(R.id.postNameResponse2Vol);
            parent = itemView.findViewById(R.id.parentResponse);
            btnSeeMoreDetails = itemView.findViewById(R.id.btnSeeDetailsResponse2Vol);



        }
    }
}

