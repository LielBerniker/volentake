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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.model_voluntake_class.Assoc_post;
import com.example.model_voluntake_class.Vol_user;
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
       PostStreet = (TextView)findViewById(R.id.detailStreetPostActivePostVol);
        PostCity = (TextView)findViewById(R.id.detailCityPostActivePostVol);
        Poststnum = (TextView)findViewById(R.id.detailHouseNumberPostActivePostVol);
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
                    PostStreet.setText(cur_post.getLocation().getStreet());
                    PostCity.setText(cur_post.getLocation().getCity());
                    Poststnum.setText(Integer.toString(cur_post.getLocation().getNumber()));
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
                post_pic.setImageResource(R.drawable.logo_01);
                progressDialog.dismiss();
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
                Intent intent1 = new Intent(DetailsActivePostVol.this,VolunteerPage.class);
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
                                    Intent intent1 = new Intent(DetailsActivePostVol.this, VolunteerLogIn.class);
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