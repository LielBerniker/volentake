package com.example.volentake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Assoc_post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class DetailsPostVol extends AppCompatActivity {
    private TextView PostName,PostCity,PostStreet,Poststnum,NumOfVol,PostType,PostPhoneNum, PostDescription;
    private Button back, btnPageRequest;
    private ImageView post_pic;
    //    firebase
    private DatabaseReference mDatabase;
    private StorageReference mystorge;
    String vol_user_id = "";
    String post_id = "";
    String assoc_id = "";
    String search_keyword;
    int state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_post_vol);
//        firebase code
        mDatabase = FirebaseDatabase.getInstance().getReference();

//        get information bundle
        Bundle bun = null;
        bun = getIntent().getExtras();
        if(bun != null)
        {
            vol_user_id = bun.getString("vol_id");
            post_id = bun.getString("post_id");
            state= bun.getInt("state");
            search_keyword = bun.getString("search_keyword");
        }
        mystorge = FirebaseStorage.getInstance().getReference().child("post_description_pic/"+post_id);
        back = (Button) findViewById(R.id.btnBackToFeedOfPosts);
        btnPageRequest =  (Button) findViewById(R.id.btnPageRequest);
        PostName = (TextView)findViewById(R.id.detailPostName);
        NumOfVol = (TextView)findViewById(R.id.detailNumVolPost);
        PostPhoneNum = (TextView)findViewById(R.id.detailPhoneNumberPost);
        PostDescription = (TextView)findViewById(R.id.detailDescriptionPost);
        PostType = (TextView)findViewById(R.id.detailTypePost);
        post_pic = (ImageView)findViewById(R.id.PostImage2);
        mDatabase.child("posts").child(post_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Assoc_post cur_post =  task.getResult().getValue(Assoc_post.class);
                    assert cur_post != null;
                    PostName.setText(cur_post.getName());
                    NumOfVol.setText(String.valueOf(cur_post.getNum_of_participants()));
                    PostPhoneNum.setText(cur_post.getPhone_number());
                    PostDescription.setText(cur_post.getDescription());
                    PostType.setText(cur_post.getType());
                    assoc_id = cur_post.userId;
                    System.out.println("this id the user id in the post" + assoc_id);
                }
            }
        });
        mystorge.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                try {
                    final File localFile = File.createTempFile(post_id,"");
                    mystorge.getFile(localFile)
                            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                    final Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                    post_pic.setImageBitmap(bitmap);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        }
                    });
                }
                catch (IOException e){

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // File not found
            }
        });

        back.setOnClickListener(view -> {
            if(state == 0) {
                Intent intent = new Intent(DetailsPostVol.this, FeedPostsVolByCity.class);
                intent.putExtra("id", vol_user_id);
                intent.putExtra("city", search_keyword);
                startActivity(intent);
            }
            else if(state == 1)
            {
                Intent intent = new Intent(DetailsPostVol.this,FeedPostsVolByType.class);
                intent.putExtra("id", vol_user_id);
                intent.putExtra("type", search_keyword);
                startActivity(intent);
            }
            else
            {
                Intent intent = new Intent(DetailsPostVol.this, FeedPostsVol.class);
                intent.putExtra("id", vol_user_id);
                intent.putExtra("no_word", search_keyword);
                startActivity(intent);
            }
        });
        btnPageRequest.setOnClickListener(view -> {
            Intent intent = new Intent(DetailsPostVol.this, RequestAssociation.class);
            intent.putExtra("vol_id",vol_user_id);
            intent.putExtra("post_id",post_id);
            intent.putExtra("assoc_id",assoc_id);
            intent.putExtra("search_keyword",search_keyword);
            intent.putExtra("state",state);
            startActivity(intent);
        });
    }
}