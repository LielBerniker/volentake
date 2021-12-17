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
import com.example.volentake.R;
import com.example.volentake.details_request_afterans;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterReqAssociation extends RecyclerView.Adapter<AdapterReqAssociation.ViewHolder> {
    Context context;
    String assoc_id;
    ArrayList<Pair<Request_vol,String >> listRequests= new ArrayList<>();

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
            if(listRequests.get(position).first.getStatus() == Status.APPROVED || listRequests.get(position).first.getStatus() == Status.REJECTED)
            {            Intent intent = new Intent(context, details_request_afterans.class);
                intent.putExtra("request_id",listRequests.get(position).second);
                intent.putExtra("assoc_id",assoc_id);
                intent.putExtra("post_name",holder.namePost.getText().toString());
                intent.putExtra("vol_user_name",holder.txtFullNameRequestToAss.getText().toString());
                intent.putExtra("vol_user_email",holder.MailVolRequestToAss.getText().toString());
                intent.putExtra("post_id",post_id);
                context.startActivity(intent);
            }
            else {
                Intent intent = new Intent(context, DetailsRequest.class);
                intent.putExtra("request_id", listRequests.get(position).second);
                intent.putExtra("assoc_id", assoc_id);
                intent.putExtra("post_name", holder.namePost.getText().toString());
                intent.putExtra("vol_user_name", holder.txtFullNameRequestToAss.getText().toString());
                intent.putExtra("vol_user_email", holder.MailVolRequestToAss.getText().toString());
                intent.putExtra("post_id", post_id);
                context.startActivity(intent);
            }
                });
    }

    @Override
    public int getItemCount() {
        return listRequests.size();
    }

    public void setListRequests(ArrayList<Pair<Request_vol,String >> listRequests){
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

            namePost = itemView.findViewById(R.id.nameResponse);
            txtFullNameRequestToAss = itemView.findViewById(R.id.fullNameRequestToAss);

            //        firebase code
            mDatabase = FirebaseDatabase.getInstance().getReference();
            MailVolRequestToAss = itemView.findViewById(R.id.mailVolunteerRequestToAss);
            btnSeeDetailsRequestToAss = itemView.findViewById(R.id.btnSeeDetailsRequestToAss);
            parentRequestToAss = itemView.findViewById(R.id.parentRequestToAss);

        }
    }
}
