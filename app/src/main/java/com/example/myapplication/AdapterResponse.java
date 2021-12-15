package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.volentake.DetailsRequest;
import com.example.volentake.InboxResponses;
import com.example.volentake.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterResponse extends RecyclerView.Adapter<AdapterResponse.ViewHolder> {
    Context context;
    String vol_id;
    ArrayList<Pair<Response,String>> listResponse= new ArrayList<>();

    public AdapterResponse(Context context, String vol_id){
        this.context = context;
        this.vol_id = vol_id;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_response_shape, parent, false);
        ViewHolder holder= new ViewHolder(view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String assoc_id = listResponse.get(position).first.getAssociation_user_id();
        String post_id = listResponse.get(position).first.getPost_id();
        holder.mDatabase.child("assoc_users").child(assoc_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Assoc_user cure_user =  task.getResult().getValue(Assoc_user.class);
                    holder.txtNameAssociation.setText(cure_user.getName());
                    holder.txtMailAssociation.setText(cure_user.getEmail());

                }
            }
        });
        holder.mDatabase.child("posts").child(post_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Assoc_post cure_post =  task.getResult().getValue(Assoc_post.class);
                    holder.txtNamePost.setText(cure_post.getName());
                }
            }
        });

        holder.btnSeeMoreDetails.setOnClickListener(view -> {
            Intent intent = new Intent(context, InboxResponses.class);
            intent.putExtra("response_id",listResponse.get(position).second);
            intent.putExtra("vol_id",vol_id);
            intent.putExtra("post_name",holder.txtNamePost.getText().toString());
            intent.putExtra("assoc_name",holder.txtNameAssociation.getText().toString());
            intent.putExtra("assoc_email",holder.txtMailAssociation.getText().toString());
            context.startActivity(intent);
        });

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
        private TextView  txtNameAssociation, txtMailAssociation,txtNamePost;
        private Button btnSeeMoreDetails;
        private CardView parent;
        private DatabaseReference mDatabase;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //        firebase code
            mDatabase = FirebaseDatabase.getInstance().getReference();
            txtNameAssociation = itemView.findViewById(R.id.assocNameResponse2Vol);
            txtMailAssociation = itemView.findViewById(R.id.mailVolunteerResponse2Vol);
            txtNamePost = itemView.findViewById(R.id.nameResponse2Vol);
            parent = itemView.findViewById(R.id.parentResponse);
            btnSeeMoreDetails = itemView.findViewById(R.id.btnSeeDetailsResponse2Vol);



        }
    }
}

