package com.example.volentake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.AdapterActivePostVol;
import com.example.myapplication.Assoc_post;
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

public class DetailsActivePostVol extends AppCompatActivity {
    private TextView PostName,PostCity,PostStreet,Poststnum,NumOfVol,PostType,PostPhoneNum, PostDescription;
    private Button quit,back;
    private ImageView post_pic;
    //    firebase
    private DatabaseReference mDatabase;
    private StorageReference mystorge;
    String vol_user_id = "";
    String post_id = "";
    int post_position;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_active_post_vol);
        builder = new AlertDialog.Builder(this);
        //        firebase code
        mDatabase = FirebaseDatabase.getInstance().getReference();

//        get information bundle
        Bundle bun = null;
        bun = getIntent().getExtras();
        if(bun != null)
        {
            vol_user_id = bun.getString("vol_id");
            post_id = bun.getString("post_id");
            post_position = bun.getInt("post_position");
        }
        mystorge = FirebaseStorage.getInstance().getReference().child("post_description_pic/"+post_id);
        back = (Button) findViewById(R.id.backtoactiveposts);
        quit = (Button) findViewById(R.id.quitVolunteeringActivePostVol);
        PostName = (TextView)findViewById(R.id.detailPostNameActivePostVol);
        NumOfVol = (TextView)findViewById(R.id.detailNumVolPostActivePostVol);
        PostPhoneNum = (TextView)findViewById(R.id.detailPhoneNumberPostActivePostVol);
        PostDescription = (TextView)findViewById(R.id.detailDescriptionPostActivePostVol);
        PostType = (TextView)findViewById(R.id.detailTypePostActivePostVol);
        post_pic = (ImageView)findViewById(R.id.PostImage2ActivePostVol);
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

        quit.setOnClickListener(view -> {
            builder.setTitle("quit")
                    .setMessage("are you sure you want to quit this volunteering event?")
                    .setCancelable(true)
                    .setPositiveButton("quit now", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mDatabase.child("vol_users").child(vol_user_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    if (!task.isSuccessful()) {
                                        Log.e("firebase", "Error getting data", task.getException());
                                    } else {
                                        Vol_user cure_user = task.getResult().getValue(Vol_user.class);
                                        cure_user.getActive_posts().remove(post_position);
                                        mDatabase.child("vol_users").child(vol_user_id).setValue(cure_user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    Toast.makeText(DetailsActivePostVol.this, "Done Successfully!", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(DetailsActivePostVol.this, FeedActivePostsVol.class);
                                                    intent.putExtra("id",vol_user_id);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }
                                        });

                                    }
                                }
                            });
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
        back.setOnClickListener(view -> {
            Intent intent = new Intent(DetailsActivePostVol.this, FeedActivePostsVol.class);
            intent.putExtra("id",vol_user_id);
            startActivity(intent);
        });
    }
}