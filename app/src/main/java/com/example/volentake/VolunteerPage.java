package com.example.volentake;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Vol_user;
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

/**
 * Activity Volunteer Home Page
 */
public class VolunteerPage extends AppCompatActivity {
    private Button edit;
    private Button logOut;
    private Button edit_pic;
    private Button SerchPost;
    private Button inboxResponse;
    private Button activePosts;
    private TextView FirstNameInsert;
    private TextView PhoneNumberInsert;
    private TextView EmailInsert;
    private ImageView profile_pic;
    AlertDialog.Builder builder;
    //    firebase
    private DatabaseReference mDatabase;
    private StorageReference mystorge;
    String user_id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_page);
        builder = new AlertDialog.Builder(this);
//        firebase code
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ProgressDialog progressDialog
                = new ProgressDialog(this);
        progressDialog.setTitle("loading...");
        progressDialog.setIcon(R.drawable.logovector_01);
        progressDialog.show();
//        get information bundle
        Bundle bun = null;
        bun = getIntent().getExtras();
        if(bun != null)
        {
             user_id = bun.getString("id");
        }
        mystorge = FirebaseStorage.getInstance().getReference().child("vol_profile_pic/"+user_id);
        edit_pic = (Button)findViewById(R.id.btnaddpic);
        SerchPost = (Button)findViewById(R.id.searchPostBTN);
        edit = (Button)findViewById(R.id.btnEditVol);
        logOut = (Button)findViewById(R.id.btnLogOut);
        inboxResponse = (Button)findViewById(R.id.btnInboxResponses);
        activePosts = (Button)findViewById(R.id.btnActivePosts);
        FirstNameInsert = (TextView)findViewById(R.id.firstnameinsert);
//
        PhoneNumberInsert = (TextView)findViewById(R.id.phonenumberinsert);
        EmailInsert = (TextView)findViewById(R.id.emailinsert);
        profile_pic = (ImageView)findViewById(R.id.imagevolprofile);
        mDatabase.child("vol_users").child(user_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Vol_user cure_user =  task.getResult().getValue(Vol_user.class);
                    assert cure_user != null;
                    FirstNameInsert.setText(cure_user.getFirst_name()+" " + cure_user.getLast_name());
                    PhoneNumberInsert.setText(cure_user.getPhone_num());
                    EmailInsert.setText(cure_user.getEmail());


                }
            }
        });
        mystorge.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                try {
                    final File localFile = File.createTempFile(user_id,"");
                    mystorge.getFile(localFile)
                            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                    final Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                    profile_pic.setImageBitmap(bitmap);
                                    progressDialog.dismiss();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
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
                progressDialog.dismiss();
            }
        });


        activePosts.setOnClickListener(view -> {
            Intent intent = new Intent(VolunteerPage.this, FeedActivePostsVol.class);
            intent.putExtra("id",user_id);
            startActivity(intent);
        });


        edit.setOnClickListener(view -> {
            Intent intent = new Intent(VolunteerPage.this, EditVolunteer.class);
            intent.putExtra("id",user_id);
            startActivity(intent);
        });
        SerchPost.setOnClickListener(view -> {
            Intent intent = new Intent(VolunteerPage.this, MainSearchPostsVol.class);
            intent.putExtra("id",user_id);
            startActivity(intent);
        });
        edit_pic.setOnClickListener(view -> {
            Intent intent = new Intent(VolunteerPage.this, picture_edit_vol.class);
            intent.putExtra("id",user_id);
            startActivity(intent);
        });
        inboxResponse.setOnClickListener(view -> {
            Intent intent = new Intent(VolunteerPage.this, InboxResponses.class);
            intent.putExtra("id",user_id);
            startActivity(intent);
        });


        logOut.setOnClickListener(view -> {
            builder.setTitle("logout")
                    .setMessage("you are about to log out")
                    .setCancelable(true)
                    .setPositiveButton("log out", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent1 = new Intent(VolunteerPage.this, VolunteerLogIn.class);
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
}