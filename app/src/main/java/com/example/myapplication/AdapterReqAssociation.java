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

public class AdapterReqAssociation extends RecyclerView.Adapter<AdapterReqAssociation.ViewHolder> {
    Context context;
    ArrayList<Request_vol> listMessages= new ArrayList<>();

    public AdapterReqAssociation(Context context){
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_request_association, parent, false);
        ViewHolder holder= new ViewHolder(view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.txtMailVolunteer.setText(listMessages.get(position).getVol_user().getEmail());
        holder.txtNumVolOfPost.setText(String.valueOf(listMessages.get(position).getNum_of_vol()));
        holder.txtContent.setText(listMessages.get(position).getContent());
//        holder.txtLocationPost.setText(listMessages.get(position).getLocation().getCity());
    }

    @Override
    public int getItemCount() {
        return listMessages.size();
    }

    public void setListMessages(ArrayList<Request_vol> listMessages){
        this.listMessages = listMessages;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtMailVolunteer, txtNumVolOfPost,txtLocationPost, txtContent;
        private CardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMailVolunteer = itemView.findViewById(R.id.mailVolunteer);
            txtNumVolOfPost = itemView.findViewById(R.id.numVolOfPost);
            txtLocationPost = itemView.findViewById(R.id.locationPost);
//            txtContent = itemView.findViewById(R.id.contentMessage);
            parent = itemView.findViewById(R.id.parentReqAss);

        }
    }
}
