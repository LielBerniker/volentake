package com.example.volentake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication.AdapterPost;
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

    private RecyclerView recycleMessages;
    private DatabaseReference mRootRef;
    ArrayList<Request_vol> messages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox_association);

        recycleMessages = findViewById(R.id.recycleMessages);
        mRootRef = FirebaseDatabase.getInstance().getReference();

        messages.add(new Request_vol("","","", 8));
        messages.add(new Request_vol("","","", 19));


        AdapterReqAssociation adapter = new AdapterReqAssociation( InboxAssociation.this);
        adapter.setListMessages(messages);

        recycleMessages.setAdapter(adapter);
        recycleMessages.setLayoutManager(new GridLayoutManager(InboxAssociation.this,1));

    }
}