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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import com.example.model_voluntake_class.AdapterPostVol;
import com.example.model_voluntake_class.Assoc_post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class FeedPostsVolByType extends AppCompatActivity {

    private RecyclerView recyclePostsVolByType;
    private DatabaseReference mRootRef;
    private Button backBtn;
    AlertDialog.Builder builder;
    String vol_user_id = "";
    String search_type = "";
    ArrayList<Pair<Assoc_post, String>> posts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_posts_vol_by_type);
        backBtn = findViewById(R.id.back_activity_feed_posts_vol_by_type);
        builder = new AlertDialog.Builder(this);
        ProgressDialog progressDialog
                = new ProgressDialog(this);
        progressDialog.setTitle("loading...");
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.drawable.logovector_01);
        progressDialog.show();
        Bundle bun = null;
        bun = getIntent().getExtras();
        if (bun != null) {
            vol_user_id = bun.getString("id");
            search_type = bun.getString("type");
        }
        recyclePostsVolByType = findViewById(R.id.recyclePostsVolByType);
        mRootRef = FirebaseDatabase.getInstance().getReference();

        mRootRef.child("posts")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data1 : dataSnapshot.getChildren()) {
                            String post_id = data1.getKey();
                            Assoc_post cur_post1 = data1.getValue(Assoc_post.class);
                            if (cur_post1.getType().equals(search_type)) {
                                Assoc_post cur_post2 = new Assoc_post(cur_post1.getName(), cur_post1.getLocation(), cur_post1.getNum_of_participants(), cur_post1.getType(), cur_post1.getPhone_number(), cur_post1.userId, cur_post1.getDescription());
                                Pair<Assoc_post, String> pair1 = new Pair<>(cur_post2, post_id);
                                posts.add(pair1);
                            }

                        }
                        if (posts.size() == 0) {
                            progressDialog.dismiss();
                            builder.setTitle("volunteering events")
                                    .setMessage("there is not any volunteering events with the type: " + search_type)
                                    .setCancelable(true)
                                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent1 = new Intent(FeedPostsVolByType.this, MainSearchPostsVol.class);
                                            intent1.putExtra("id", vol_user_id);
                                            startActivity(intent1);
                                        }
                                    });
                            AlertDialog alterdialod = builder.create();
                            alterdialod.show();
                        }
                        Collections.reverse(posts);
                        AdapterPostVol adapter = new AdapterPostVol(FeedPostsVolByType.this, vol_user_id, search_type, 1);
                        adapter.setPosts(posts);

                        recyclePostsVolByType.setAdapter(adapter);
                        recyclePostsVolByType.setLayoutManager(new GridLayoutManager(FeedPostsVolByType.this, 1));
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
        backBtn.setOnClickListener(view -> {
            Intent intent = new Intent(FeedPostsVolByType.this, MainSearchPostsVol.class);
            intent.putExtra("id", vol_user_id);
            startActivity(intent);
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itembacktouser:
                Intent intent1 = new Intent(FeedPostsVolByType.this,VolunteerPage.class);
                intent1.putExtra("id",vol_user_id);
                startActivity(intent1);
                return true;
            case R.id.itemlogout:
                builder.setTitle("log out")
                        .setMessage("are you sure you want to log out?")
                        .setCancelable(true)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent1 = new Intent(FeedPostsVolByType.this, VolunteerLogIn.class);
                                startActivity(intent1);
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alterdialod = builder.create();
                alterdialod.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}