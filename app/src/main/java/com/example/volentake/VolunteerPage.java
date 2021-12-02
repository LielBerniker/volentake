package com.example.volentake;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
    private Button addPost;
    private Button logOut;
    private Button edit_pic;
    private TextView FirstNameInsert;
    private TextView LastNameInsert;
    private TextView PhoneNumberInsert;
    private TextView EmailInsert;
    private ImageView profile_pic;
    //    firebase
    private DatabaseReference mDatabase;
    private StorageReference mystorge;
    String user_id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_page);
//        firebase code
        mDatabase = FirebaseDatabase.getInstance().getReference();

//        get information bundle
        Bundle bun = null;
        bun = getIntent().getExtras();
        if(bun != null)
        {
             user_id = bun.getString("id");
        }
        mystorge = FirebaseStorage.getInstance().getReference().child("vol_profile_pic/"+user_id);
        edit_pic = (Button)findViewById(R.id.btnaddpic);
        edit = (Button)findViewById(R.id.btnEditVol);
        logOut = (Button)findViewById(R.id.btnLogOut);
        FirstNameInsert = (TextView)findViewById(R.id.firstnameinsert);
//        LastNameInsert = (TextView)findViewById(R.id.lastnameinsert);
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
                    FirstNameInsert.setText(cure_user.getFirst_name());
//                    LastNameInsert.setText(cure_user.getLast_name());
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





        edit.setOnClickListener(view -> {
            Intent intent = new Intent(VolunteerPage.this, EditVolunteer.class);
            intent.putExtra("id",user_id);
            startActivity(intent);
        });
        edit_pic.setOnClickListener(view -> {
            Intent intent = new Intent(VolunteerPage.this, picture_edit_vol.class);
            intent.putExtra("id",user_id);
            startActivity(intent);
        });


        logOut.setOnClickListener(view -> {
            Intent intent = new Intent(VolunteerPage.this, VolunteerLogIn.class);
            startActivity(intent);
        });
    }
}