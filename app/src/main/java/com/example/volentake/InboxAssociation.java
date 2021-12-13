package com.example.volentake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Pair;

import com.example.myapplication.AdapterPostVol;
import com.example.myapplication.AdapterReqAssociation;
import com.example.myapplication.Assoc_post;
import com.example.myapplication.Request_vol;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InboxAssociation extends AppCompatActivity {

    private RecyclerView recycleRequests;
    private DatabaseReference mRootRef;
    String request_id = "";
    ArrayList<Pair<Request_vol,String>> requests = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox_association);
        Bundle bun = null;
        bun = getIntent().getExtras();
        if(bun != null)
        {
            request_id = bun.getString("id");
        }

        recycleRequests = findViewById(R.id.recycleRequests);
        mRootRef = FirebaseDatabase.getInstance().getReference();

        mRootRef.child("posts")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ///////for Liel
//                        for (DataSnapshot data1 : dataSnapshot.getChildren() )
//                        {
//                            String post_id = data1.getKey();
//                            Assoc_post cur_post1 = data1.getValue(Assoc_post.class);
//                            Assoc_post cur_post2 = new Assoc_post(cur_post1.getName(),cur_post1.getLocation(),cur_post1.getNum_of_participants(),cur_post1.getType(),cur_post1.getPhone_number(), cur_post1.userId,cur_post1.getDescription());
//                            Pair<Assoc_post,String> pair1 =new Pair<>(cur_post2,post_id);
//                            posts.add(pair1);
//
//                        }
                        AdapterReqAssociation adapter = new AdapterReqAssociation( InboxAssociation.this,request_id);
                        adapter.setListRequests(requests);

                        recycleRequests.setAdapter(adapter);
                        recycleRequests.setLayoutManager(new GridLayoutManager(InboxAssociation.this,1));
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });



    }
}