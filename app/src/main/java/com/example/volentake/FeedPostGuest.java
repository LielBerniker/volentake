package com.example.volentake;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.widget.Button;

import com.example.myapplication.AdapterPostGuest;
import com.example.myapplication.AdapterPostVol;
import com.example.myapplication.Assoc_post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class FeedPostGuest extends AppCompatActivity {

    private RecyclerView postsRecycle;
    private DatabaseReference mRootRef;
    private Button backBtn;
    AlertDialog.Builder builder;
    ArrayList<Pair<Assoc_post, String>> posts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_post_guest);
        builder = new AlertDialog.Builder(this);
        backBtn = findViewById(R.id.back_activity_feed_post_guest);
        postsRecycle = findViewById(R.id.recyclePostsGuest);
        mRootRef = FirebaseDatabase.getInstance().getReference();
        ProgressDialog progressDialog
                = new ProgressDialog(this);
        progressDialog.setTitle("loading...");
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.drawable.logovector_01);
        progressDialog.show();
        mRootRef.child("posts")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data1 : dataSnapshot.getChildren()) {
                            String post_id = data1.getKey();
                            Assoc_post cur_post1 = data1.getValue(Assoc_post.class);
                            Assoc_post cur_post2 = new Assoc_post(cur_post1.getName(), cur_post1.getLocation(), cur_post1.getNum_of_participants(), cur_post1.getType(), cur_post1.getPhone_number(), cur_post1.userId, cur_post1.getDescription());
                            Pair<Assoc_post, String> pair1 = new Pair<>(cur_post2, post_id);
                            posts.add(pair1);

                        }
                        if (posts.size() == 0) {
                            progressDialog.dismiss();
                            builder.setTitle("volunteering events")
                                    .setMessage("there is not any volunteering events ")
                                    .setCancelable(true)
                                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent1 = new Intent(FeedPostGuest.this,GuestUser.class);
                                            startActivity(intent1);
                                        }
                                    });
                            AlertDialog alterdialod = builder.create();
                            alterdialod.show();
                        }
                        Collections.reverse(posts);
                        AdapterPostGuest adapter = new AdapterPostGuest(FeedPostGuest.this);
                        adapter.setPosts(posts);

                        postsRecycle.setAdapter(adapter);
                        postsRecycle.setLayoutManager(new GridLayoutManager(FeedPostGuest.this, 1));
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
        backBtn.setOnClickListener(view -> {
            Intent intent = new Intent(FeedPostGuest.this, GuestUser.class);
            startActivity(intent);
        });
    }

}