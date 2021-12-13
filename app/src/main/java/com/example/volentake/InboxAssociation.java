package com.example.volentake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;

import com.example.myapplication.AdapterPostAssociation;
import com.example.myapplication.AdapterPostVol;
import com.example.myapplication.AdapterReqAssociation;
import com.example.myapplication.Assoc_post;
import com.example.myapplication.Request;
import com.example.myapplication.Request_vol;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class InboxAssociation extends AppCompatActivity {

    private RecyclerView recycleRequests;
    private DatabaseReference mRootRef;
    String assoc_id = "";
    ArrayList<Pair<Request, Integer>> requests = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox_association);
        Bundle bun = null;
        bun = getIntent().getExtras();
        if(bun != null)
        {
            assoc_id = bun.getString("id");
        }

        recycleRequests = findViewById(R.id.recycleRequests);
        mRootRef = FirebaseDatabase.getInstance().getReference();

        mRootRef.child("assoc_massages").child(assoc_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    ArrayList<HashMap<String,String >> cur_massages1  = (ArrayList<HashMap<String,String>>)task.getResult().getValue();
                    ArrayList<HashMap<String, Long>> cur_massages2  = (ArrayList<HashMap<String, Long>>)task.getResult().getValue();
                    for (int i = 1; i <cur_massages1.size(); i++) {
                        Request cur_req = new Request_vol(cur_massages1.get(i).get("vol_user_id"),cur_massages1.get(i).get("post_id"),cur_massages1.get(i).get("content"),cur_massages2.get(i).get("num_of_vol").intValue());
                        Pair<Request,Integer> pair1 =new Pair<>(cur_req,i);
                        requests.add(pair1);
                    }

                        AdapterReqAssociation adapter = new AdapterReqAssociation( InboxAssociation.this,assoc_id);
                        adapter.setListRequests(requests);

                    recycleRequests.setAdapter(adapter);
                    recycleRequests.setLayoutManager(new GridLayoutManager(InboxAssociation.this,1));}

            }
        });



    }
}