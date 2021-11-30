package com.example.volentake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication.AdapterPost;
import com.example.myapplication.Assoc_post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class searchPosts extends AppCompatActivity {

    private RecyclerView postsRecycle;
    private DatabaseReference mRootRef;
    ArrayList<Assoc_post> posts = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_posts);

        postsRecycle = findViewById(R.id.recyclePosts);
        mRootRef = FirebaseDatabase.getInstance().getReference();

        mRootRef.child("posts")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                            Assoc_post cur_post = dataSnap.getValue(Assoc_post.class);
                            posts.add(cur_post);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
        AdapterPost adapter = new AdapterPost(this);
        adapter.setPosts(posts);

        postsRecycle.setAdapter(adapter);
        postsRecycle.setLayoutManager(new GridLayoutManager(this,1));


    }
}