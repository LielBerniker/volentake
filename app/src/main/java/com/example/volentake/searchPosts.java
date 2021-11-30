package com.example.volentake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication.AdapterPost;
import com.example.myapplication.Assoc_post;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class searchPosts extends AppCompatActivity {

    private RecyclerView postsRecycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_posts);

        postsRecycle = findViewById(R.id.recyclePosts);

        ArrayList<Assoc_post> posts = new ArrayList<>();
        posts.add(new Assoc_post("Dvir", null, 5, "", "", "", ""));
        posts.add(new Assoc_post("Liel", null, 8, "", "", "", ""));
        posts.add(new Assoc_post("Kfir", null, 12, "", "", "", ""));

        AdapterPost adapter = new AdapterPost(this);
        adapter.setPosts(posts);

        postsRecycle.setAdapter(adapter);
        postsRecycle.setLayoutManager(new GridLayoutManager(this,2));


    }
}