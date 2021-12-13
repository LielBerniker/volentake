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

import com.example.volentake.DetailsPostAssociation;
import com.example.volentake.R;

import java.util.ArrayList;

public class AdapterReqAssociation extends RecyclerView.Adapter<AdapterReqAssociation.ViewHolder> {
    Context context;
    String request_id;
    ArrayList<Pair<Request_vol,String>> listRequests= new ArrayList<>();

    public AdapterReqAssociation(Context context, String request_id){
        this.context = context;
        this.request_id = request_id;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_request_shape, parent, false);
        ViewHolder holder= new ViewHolder(view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        /////////////for Liel
//        holder.txtNameAssociation.setText(listPosts.get(position).first.getName());
//        holder.txtNumVol.setText(String.valueOf(listPosts.get(position).first.getNum_of_participants()));
//        holder.txtType.setText(listPosts.get(position).first.getType());
//        holder.txtCity.setText(listPosts.get(position).first.getLocation().getCity());
//        holder.btnSeeMoreDetails.setOnClickListener(view -> {
//            Intent intent = new Intent(context, DetailsPostAssociation.class);
//            intent.putExtra("request_id",request_id);
//            intent.putExtra("post_id",listPosts.get(position).second);
//            context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return listRequests.size();
    }

    public void setListRequests(ArrayList<Pair<Request_vol,String>> listRequests){
        this.listRequests = listRequests;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtMailVolRequestToAss, namePost,txtFullNameRequestToAss;
        private Button btnSeeDetailsRequestToAss;
        private CardView parentRequestToAss;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMailVolRequestToAss = itemView.findViewById(R.id.txtMailVolRequestToAss);
            namePost = itemView.findViewById(R.id.nameMessageRequestToAss);
            txtFullNameRequestToAss = itemView.findViewById(R.id.txtFullNameRequestToAss);
            btnSeeDetailsRequestToAss = itemView.findViewById(R.id.btnSeeDetailsRequestToAss);
            parentRequestToAss = itemView.findViewById(R.id.parentRequestToAss);

        }
    }
}
