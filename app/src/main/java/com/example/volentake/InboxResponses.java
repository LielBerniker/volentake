package com.example.volentake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;

import com.example.myapplication.AdapterReqAssociation;
import com.example.myapplication.AdapterResponse;
import com.example.myapplication.Assoc_user;
import com.example.myapplication.Request_vol;
import com.example.myapplication.Response;
import com.example.myapplication.Vol_user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InboxResponses extends AppCompatActivity {

    private RecyclerView recycleResponses;
    private DatabaseReference mRootRef;
    String vol_id = "";
    ArrayList<String> cur_responses ;
    ArrayList<Pair<Response,String>> responses = new ArrayList<>();
    int num_of_responses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox_responses);
        Bundle bun = null;
        bun = getIntent().getExtras();
        if(bun != null)
        {
            vol_id = bun.getString("id");
        }

        recycleResponses = findViewById(R.id.recycleResponses);
        mRootRef = FirebaseDatabase.getInstance().getReference();

        mRootRef.child("vol_users").child(vol_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Vol_user cure_user =  task.getResult().getValue(Vol_user.class);
                    cur_responses= cure_user.getMassages_res();

                    num_of_responses = cur_responses.size();
                    for (int i = 1; i <num_of_responses ; i++) {
                        int count = i;
                        String res_id = cur_responses.get(count);
                        mRootRef.child("massage_vol").child(res_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (!task.isSuccessful()) {
                                    Log.e("firebase", "Error getting data", task.getException());
                                }
                                else {
                                    Response cur_res1 =  task.getResult().getValue(Response.class);
                                    Response cur_res2 = new Response(cur_res1.getVol_user_id(),cur_res1.getAssociation_user_id(),cur_res1.getPost_id(),cur_res1.getStatus());
                                    Pair<Response,String> pair1 =new Pair<>(cur_res2,res_id);
                                    responses.add(pair1);
                                    if(count+1==num_of_responses)
                                    {
                                        AdapterResponse adapter = new AdapterResponse( InboxResponses.this,vol_id);
                                        adapter.setResponses(responses);

                                        recycleResponses.setAdapter(adapter);
                                        recycleResponses.setLayoutManager(new GridLayoutManager(InboxResponses.this,1));}
                                }
                            }
                        });
                    }


                }
            }
        });



    }
}