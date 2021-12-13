package com.example.myapplication;

import android.content.Context;
<<<<<<< HEAD
=======
import android.content.Intent;
import android.util.Log;
>>>>>>> 8b8099e44f874e7a60d3397fbfab39ed0ba679df
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

<<<<<<< HEAD
=======
import com.example.volentake.DetailsPostAssociation;
import com.example.volentake.DetailsRequest;
>>>>>>> 8b8099e44f874e7a60d3397fbfab39ed0ba679df
import com.example.volentake.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterReqAssociation extends RecyclerView.Adapter<AdapterReqAssociation.ViewHolder> {
    Context context;
    String assoc_id;
    ArrayList<Pair<Request,Integer>> listRequests= new ArrayList<>();

    public AdapterReqAssociation(Context context, String assoc_id){
        this.context = context;
        this.assoc_id =assoc_id;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_request_shape, parent, false);
        ViewHolder holder= new ViewHolder(view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String vol_id = listRequests.get(position).first.getVol_user_id();
        String post_id = listRequests.get(position).first.getPost_id();
        holder.mDatabase.child("vol_users").child(vol_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Vol_user cure_user =  task.getResult().getValue(Vol_user.class);
                  holder.txtFullNameRequestToAss.setText(cure_user.getFirst_name()+" "+cure_user.getLast_name());
                    holder.MailVolRequestToAss.setText(cure_user.getEmail());

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
                    holder.namePost.setText(cure_post.getName());
                }
            }
        });

        holder.btnSeeDetailsRequestToAss.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailsRequest.class);
            intent.putExtra("request_pos",listRequests.get(position).second);
            intent.putExtra("assoc_id",assoc_id);
            context.startActivity(intent);
                });
    }

    @Override
    public int getItemCount() {
        return listRequests.size();
    }

    public void setListRequests(ArrayList<Pair<Request,Integer>> listRequests){
        this.listRequests = listRequests;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView MailVolRequestToAss, namePost,txtFullNameRequestToAss;
        private Button btnSeeDetailsRequestToAss;
        private CardView parentRequestToAss;
        //    firebase
        private DatabaseReference mDatabase;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
<<<<<<< HEAD
            txtMailVolRequestToAss = itemView.findViewById(R.id.txtMailVolRequestToAss);
            namePost = itemView.findViewById(R.id.nameResponse);
            txtFullNameRequestToAss = itemView.findViewById(R.id.txtFullNameRequestToAss);
=======
            //        firebase code
            mDatabase = FirebaseDatabase.getInstance().getReference();
            MailVolRequestToAss = itemView.findViewById(R.id.mailVolunteerRequestToAss);
            namePost = itemView.findViewById(R.id.nameMessageRequestToAss);
            txtFullNameRequestToAss = itemView.findViewById(R.id.fullNameRequestToAss);
>>>>>>> 8b8099e44f874e7a60d3397fbfab39ed0ba679df
            btnSeeDetailsRequestToAss = itemView.findViewById(R.id.btnSeeDetailsRequestToAss);
            parentRequestToAss = itemView.findViewById(R.id.parentRequestToAss);

        }
    }
}
