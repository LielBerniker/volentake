package com.example.volentake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Pair;

import com.example.myapplication.AdapterReqAssociation;
import com.example.myapplication.AdapterResponse;
import com.example.myapplication.Request_vol;
import com.example.myapplication.Response;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InboxResponses extends AppCompatActivity {

    private RecyclerView recycleResponses;
    private DatabaseReference mRootRef;
    String response_id = "";
    ArrayList<Pair<Response,String>> responses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox_responses);
        Bundle bun = null;
        bun = getIntent().getExtras();
        if(bun != null)
        {
            response_id = bun.getString("id");
        }

        recycleResponses = findViewById(R.id.recycleResponses);
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
                        AdapterResponse adapter = new AdapterResponse( InboxResponses.this,response_id);
                        adapter.setResponses(responses);

                        recycleResponses.setAdapter(adapter);
                        recycleResponses.setLayoutManager(new GridLayoutManager(InboxResponses.this,1));
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });



    }
}