package com.example.volentake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
    private TextView PostName, PostCity, PostStreet, Poststnum, NumOfVol, PostType, PostPhoneNum, PostDescription;
    private Button back, btnPageRequest;
    private ImageView post_pic;
    //    firebase
    private DatabaseReference mDatabase;
    private StorageReference mystorge;
    String vol_user_id = "";
    String post_id = "";
    String assoc_id = "";
    String search_keyword;
    AlertDialog.Builder builder;
    int state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_post_vol);
        ProgressDialog progressDialog
                = new ProgressDialog(this);
        progressDialog.setTitle("loading...");
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.drawable.logovector_01);
        progressDialog.show();
        builder = new AlertDialog.Builder(this);
//        firebase code
        mDatabase = FirebaseDatabase.getInstance().getReference();

//        get information bundle
        Bundle bun = null;
        bun = getIntent().getExtras();
        if (bun != null) {
            vol_user_id = bun.getString("vol_id");
            post_id = bun.getString("post_id");
            state = bun.getInt("state");
            search_keyword = bun.getString("search_keyword");
        }
        mystorge = FirebaseStorage.getInstance().getReference().child("post_description_pic/" + post_id);
        back = (Button) findViewById(R.id.btnBackToFeedOfPosts);
        btnPageRequest = (Button) findViewById(R.id.btnPageRequest);
        PostName = (TextView) findViewById(R.id.detailPostName);
        NumOfVol = (TextView) findViewById(R.id.detailNumVolPost);
        PostPhoneNum = (TextView) findViewById(R.id.detailPhoneNumberPost);
        PostDescription = (TextView) findViewById(R.id.detailDescriptionPost);
        PostType = (TextView) findViewById(R.id.detailTypePost);
        post_pic = (ImageView) findViewById(R.id.PostImage2);
        mDatabase.child("posts").child(post_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    Assoc_post cur_post = task.getResult().getValue(Assoc_post.class);
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
                    final File localFile = File.createTempFile(post_id, "");
                    mystorge.getFile(localFile)
                            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                    final Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                    post_pic.setImageBitmap(bitmap);
                                    progressDialog.dismiss();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                        }
                    });
                } catch (IOException e) {

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                progressDialog.dismiss();
            }
        });

        back.setOnClickListener(view -> {
            if (state == 0) {
                Intent intent = new Intent(DetailsPostVol.this, FeedPostsVolByCity.class);
                intent.putExtra("id", vol_user_id);
                intent.putExtra("city", search_keyword);
                startActivity(intent);
            } else if (state == 1) {
                Intent intent = new Intent(DetailsPostVol.this, FeedPostsVolByType.class);
                intent.putExtra("id", vol_user_id);
                intent.putExtra("type", search_keyword);
                startActivity(intent);
            } else if(state == 2){
                Intent intent = new Intent(DetailsPostVol.this, FeedPostsVol.class);
                intent.putExtra("id", vol_user_id);
                intent.putExtra("no_word", search_keyword);
                startActivity(intent);
            }
         else {
            Intent intent = new Intent(DetailsPostVol.this, FeedPostsVolShuffle.class);
            intent.putExtra("id", vol_user_id);
            intent.putExtra("no_word", search_keyword);
            startActivity(intent);
        }
        });
        btnPageRequest.setOnClickListener(view -> {
            builder.setTitle("apply request")
                    .setMessage("you are about to send a volunteering request")
                    .setCancelable(true)
                    .setPositiveButton("proceed to request", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent1 = new Intent(DetailsPostVol.this, RequestAssociation.class);
                            intent1.putExtra("post_id", post_id);
                            intent1.putExtra("vol_id", vol_user_id);
                            intent1.putExtra("assoc_id", assoc_id);
                            intent1.putExtra("search_keyword", search_keyword);
                            intent1.putExtra("state", state);
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
                Intent intent1 = new Intent(DetailsPostVol.this,VolunteerPage.class);
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
                                Intent intent1 = new Intent(DetailsPostVol.this, VolunteerLogIn.class);
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