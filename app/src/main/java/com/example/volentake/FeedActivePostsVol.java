package com.example.volentake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;

import com.example.myapplication.AdapterActivePostVol;
import com.example.myapplication.AdapterPostAssociation;
import com.example.myapplication.AdapterPostVol;
import com.example.myapplication.Assoc_post;
import com.example.myapplication.Assoc_user;
import com.example.myapplication.Vol_user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FeedActivePostsVol extends AppCompatActivity {
    private RecyclerView postsActiveRecycle;
    private DatabaseReference mRootRef;
    String vol_user_id = "";
    ArrayList<Pair<Assoc_post, String>> posts = new ArrayList<>();
    ArrayList<String> cur_active_posts;
    int num_of_posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_active_posts_vol);
        //        get information bundle
        Bundle bun = null;
        bun = getIntent().getExtras();
        if (bun != null) {
            vol_user_id = bun.getString("id");
        }
        postsActiveRecycle = findViewById(R.id.recycleActivePosts);
        mRootRef = FirebaseDatabase.getInstance().getReference();

        mRootRef.child("vol_users").child(vol_user_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    Vol_user cure_user = task.getResult().getValue(Vol_user.class);
                    cur_active_posts = cure_user.getActive_posts();

                    num_of_posts = cur_active_posts.size();
                    for (int i = 1; i < num_of_posts; i++) {
                        int count = i;
                        String post_id = cur_active_posts.get(count);
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
                                        AdapterActivePostVol adapter = new AdapterActivePostVol(FeedActivePostsVol.this, vol_user_id);
                                        adapter.setPosts(posts);

                                        postsActiveRecycle.setAdapter(adapter);
                                        postsActiveRecycle.setLayoutManager(new GridLayoutManager(FeedActivePostsVol.this, 1));
                                    }
                                }
                            }


                        });
                    }
                }
            }
        });
    }
}