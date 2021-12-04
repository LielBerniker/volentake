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
import java.util.Map;

public class searchPosts extends AppCompatActivity {

    private RecyclerView postsRecycle;
    private DatabaseReference mRootRef;
    String vol_user_id = "";
    ArrayList<Assoc_post> posts = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_posts);
//        get information bundle
        Bundle bun = null;
        bun = getIntent().getExtras();
        if(bun != null)
        {
            vol_user_id = bun.getString("id");
        }
        postsRecycle = findViewById(R.id.recyclePosts);
        mRootRef = FirebaseDatabase.getInstance().getReference();

        mRootRef.child("posts")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                      for (DataSnapshot data1 : dataSnapshot.getChildren() )
                      {
                          Assoc_post cur_post1 = data1.getValue(Assoc_post.class);
                          Assoc_post cur_post2 = new Assoc_post(cur_post1.getName(),cur_post1.getLocation(),cur_post1.getNum_of_participants(),cur_post1.getType(),cur_post1.getPhone_number(), cur_post1.getId(),cur_post1.getDescription());
                          posts.add(cur_post2);

                      }
                        AdapterPost adapter = new AdapterPost( searchPosts.this,vol_user_id);
                        adapter.setPosts(posts);

                        postsRecycle.setAdapter(adapter);
                        postsRecycle.setLayoutManager(new GridLayoutManager(searchPosts.this,1));
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });



    }

}