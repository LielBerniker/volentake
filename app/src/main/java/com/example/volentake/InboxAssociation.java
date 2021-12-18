package com.example.volentake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.Button;

import com.example.myapplication.AdapterPostAssociation;
import com.example.myapplication.AdapterPostVol;
import com.example.myapplication.AdapterReqAssociation;
import com.example.myapplication.Assoc_post;
import com.example.myapplication.Assoc_user;
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
    private Button back;
    private RecyclerView recycleRequests;
    private DatabaseReference mRootRef;
    String assoc_id = "";
    ArrayList<String> cur_requests ;
    ArrayList<Pair<Request_vol,String>> requests = new ArrayList<>();
    int num_of_requests;
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
        back = (Button) findViewById(R.id.backtoassocuserfrominbox);
        mRootRef.child("assoc_users").child(assoc_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Assoc_user cure_user =  task.getResult().getValue(Assoc_user.class);
                    cur_requests = cure_user.getMassages_req();

                    num_of_requests = cur_requests.size();
                    for (int i = 1; i <num_of_requests ; i++) {
                        int count = i;
                        String req_id = cur_requests.get(count);
                        mRootRef.child("massage_assoc").child(req_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (!task.isSuccessful()) {
                                    Log.e("firebase", "Error getting data", task.getException());
                                }
                                else {
                                    Request_vol cur_req1 =  task.getResult().getValue(Request_vol.class);
                                    Request_vol cur_req2 = new Request_vol(cur_req1.getVol_user_id(),cur_req1.getPost_id(),cur_req1.getContent(),cur_req1.getNum_of_vol(),cur_req1.getStatus());
                                    Pair<Request_vol,String> pair1 =new Pair<>(cur_req2,req_id);
                                    requests.add(pair1);
                                    if(count+1==num_of_requests)
                                    {
                                        AdapterReqAssociation adapter = new AdapterReqAssociation( InboxAssociation.this,assoc_id);
                                        adapter.setListRequests(requests);

                                        recycleRequests.setAdapter(adapter);
                                        recycleRequests.setLayoutManager(new GridLayoutManager(InboxAssociation.this,1));}
                                }
                            }
                        });
                    }


                }
            }
        });

        back.setOnClickListener(view -> {
            Intent intent = new Intent(InboxAssociation.this,AssociationPage.class);
            intent.putExtra("id", assoc_id);
            startActivity(intent);
        });

    }
}