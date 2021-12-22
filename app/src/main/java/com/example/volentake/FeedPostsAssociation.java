package com.example.volentake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.Button;

import com.example.myapplication.AdapterPostAssociation;
import com.example.myapplication.AdapterPostVol;
import com.example.myapplication.Assoc_post;
import com.example.myapplication.Assoc_user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FeedPostsAssociation extends AppCompatActivity {

    private RecyclerView postsRecycle;
    private DatabaseReference mRootRef;
    private Button backBtn;
    AlertDialog.Builder builder;
    String association_user_id = "";
    ArrayList<String> cur_posts;
    ArrayList<Pair<Assoc_post, String>> posts = new ArrayList<>();
    int num_of_posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_posts_associations);
        backBtn.findViewById(R.id.back_activity_feed_posts_associations);
        builder = new AlertDialog.Builder(this);
        ProgressDialog progressDialog
                = new ProgressDialog(this);
        progressDialog.setTitle("loading...");
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.drawable.logovector_01);
        progressDialog.show();
//        get information bundle
        Bundle bun = null;
        bun = getIntent().getExtras();
        if (bun != null) {
            association_user_id = bun.getString("id");
        }
        postsRecycle = findViewById(R.id.recyclePostsAssociation);
        mRootRef = FirebaseDatabase.getInstance().getReference();

        mRootRef.child("assoc_users").child(association_user_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    Assoc_user cure_user = task.getResult().getValue(Assoc_user.class);
                    cur_posts = cure_user.getPosts();

                    num_of_posts = cur_posts.size();
                    if (num_of_posts == 1) {
                        progressDialog.dismiss();
                        builder.setTitle("volunteering events")
                                .setMessage("this association do not have any volunteering events")
                                .setCancelable(true)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent1 = new Intent(FeedPostsAssociation.this, AssociationPage.class);
                                        intent1.putExtra("id", association_user_id);
                                        startActivity(intent1);
                                    }
                                });
                        AlertDialog alterdialod = builder.create();
                        alterdialod.show();
                    }
                    for (int i = 1; i < num_of_posts; i++) {
                        int count = i;
                        String post_id = cur_posts.get(count);
                        mRootRef.child("posts").child(post_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (!task.isSuccessful()) {
                                    Log.e("firebase", "Error getting data", task.getException());
                                } else {
                                    Assoc_post cur_post1 = task.getResult().getValue(Assoc_post.class);
                                    Assoc_post cur_post2 = new Assoc_post(cur_post1.getName(), cur_post1.getLocation(), cur_post1.getNum_of_participants(), cur_post1.getType(), cur_post1.getPhone_number(), cur_post1.userId, cur_post1.getDescription());
                                    Pair<Assoc_post, String> pair1 = new Pair<>(cur_post2, post_id);
                                    posts.add(pair1);
                                    if (count + 1 == num_of_posts) {
                                        AdapterPostAssociation adapter = new AdapterPostAssociation(FeedPostsAssociation.this, association_user_id);
                                        adapter.setPosts(posts);

                                        postsRecycle.setAdapter(adapter);
                                        postsRecycle.setLayoutManager(new GridLayoutManager(FeedPostsAssociation.this, 1));
                                    }
                                    progressDialog.dismiss();
                                }
                            }
                        });
                    }


                }
            }
        });

        backBtn.setOnClickListener(view -> {
            Intent intent = new Intent(FeedPostsAssociation.this, AssociationPage.class);
            intent.putExtra("id", association_user_id);
            startActivity(intent);
        });

        progressDialog.dismiss();

    }

}